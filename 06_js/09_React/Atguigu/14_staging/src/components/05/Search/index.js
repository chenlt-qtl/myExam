import React, { Component } from 'react'
import axios from 'axios'

export default class Search extends Component {

  search = () => {
    const { updateAppState } = this.props;
    //通过ref获取用户的输入
    const { keyWordElement: { value: keyWord } } = this;
    if (!keyWord) {
      updateAppState({ isFirst: false, isLoading: false, err: "请输入关键字" })
      return;
    } else {
      updateAppState({ err: "" })
    }
    //发送请求前通知App更新状态
    updateAppState({ isFirst: false, isLoading: true })
    axios.get(`https://api.github.com/search/users?q=${keyWord}`).then(
      res => {
        //成功后更新app状态
        updateAppState({ isLoading: false, users: res.data.items })
      },
      error => {
        //失败后把失败原因返回给app
        updateAppState({ isLoading: false, err: error.message })
      }
    )
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
