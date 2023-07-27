import axios from 'axios'
import React, { Component } from 'react'

export default class App04 extends Component {



  getStudentData = () => {
    //代理方法一
    //在package.json中加入"proxy":"http://localhost:5000"
    // const url = 'http://localhost:3008/students'


    //代理方法二
    const url = 'http://localhost:3008/api1/students' 
    axios.get(url).then(
      response => { console.log("成功了", response.data); },
      error => { console.log("失败了", error); }
    )
  }

  getCarData = () => {
    axios.get('http://localhost:3008/api2/cars').then(
      response => { console.log("成功了", response.data); },
      error => { console.log("失败了", error); }
    )
  }
  render() {

    return (
      <div>
        <button onClick={this.getStudentData}>获取学生数据</button>&nbsp;
        <button onClick={this.getCarData}>获取汽车数据</button>
      </div>
    )
  }
}

