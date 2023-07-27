import axios from 'axios'
import React, { Component } from 'react'
import Search from '../../components/06/Search'
import List from '../../components/06/List'

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

