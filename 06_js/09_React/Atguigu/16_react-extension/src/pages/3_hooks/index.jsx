import React, { Component, useEffect, useRef, useState } from 'react'

/**
 * 类式组件
 */
class Rcc extends Component {
    state = { count: 0 }

    myRef = React.createRef()

    componentDidMount() {
        this.timer = setInterval(() => {
            this.setState(({ count }) => ({ count: count + 1 }))
        }, 1000)
    }

    componentWillUnmount() {
        clearInterval(this.timer)
    }

    add = () => {
        const { count } = this.state
        this.setState({ count: count + 1 })
    }

    show = () => {
        alert(this.myRef.current.value)
    }

    render() {
        return (
            <div>
                <h4>类式组件</h4>
                <input type="text" ref={this.myRef}></input>
                <h3>当前count为:{this.state.count}</h3>
                <br />
                <input type='button' value={"加1"} onClick={this.add}></input>
                <input type='button' value={"显示"} onClick={this.show}></input>
            </div>
        )
    }
}


/**
 * 
 * @returns 函数式组件
 */
function Hooks() {
    const [count, setCount] = useState(0)

    const myRef = useRef()

    const add = () => {
        setCount(count + 1)
    }

    const show = () => {
        alert(myRef.current.value)
    }

    useEffect(() => {
        const timer = setInterval(() => {
            setCount(count => count + 1)
        }, 1000)

        return () => {
            clearInterval(timer)
        }
    }, [])
    return (
        <div>
            <h4>函数式组件</h4>
            <input type="text" ref={myRef}></input>
            <h3>当前count为:{count}</h3>
            <br />
            <input type='button' value={"加1"} onClick={add}></input>
            <input type='button' value={"显示"} onClick={show}></input>
        </div>
    )
}

export default () => <>
    <Hooks /><hr /><Rcc />
</>