import React from 'react'
import { Link, Outlet, useNavigate } from 'react-router-dom'

const messages = [
  { id: '001', title: "消息1", content: "内容1" },
  { id: '002', title: "消息2", content: "内容2" },
  { id: '003', title: "消息3", content: "内容3" },
  { id: '004', title: "消息4", content: "内容4" },
]
export default function Message() {

  const navigate = useNavigate()

  const showDetail = m => {
    navigate('detail', { replace: false, state: m })
  }
  return (
    <div>
      <ul>
        {
          messages.map(m => <li key={m.id}>
            <Link to="detail" state={m}>{m.title}</Link>&nbsp;&nbsp;
            <button onClick={() => showDetail(m)}>查看</button>
          </li>)
        }
      </ul>
      <hr />
      <Outlet />
    </div>
  )
}
