import { DECREMENT, INCREMENT } from "../constant";

/**
 * 
 * 1. 该文件是用于创建一个为count组件服务的reducer, reducer的本质就是一个函数
 * 2. reducer函数会接到两个参数，分别为:之前的状态，动作对象
 */
const initState = 0
export default function countReducer(preState=initState,action){
    console.log(preState,action);
    const {type,data} = action;
    switch(type){
        case INCREMENT:
            return preState + data
        case DECREMENT:
            return preState - data
        default:
            return preState
    }
}