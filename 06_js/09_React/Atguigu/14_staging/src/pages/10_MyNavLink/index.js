import React, { Component } from 'react'
import { Route,NavLink } from 'react-router-dom'
import About from '../About' //路由组件
import Home from '../Home' //路由组件
import MyNavLink from '../../components/10/MyNavLink'
import './styles.css'
import Header from '../../components/Header'  //一般组件

export default class App06 extends Component {

  render() {

    return (
      <div>
        <div className="row">
          <div className='col-xs-offset-2 col-xs-8'>
            <Header></Header>
          </div>
        </div>
        <div className="row">
          <div className='col-xs-2 col-xs-offset-2'>
            <div className='list-group'>
              {/* 在React中靠路由链接实现切换组件--编写路由链接 */}
              <MyNavLink to="/10/about" title="About" ></MyNavLink>
              <MyNavLink to="/10/home" title="Home" ></MyNavLink>
            </div>
          </div>
          <div className='col-xs-6'>
            <div className='panel'>
              <div className='panel-body'>
                {/* 注册路由 */}
                <Route path='/10/about' component={About} />
                <Route path='/10/home' component={Home} />
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

