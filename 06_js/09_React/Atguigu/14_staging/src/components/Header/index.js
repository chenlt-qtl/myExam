import React, { Component } from 'react'

export default class Header extends Component {
  render() {
    console.log("普通组件的props", this.props);
    return (
        <div className="page-header"><h2>React Router Demo</h2></div>
    )
  }
}
