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
            <Link replace to={`/19/home/message/detail/${id}/${title}`}>{title}(replace)</Link>
          </li>))}
        </ul>
        <hr />
        {/* 无需声明接收 */}
        <Route path="/19/home/message/detail/:id/:title" component={Detail}></Route>
      </div>
    )
  }
}
