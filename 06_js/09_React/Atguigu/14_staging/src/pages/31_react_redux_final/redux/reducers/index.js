/**
 * 该文件用于汇总所有的reducer为一个总的reducer
 */
import { combineReducers } from 'redux'
//引入为count组件服务的reducer
import count from './count'
//引入为person组件服务的reducer
import person from './person'

export default combineReducers({ count, person })