import { Button } from 'antd';
import React, { useState, useRef } from 'react'

export default function Count() {

  const [count, setCount] = useState(0);

  const numRef = useRef();

  const increment = () => {
    const number = numRef.current.value || 0;
    setCount(count + number * 1)
  }

  const decrement = () => {
    const number = numRef.current.value || 0;
    setCount(count - number * 1)
  }

  /**
   * 奇数再加
   */
  const incrementIfOdd = () => {
    if (count % 2 !== 0) {
      const number = numRef.current.value || 0;
      setCount(count + number * 1)
    }
  }

  const incrementAsync = () => {
    setTimeout(() => {
      const number = numRef.current.value || 0;
      setCount(count + number * 1)
    }, 1000)

  }

  return (
    <div style={{ padding: "30px", display: "flex", flexDirection: "column", gap: "20px" }}>
      <h4>当前求和为:{count}</h4>
      <select ref={numRef} style={{ width: "100px" }}>
        <option value={1}>1</option>
        <option value={2}>2</option>
        <option value={3}>3</option>
      </select>
      <div style={{ display: "flex", gap: "10px" }}>
        <Button onClick={increment}>+</Button>
        <Button onClick={decrement}>-</Button>
        <Button onClick={incrementIfOdd}>奇数+</Button>
        <Button onClick={incrementAsync}>异步+</Button>
      </div>
    </div>
  )
}
