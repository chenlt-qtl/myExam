import React from 'react'
import { useLocation } from 'react-router-dom'

export default function Detail() {
    const { state:{ id, title, content } } = useLocation()

    return (
        <ul>
            <li>商品编号：{id}</li>
            <li>商品标题：{title}</li>
            <li>商品内容：{content}</li>
        </ul>
    )
}
