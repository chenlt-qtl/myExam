import React, { Component } from 'react'
import { Button } from 'antd'
//connect用于连接UI组件与redux
import { connect } from 'react-redux'
import { createDecrementAction, createIncrementAction, createIncrementAsyncAction } from '../../redux/count_action'

class Count extends Component {

    increment = () => {
        const { value } = this.selectNumber;
        this.props.jia(value * 1)
    }

    decrement = () => {
        const { value } = this.selectNumber;
        this.props.jian(value * 1)
    }

    incrementIfOdd = () => {
        const { value } = this.selectNumber;
        if (this.props.count % 2 !== 0) {
            this.props.jia(value * 1)
        }
    }


    incrementAsync = () => {
        const { value } = this.selectNumber;
        this.props.jiaAsync(value * 1, 1000)
    }

    render() {
        return (
            <div style={{ padding: "20px" }}>
                <h1>28. 当前求和为:{this.props.count}</h1>
                <select ref={c => this.selectNumber = c}>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                </select>
                <hr />
                <Button onClick={this.increment}>+</Button>
                <Button onClick={this.decrement}>-</Button>
                <Button onClick={this.incrementIfOdd}>奇数再加</Button>
                <Button onClick={this.incrementAsync}>异步+</Button>
            </div>
        )
    }
}


//使用connect()()创建一个Count的容器组件
export default connect(
    state => ({ count: state }),
    {
        jia: createIncrementAction,
        jian: createDecrementAction,
        jiaAsync: createIncrementAsyncAction
    }
)(Count)