import React from 'react'
import { NavLink, Outlet } from 'react-router-dom'

export default function Home() {
  return (
    <div>
      <h3>Home</h3>
      <div>
        <ul className='nav nav-tabs'>
          <li><NavLink className="list-group-item" to="news">News</NavLink></li>
          {/* 加上end后，当子路由active时，父路由不会active */}
          <li><NavLink className="list-group-item" end to="message">Message</NavLink></li>
        </ul>
        <Outlet></Outlet>
      </div>
    </div>
  )
}
