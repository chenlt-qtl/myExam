import React, { Component } from 'react'
import './styles.css'

export default class Item extends Component {
    state = { mouse: false }

    //使用JS控制鼠标hover
    handleMouse = flag => {
        return () => {
            this.setState({ mouse: flag })
        }
    }


    //勾选、取消勾选某个todo的回调
    handleCheck = id => {
        return e => {
            this.props.updateTodo(id, e.target.checked)
        }
    }

    handleDelete = id=>{
        if(window.confirm("确定删除吗？")){
            this.props.deleteTodo(id)
        }
    }

    render() {
        const { id, name, done } = this.props;
        const { mouse } = this.state
        return (
            <li style={{ backgroundColor: mouse ? "#ddd" : "white" }} onMouseEnter={this.handleMouse(true)} onMouseLeave={this.handleMouse(false)}>
                <label>
                    <input type='checkbox' checked={done} onChange={this.handleCheck(id)}></input>
                    <span>{name}</span>
                </label>
                <button className='btn btn-danger' onClick={()=>this.handleDelete(id)} style={{ display: mouse ? "inline-block" : "none" }}>删除</button>
            </li>
        )
    }
}
