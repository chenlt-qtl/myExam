import React from 'react'
import { NavLink, Outlet } from 'react-router-dom'

export default function Home() {
  return (
    <div>
      <h3>Home</h3>
      <div>
        <ul className='nav nav-tabs'>
          <li><NavLink className="list-group-item" to="news">News</NavLink></li>
          <li><NavLink className="list-group-item" to="message">Message</NavLink></li>
        </ul>
        <Outlet></Outlet>
      </div>
    </div>
  )
}
