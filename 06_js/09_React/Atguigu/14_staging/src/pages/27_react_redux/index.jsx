import React, { Component } from 'react'
import Count from './components/Count'
import store from "./redux/store"

export default class index extends Component {

  render() {
    return (
      <Count store={store} />
    )
  }
}

