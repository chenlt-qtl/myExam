//引入createStore专门用于创建redux中最为核心的store对象
import { createStore, applyMiddleware } from 'redux'
//引入汇总后的reducer
import reducer from './reducers'
//引入redux-thunk,用于支持异步action
import thunk from 'redux-thunk'

import { composeWithDevTools } from 'redux-devtools-extension'



export default createStore(reducer, composeWithDevTools(applyMiddleware(thunk)))