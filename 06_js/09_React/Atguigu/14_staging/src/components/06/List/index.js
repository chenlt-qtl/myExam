import React, { Component } from 'react'
import './styles.css'
import PubSub from 'pubsub-js'

export default class List extends Component {

  state={
    users:[],
    isFirst:true,//是否为第一次打开页面
    isLoading:false,//标识是否处于加载中
    err:"",//请求出错时的信息
  }

  componentDidMount(){
    this.token = PubSub.subscribe("abc",(_,stateObj)=>{
      this.setState(stateObj)
    })
  }

  componentWillUnmount(){
    PubSub.unsubscribe();
  }

  render() {
    const { users, isFirst, isLoading, err } = this.state
    return (
      <div className='row'>
        {isFirst ? <h2>欢迎使用，输入关键字，然后点击搜索</h2> :
          isLoading ? <h2>Loading...</h2> :
            err ? <h2>{err}</h2> :
              users.map(user => (
                <div key={user.id} className='card'>
                  <a rel="noreferrer" href={user.html_url} target='_blank'>
                    <img alt="head_portrait" src={user.avatar_url} style={{ width: "100px" }} />
                  </a>
                  <p className='card-text'>{user.login}</p>
                </div>
              ))
        }

      </div>
    )
  }
}
