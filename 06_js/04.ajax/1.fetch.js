//fetch返回一个promise对象，resolve的参数是response

//fetch的第一个then执行第一个resolve回调函数，参数为response对象

//response.json()返回一个promise,resolve的参数是将文本体解析为json

//所以要两次then才能取出fetch中的数据

//如果作为参数的 Promise 实例，自己定义了catch方法，那么它一旦被rejected，并不会触发Promise.all()的catch方法。

//例1
const p1 = new Promise((resolve, reject) => {
    resolve('hello');
})
    .then(result => result)
    .catch(e => e);

const p2 = new Promise((resolve, reject) => {
    throw new Error('报错了');
})
    .then(result => result)
    .catch(e => e);

Promise.all([p1, p2])
    .then(result => console.log(result))
    .catch(e => console.log(e));