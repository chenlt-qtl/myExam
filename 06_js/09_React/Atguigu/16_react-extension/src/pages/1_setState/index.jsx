import React, { Component } from 'react'

export default class SetState extends Component {
  state = { count: 0 }
  add = () => {
    const { count } = this.state
    //写法1
    this.setState({ count: count + 1 }, () => {
      console.log("new Count", this.state.count);
    })
  }

  add2 = () => {
    //写法2
    this.setState(({ count }) => ({ count: count + 2 }), () => {
      console.log("new Count", this.state.count);
    })


  }

  render() {
    return (
      <div>
        <h3>当前count为:{this.state.count}</h3>
        <br />
        <input type='button' value={"加1"} onClick={this.add}></input>&nbsp;
        <input type='button' value={"加2"} onClick={this.add2}></input>
      </div>
    )
  }
}

