import { Button ,Switch } from 'antd'
import React, { Component } from 'react'

export default class Demo extends Component {

  render() {

    return (
      <div>
        <button>点我</button>
        <Button type='primary'>primary button</Button>
        <Switch defaultChecked />
      </div>
    )
  }
}

