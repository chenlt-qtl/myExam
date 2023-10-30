import React, { PureComponent, Component } from 'react'

export default class Parent extends Component {
    state = { carName: '奔驰', users: ['小张', '小李', '小王'] }

    changeCar = () => {
        this.setState({ carName: "迈巴赫" })
    }
    add = () => {
        this.setState(({ users }) => ({ users: ["小陈", ...users] }))
    }

    render() {
        console.log("Parent---render");
        const { carName, users } = this.state
        return (
            <div className='box'>
                <h4>Parent</h4>
                <div>车名：{carName}</div>
                <div>人：{users}</div>
                <button onClick={this.changeCar}>换车</button>
                <button onClick={this.add}>加人</button>
                <Children users={users}></Children>
            </div>
        )
    }
}

/**
 * 如果这里使用Component，只要父组件有更新都会重新render
 * 如果这里使用PureComponent，只有用到的参数更新才会重新render
 */
class Children extends PureComponent {
    render() {
        console.log("Children---render");
        return (
            <div>
                <h4>Children</h4>
                <div>人：{this.props.users}</div>
            </div>
        )
    }
}

