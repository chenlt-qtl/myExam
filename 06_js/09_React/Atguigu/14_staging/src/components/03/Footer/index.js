import React, { Component } from 'react'
import styles from './styles.css'

export default class Footer extends Component {

  //全选全不选
  handleCheckAll = e => {
    this.props.checkAllTodo(e.target.checked)
  }

  render() {
    const { clearAllDone, todos } = this.props;
    //已完成的个数
    const doneCount = todos.reduce((total, curent) => total + (curent.done ? 1 : 0), 0);
    //总数
    const total = todos.length;
    return (
      <div className='todo-footer'>
        <label>
          <input type='checkbox' onChange={this.handleCheckAll} checked={doneCount === total && total != 0} ></input>
        </label>
        <span>
          <span>已完成{doneCount}</span>/全部{total}
        </span>
        <button className='btn btn-danger' onClick={clearAllDone}>清除已完成</button>
      </div>
    )
  }
}
