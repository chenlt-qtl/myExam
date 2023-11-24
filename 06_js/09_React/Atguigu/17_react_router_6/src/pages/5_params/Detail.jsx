import React from 'react'
import { useParams } from 'react-router-dom'

export default function Detail() {
    const { id, title, content } = useParams()
    return (
        <ul>
            <li>商品编号：{id}</li>
            <li>商品标题：{title}</li>
            <li>商品内容：{content}</li>
        </ul>
    )
}
