import React, { Component } from 'react'
import { NavLink, Outlet } from 'react-router-dom'



export default class LazyLoad extends Component {

    render() {

        return (
            <div style={{ padding: "50px" }}>
                <div className="row">
                    <div className='col-xs-2 col-xs-offset-2'>
                        <div className='list-group'>
                            {/* 在React中靠路由链接实现切换组件--编写路由链接 */}
                            <NavLink className={({ isActive }) => 'list-group-item' + (isActive ? ' myActive' : "")} to="/03/about" >About</NavLink>
                            <NavLink className={({ isActive }) => 'list-group-item' + (isActive ? ' myActive' : "")} to="/03/home" >Home</NavLink>
                        </div>
                    </div>
                    <div className='col-xs-6'>
                        <div className='panel'>
                            <div className='panel-body'>
                                <Outlet />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

