import React, { Component } from 'react'
import Count from './containers/Count'
import store from "./redux/store"
import { Provider } from 'react-redux'

export default class index extends Component {

  render() {
    return (
      <Provider store={store}>
        <Count />
      </Provider>
    )
  }
}

