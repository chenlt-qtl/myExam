import axios from 'axios'
import React, { Component } from 'react'
import Search from '../../components/07/Search'
import List from '../../components/07/List'

export default class App06 extends Component {

  render() {

    return (
      <div className='container'>
        <Search/>
        <List/>
      </div>
    )
  }
}

