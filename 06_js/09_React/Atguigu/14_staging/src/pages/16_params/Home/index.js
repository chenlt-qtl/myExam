import React, { Component } from 'react'
import MyNavLink from '../../../components/10/MyNavLink'
import { Switch, Route, Redirect } from 'react-router-dom'
import News from "./News"
import Message from "./Message"

export default class Home extends Component {
  render() {
    return (
      <>
        <div style={{padding:"10px"}}>我是Home</div>
        <div>
          <ul className='nav nav-tabs'>
            <li>
              <MyNavLink to="/16/home/news" title="News"/>
            </li>
            <li>
              <MyNavLink to="/16/home/message" title="Message"/>
            </li>
          </ul>
          <Switch>
            <Route path="/16/home/news" component={News}></Route>
            <Route path="/16/home/message" component={Message}></Route>
            <Redirect to="/16/home/news" />
          </Switch>
        </div>
      </>
    )
  }
}
