import React, { useState } from 'react'
import './styles.css'

const MyContext = React.createContext()
const { Provider, Consumer } = MyContext;
export default function A() {
    const [username, setUsername] = useState("tom")
    return (
        <div className='box'>
            <h3>A</h3>
            <h4>用户名:{username}</h4>
            <Provider value={username}>
                <B></B>
            </Provider>
        </div>
    )
}


function B() {
    return (
        <div className='box'>
            <h3>B</h3>
            <C></C>
        </div>
    )
}

function C() {

    return (
        <div className='box'>
            <h3>C</h3>
            <Consumer>
                {value =>
                    <h4>用户名:{value}</h4>
                }

            </Consumer>
        </div>
    )
}
