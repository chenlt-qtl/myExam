import React, { Component } from 'react'
import PubSub from 'pubsub-js'

export default class Search extends Component {

  search = async () => {
    //通过ref获取用户的输入
    const { keyWordElement: { value: keyWord } } = this;

    if (!keyWord) {
      PubSub.publish("abc", { isFirst: false, isLoading: false, err: "请输入关键字" })
      return;
    } else {
      PubSub.publish("abc", { err: "" })
    }
    //发送请求前通知List更新状态
    PubSub.publish("abc", { isFirst: false, isLoading: true })

    //使用fetch发送
    // fetch(`https://api.github.com/search/users?q=${keyWord}`).then(
    //   res => {
    //     console.log("联系服务器成功");
    //     if (res.ok) {
    //       res.json().then(response => {
    //         console.log("获取数据成功", response);
    //       },
    //         error => {
    //           console.log("获取数据失败", error);
    //         })
    //     }else{
    //       console.log("获取数据失败", res);
    //     }
    //   },
    //   error => {
    //     console.log("联系服务器失败", error);
    //   }
    // )

    //优化1
    // fetch(`https://api.github.com/search/users?q=${keyWord}`).then(
    //   res => {
    //     console.log("联系服务器成功");
    //     if (res.ok) {
    //       return res.json();
    //     } else {
    //       throw new Error(res.statusText);
    //     }
    //   }).then(
    //     res => { console.log("获取数据成功", res); }
    //   ).catch(
    //     error => { console.log("请求出错", error.message); }
    //   )

    //优化2
    try {
      const res = await fetch(`https://api.github.com/search/users?q=${keyWord}`)

      if (res.ok) {
        const data = await res.json()
        PubSub.publish("abc", { isLoading: false, users: data.items })
      } else {
        throw new Error(res.statusText);
      }

    } catch (error) {
      PubSub.publish("abc", { isLoading: false, err: error.message })
    }


  }
  render() {
    return (
      <section className='jumbotron'>
        <h3 className='jumbotron-heading'>搜索github用户</h3>
        <div>
          <input ref={c => this.keyWordElement = c} type="text" placeholder='请输入关键词点击搜索' />&nbsp;
          <button onClick={this.search}>搜索</button>
        </div>
      </section>
    )
  }
}
