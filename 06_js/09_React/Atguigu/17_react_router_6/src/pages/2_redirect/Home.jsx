import React, { useState } from 'react'
import { Navigate } from 'react-router-dom'

export default function Home() {
  const [sum, setSum] = useState(1)
  return (
    <div>
      <h3>Home</h3>
      <div>sum的值是{sum}</div>
      <button onClick={() => setSum(2)}>变成2</button>
      {sum == 2 ? <Navigate to={"/02/about"} /> : ""}
    </div>
  )
}
