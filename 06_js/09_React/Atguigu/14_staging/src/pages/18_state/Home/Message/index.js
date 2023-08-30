import React, { Component } from 'react'
import { Link, Route } from 'react-router-dom'
import Detail from './Detail'

export default class Message extends Component {
  state = {
    messageArr: [
      { id: "01", title: "message01" },
      { id: "02", title: "message02" },
      { id: "03", title: "message03" }]
  }

  render() {
    const { messageArr } = this.state;
    return (
      <div>
        <ul>
          {messageArr.map(({ id, title }) => (<li key={id}>
            <Link to={`/17/home/message/detail?id=${id}&title=${title}`}>{title}</Link>
          </li>))}
        </ul>
        <hr />
        <Route path="/17/home/message/detail" component={Detail}></Route>
      </div>
    )
  }
}
