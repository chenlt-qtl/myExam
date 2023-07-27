import axios from 'axios'
import React, { Component } from 'react'
import Search from '../../components/05/Search'
import List from '../../components/05/List'

export default class App05 extends Component {
  state={
    users:[],
    isFirst:true,//是否为第一次打开页面
    isLoading:false,//标识是否处于加载中
    err:"",//请求出错时的信息
  }

  //更新App的state
  updateAppState = stateObj=>{
    this.setState(stateObj)
  }


  render() {

    return (
      <div className='container'>
        <Search updateAppState={this.updateAppState}/>
        <List {...this.state}/>
      </div>
    )
  }
}

