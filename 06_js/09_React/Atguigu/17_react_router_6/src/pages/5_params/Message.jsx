import React from 'react'
import { Link, Outlet } from 'react-router-dom'

const messages = [
    {id:'001',title:"消息1",content:"内容1"},
    {id:'002',title:"消息2",content:"内容2"},
    {id:'003',title:"消息3",content:"内容3"},
    {id:'004',title:"消息4",content:"内容4"},
]
export default function Message() {

  return (
    <div>
        <ul>
            {
                messages.map(({id,title,content})=><li key={id}>
                  <Link to={`detail/${id}/${title}/${content}`}>{title}</Link>
                </li>)
            }
        </ul>
        <hr/>
        <Outlet/>
    </div>
  )
}
