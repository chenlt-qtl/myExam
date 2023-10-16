import React, { Component } from 'react'
import Count from './components/Count'
import Person from './components/Person'
import store from "./redux/store"
import { Provider } from 'react-redux'

export default class index extends Component {

  render() {
    return (
      /**
       * 此处需要用Provider包裹App,目的是让APP所有的后代容器组件都能接收到store
       */
      <Provider store={store}>
        <h2>31. react redux 最终版</h2>
        <Count />
        <hr />
        <Person></Person>
      </Provider>
    )
  }
}

