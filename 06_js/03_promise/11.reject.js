//Promise.reject(reason)方法也会返回一个新的 Promise 实例，该实例的状态为rejected

let p1 = Promise.reject('error1');
// 等同于
let p2 = new Promise((resolve, reject) => reject('error2'))//生成一个 Promise 对象的实例p，状态为rejected，回调函数会立即执行。

p1.then(null, function (s) {
    console.log(s)
});


p2.catch(e => console.log(e))
