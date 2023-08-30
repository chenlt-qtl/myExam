import React, { Component } from 'react'
import { Route, Switch } from 'react-router-dom'
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
              <MyNavLink to="/13/about" title="About" ></MyNavLink>
              <MyNavLink to="/13/home/a/b" title="Home" ></MyNavLink>
            </div>
          </div>
          <div className='col-xs-6'>
            <div className='panel'>
              <div className='panel-body'>
                {/* 注册路由 */}
                {/* 加上switch后,匹配到路由后就不会再接着匹配，不然会把所有匹配到的都展示出来 */}
                <Switch> 
                  <Route exact path='/13/about' component={About} />
                  <Route exact path='/13/home' component={Home} />
                </Switch>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

