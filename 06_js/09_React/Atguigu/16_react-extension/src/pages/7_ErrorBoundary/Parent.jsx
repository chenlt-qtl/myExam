
import React, { Component } from 'react'
import Child from './Child'


export default class Parent extends Component {
    state = { hasError: "" }

    //当Parent的子组件出现报错时，会触发getDerivedStateFromError调用，并携带错误信息
    //开发模式下还是会显示错误，打包后不会显示
    static getDerivedStateFromError(error){
        console.log("@@@",error);
        return {hasError:error}
    }

    componentDidCatch(){
        console.log("此处统计错误，反馈给服务器，用于通知编码人员进行BUG的解决");
    }

    render() {
        return (
            <div>
                <h3>Parent</h3>
                {this.state.hasError ? <h4>当前网络不稳定，请稍后再试</h4> : <Child />}
            </div>
        )
    }
}
