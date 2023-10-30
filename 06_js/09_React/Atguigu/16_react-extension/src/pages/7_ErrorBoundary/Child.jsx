import React, { useState } from 'react'
import SetState from '../1_setState'

export default function Child() {
    // const [users,setUsers] = useState([
    //     {id:'001',name:'tom',age:18},
    //     {id:'002',name:'jack',age:19},
    //     {id:'003',name:'mike',age:20},])
    const [users, setUsers] = useState("abc")
    return (
        <div>
            <h3>Child</h3>
            {users.map(user => <h4 key={user.id}>{user.name}--{user.age}</h4>)}
        </div>
    )
}
