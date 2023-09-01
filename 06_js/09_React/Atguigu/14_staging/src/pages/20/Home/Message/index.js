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


  paramsShow = (id, title, type) => {
    if (type == "push") {
      //push跳转
      this.props.history.push(`/20/home/message/detail/${id}/${title}`)
    } else {
      //replace跳转
      this.props.history.replace(`/20/home/message/detail/${id}/${title}`)
    }
  }

  stateShow = (id, title, type) => {
    if (type == "push") {
      //push跳转
      this.props.history.push(`/20/home/message/detail`, { id, title })
    } else {
      //replace跳转
      this.props.history.replace(`/20/home/message/detail/`, { id, title })
    }
  }

  render() {
    const { messageArr } = this.state;
    return (
      <div>
        {/* Params */}
        <ul>
          {messageArr.map(({ id, title }) => (<li key={id} style={{ display: 'flex', gap: "10px" }}>
            <Link to={`/20/home/message/detail/${id}/${title}`}>{title}(replace)</Link>
            <button onClick={() => this.paramsShow(id, title, "push")}>push查看</button>
            <button onClick={() => this.paramsShow(id, title, "replace")}>replace查看</button>
          </li>))}
        </ul>
        {/* State */}
        {/* <ul>
          {messageArr.map(({ id, title }) => (<li key={id} style={{ display: 'flex', gap: "10px" }}>
          <Link to={{ pathname: `/20/home/message/detail`, state: { id, title } }}>{title}</Link>
            <button onClick={() => this.stateShow(id, title, "push")}>push查看</button>
            <button onClick={() => this.stateShow(id, title, "replace")}>replace查看</button>
          </li>))}
        </ul> */}
        <hr />
        {/* params */}
        <Route path="/20/home/message/detail/:id/:title" component={Detail}></Route>
        {/* state */}
        {/* <Route path="/20/home/message/detail" component={Detail}></Route> */}
      </div>
    )
  }
}
