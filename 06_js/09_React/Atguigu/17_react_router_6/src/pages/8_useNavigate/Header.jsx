import React from 'react'
import { useNavigate } from 'react-router-dom'

export default function Header() {

    const navigate = useNavigate()

    const back = () => {
        navigate(-1)
    }

    const forword = () => {
        navigate(1)
    }
    return (
        <div className='col-xs-offset-2 col-xs-8'>
            <div className='page-header'>
                <h2>React Router Demo</h2>
            </div>
            <button onClick={back}>{"<"}后退</button>&nbsp;
            <button onClick={forword}>前进{">"}</button>
            <br />
            <br />
        </div>
    )
}
