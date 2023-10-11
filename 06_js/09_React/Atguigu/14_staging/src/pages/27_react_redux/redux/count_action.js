/*
    该文件专门为count组件生成action对象
*/
import { INCREMENT, DECREMENT } from './constant';
import store from './store';

export const createIncrementAction = data => ({ type: INCREMENT, data })
export const createDecrementAction = data => ({ type: DECREMENT, data })

//异步action：action的值为Object类型的一般对象, 异步action中一般都会调用同步action,异步action不是必须要用的
export const createIncrementAsyncAction = (data,time)=>{
    return ()=>{
        setTimeout(()=>{
            store.dispatch(createIncrementAction(data))
        },time)
    }
}
