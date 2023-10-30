import React, { Component, Suspense, lazy } from 'react'
import { Route, NavLink, Routes, Switch } from 'react-router-dom'
// import About from './About' 
// import Home from './Home' 
import Loading from './Loading'

//懒加载 引入组件
const Home = lazy(()=>import('./Home'))
const About = lazy(()=>import('./About'))

export default class LazyLoad extends Component {

    render() {

        return (
            <div style={{ padding: "50px" }}>
                <div className="row">
                    <div className='col-xs-2 col-xs-offset-2'>
                        <div className='list-group'>
                            {/* 在React中靠路由链接实现切换组件--编写路由链接 */}
                            <NavLink className="list-group-item" to="/02/about" >About</NavLink>
                            <NavLink className="list-group-item" to="/02/home" >Home</NavLink>
                        </div>
                    </div>
                    <div className='col-xs-6'>
                        <div className='panel'>
                            <div className='panel-body'>
                                {/* 注册路由 使用suspense包裹， fallback定义的组件在加载组件的时候显示*/}
                                <Suspense fallback={<Loading/>}>
                                    <Route path='/02/about' component={About} />
                                    <Route path='/02/home' component={Home} />
                                </Suspense>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

