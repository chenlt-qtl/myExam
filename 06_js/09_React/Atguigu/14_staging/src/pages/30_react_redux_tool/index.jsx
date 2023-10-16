import React, { Component } from 'react'
import Count from './components/Count'
import Person from './components/Person'
import store from "./redux/store"
import { Provider } from 'react-redux'

export default class index extends Component {

  render() {
    return (
      <Provider store={store}>
        <h2>30. react redux 开发工具</h2>
        <Count />
        <hr />
        <Person></Person>
      </Provider>
    )
  }
}

