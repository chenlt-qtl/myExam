import React, { Component } from 'react'

export default class Parent extends Component {
    render() {
        return (
            <div className='box'>
                <h3>Parent</h3>
                <A render={data => <B data={data} />}>
                    <C />
                </A>
            </div>
        )
    }
}


class A extends Component {
    state = { name: "tom" }
    render() {
        return (
            <div className='box'>
                A
                {/* renderProps形式 */}
                {this.props.render(this.state.name)}
                {/* children形式 */}
                {this.props.children}
            </div>
        )
    }
}

class B extends Component {
    render() {
        return (
            <div className='box'>
                B
                <div>父组件传递的参数:{this.props.data}</div>
            </div>
        )
    }
}
class C extends Component {
    render() {
        return (
            <div className='box'>
                C
            </div>
        )
    }
}