//定义：Promise.prototype.then(),then 方法在原型对象上

//参数 ：一个参数是resolved状态的回调函数，第二个参数是rejected状态的回调函数，它们都是可选的。

//返回：then方法返回的是一个新的Promise实例（注意，不是原来那个Promise实例）

//例1
let promise1 = new Promise(function(resolve, reject) {
    console.log('Promise1');
    resolve('test1');
});

promise1.then(function(res) {//res是resolve的参数
    console.log('resolved '+res);
});

console.log('Hi!');

//例2
let promise3 = new Promise(function(resolve, reject) {
    console.log('Promise3');
    resolve('test2');
});

let promise2 = new Promise(function(resolve, reject) {
    console.log('Promise2');
    resolve(promise3);
});

promise2.then(function(res) {//res是resolve的参数
    console.log('resolved '+res);
});

