//引入createStore专门用于创建redux中最为核心的store对象
import {createStore,applyMiddleware, combineReducers} from 'redux'
//引入为count组件服务的reducer
import countReducer from './reducers/count'
//引入为person组件服务的reducer
import personReducer from './reducers/person'
//引入redux-thunk,用于支持异步action
import thunk from 'redux-thunk'

const allReducer = combineReducers({
    count:countReducer,
    person:personReducer
})

export default createStore(allReducer,applyMiddleware(thunk))