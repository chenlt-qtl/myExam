import React, { Component } from 'react'
import store from "../../redux/store"
import { Button } from 'antd'

export default class Count extends Component {

    increment = () => {
        const { value } = this.selectNumber;
        store.dispatch({ type: "increment", data: value * 1 })
    }

    decrement = () => {
        const { value } = this.selectNumber;
        store.dispatch({ type: "decrement", data: value * 1 })
    }

    incrementIfOdd = () => {
        const { value } = this.selectNumber;
        if (store.getState() % 2 != 0) {
            store.dispatch({ type: "increment", data: value * 1 })
        }
    }


    incrementAsync = () => {

        setTimeout(() => {
            const { value } = this.selectNumber;
            store.dispatch({ type: "increment", data: value * 1 })
        }, 1000);
    }

    render() {
        return (
            <div style={{padding:"20px"}}>
                <h1>当前求和为:{store.getState()}</h1>
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
