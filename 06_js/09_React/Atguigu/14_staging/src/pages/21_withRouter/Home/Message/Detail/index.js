import React, { Component } from 'react'

const DetailData = [
  { id: "01", content: "01的内容" },
  { id: "02", content: "02的内容" },
  { id: "03", content: "03的内容" },
]
export default class Detail extends Component {
  render() {
    //接收params参数
    const { id, title } = this.props.match.params

    //接收state参数
    // const { id, title } = this.props.location.state || {}

    const findResult = id => DetailData.find(data => data.id == id)

    return (
      <ul>
        <li>ID:{id}</li>
        <li>TITLE:{title}</li>
        <li>CONTENT:{findResult(id).content}</li>
      </ul>
    )
  }
}
