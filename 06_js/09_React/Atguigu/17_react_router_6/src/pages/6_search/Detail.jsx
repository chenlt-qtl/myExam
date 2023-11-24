import React from 'react'
import { useParams, useSearchParams } from 'react-router-dom'

export default function Detail() {
    const [search,setSearch] = useSearchParams()
    const id = search.get("id")
    const title = search.get("title")
    const content = search.get("content")

    return (
        <ul>
            <li>商品编号：{id}</li>
            <li>商品标题：{title}</li>
            <li>商品内容：{content}</li>
            <li>
                <button onClick={()=>setSearch("id=888&title=abc&content=666")}>更新search参数</button>
            </li>
        </ul>
    )
}
