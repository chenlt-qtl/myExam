import React, { Component } from 'react'
import { withRouter } from 'react-router-dom'

class Header extends Component {
  back = () => {
    this.props.history.goBack();
  }
  forward = () => {
    this.props.history.goForward();
  }
  go = () => {
    this.props.history.go(-2);
  }
  render() {
    console.log("普通组件的props", this.props);
    return (

      <div className="page-header">
        <h2>React Router Demo</h2>
        <div style={{ display: "flex", gap: "20px" }}>
          <button onClick={this.back}>回退</button>
          <button onClick={this.forward}>前进</button>
          <button onClick={this.go}>go</button>
        </div>
      </div>

    )
  }
}

export default withRouter(Header)