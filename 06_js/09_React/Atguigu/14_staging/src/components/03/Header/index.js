import React, { Component } from 'react'
import './styles.css'
import { nanoid } from 'nanoid'

export default class Header extends Component {

    handleKeyUp = e => {
        //解构赋值获取keyCode target
        const { keyCode, target } = e;

        //如果不是回车则返回
        if (keyCode !== 13) {
            return
        }

        //检验不能为空
        if (target.value.trim() === "") {
            alert("输入不能为空")
            return
        }

        const todoObj = { id: nanoid(), name: target.value, done: false }
        this.props.addTodo(todoObj);
        //清空输入
        target.value = ""
    }

    render() {
        return (
            <div className='todo-header'>
                <input onKeyUp={this.handleKeyUp} type='text' placeholder='请输入你的任务名称，按回车键确认' />
            </div>
        )
    }
}
