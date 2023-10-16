import React, { useRef } from 'react'
import { nanoid } from 'nanoid'
import { connect } from 'react-redux';
import { addPerson } from '../../redux/actions/person';

function Person(props) {


    const nameRef = useRef();
    const ageRef = useRef();

    const AddPerson = () => {
        const name = nameRef.current.value
        const age = ageRef.current.value
        const personObj = { id: nanoid(), name, age }
        props.addPerson(personObj);
        nameRef.current.value = ""
        ageRef.current.value = ""
    }

    return (
        <div style={{ padding: "20px" }}>
            <h3>Person组件,count求和为{props.count}</h3>
            <div>
                name: <input ref={nameRef}></input><br />
                age: <input ref={ageRef}></input><br />
                <button onClick={AddPerson}>Add</button>
            </div>
            <hr />
            <ul>
                {props.person.map(p => <li key={p.id}>{p.name}---{p.age}</li>)}
            </ul>
        </div>
    )
}

export default connect(({ count, person }) => ({ count, person }), { addPerson })(Person)