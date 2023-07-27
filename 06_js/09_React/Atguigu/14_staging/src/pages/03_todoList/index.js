import React, { Component } from 'react'
import Header from '../../components/03/Header';
import Footer from '../../components/03/Footer';
import List from '../../components/03/List';
import './styles.css'

export default class App03 extends Component {
  state = {
    todos: [
      { id: "001", name: "吃饭", done: true },
      { id: "002", name: "整理", done: false },
      { id: "003", name: "做任务", done: true },
    ]
  }

  // addTodo用于添加一个todo,接收的参数是todo对象
  addTodo = todoObj => {
    const { todos } = this.state;
    //追加一个todo
    const newTodos = [todoObj, ...todos];
    this.setState({ todos: newTodos })
  }

  // updateTodo用于更新一个todo对象
  updateTodo = (id, done) => {
    //获取状态中的todos
    const { todos } = this.state;
    //匹配处理数据
    const newTodos = todos.map(todo => {
      if (todo.id === id) {
        return { ...todo, done }
      } else {
        return todo
      }
    })
    this.setState({ todos: newTodos })
  }

  deleteTodo = id => {
    const { todos } = this.state;
    const newTodos = todos.filter(i=>i.id!=id)
    this.setState({ todos: newTodos })
  }


  //全选全不选
  checkAllTodo = done => {
    console.log("done", done);
    const { todos } = this.state
    const newTodos = todos.map(todo => ({ ...todo, done }))
    this.setState({ todos: newTodos })
  }

  //清除所有已完成的
  clearAllDone = () => {
    const { todos } = this.state
    const newTodos = todos.filter(todo => !todo.done)
    this.setState({ todos: newTodos })
  }


  render() {
    console.log(this.state);
    const { todos } = this.state;
    return (
      <div className='todo-container'>
        <Header addTodo={this.addTodo}></Header>
        <List todos={todos} updateTodo={this.updateTodo} deleteTodo={this.deleteTodo} />
        <Footer todos={todos} checkAllTodo={this.checkAllTodo} clearAllDone={this.clearAllDone}></Footer>
      </div>
    )
  }
}

