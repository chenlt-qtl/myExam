//只要参数之中有一个实例率先改变状态，race的状态就跟着改变。那个率先改变的 Promise 实例的返回值，就传递给race的回调函数。

//如果 5 秒之内fetch方法无法返回结果，变量p的状态就会变为rejected
const p = Promise.race([
    fetch('/test.json'),
    new Promise(function (resolve, reject) {
        setTimeout(() => reject(new Error('request timeout')), 5000)
    })
]);

p.then(console.log).catch(console.error);
