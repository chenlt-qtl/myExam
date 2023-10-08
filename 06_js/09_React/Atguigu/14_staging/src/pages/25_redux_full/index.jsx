import React, { Component } from 'react'
import Count from './components/Count'
import store from "./redux/store"

export default class index extends Component {

  componentDidMount() {
    //检测redux中状态的变化，只要变化，就调用render
    store.subscribe(() => {
      this.setState({})
    })
  }

  render() {
    return (
      <Count />
    )
  }
}

