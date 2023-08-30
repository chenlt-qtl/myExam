import React, { Component } from 'react'
import { Route, Link } from 'react-router-dom'
import About from '../About' //路由组件
import Home from '../Home' //路由组件
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

              <Link className="list-group-item" to="/08/about">About</Link>
              <Link className="list-group-item" to="/08/home">Home</Link>
            </div>
          </div>
          <div className='col-xs-6'>
            <div className='panel'>
              <div className='panel-body'>
                {/* 注册路由 */}
                <Route path='/08/about' component={About} />
                <Route path='/08/home' component={Home} />
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

