//用于将多个 Promise 实例，包装成一个新的 Promise 实例

//全部成功才成功，一个失败就算失败

//成功时所有promise的返回值组成一个数组，传递给all的回调函数

//失败时第一个reject的实例的返回值，传给all的reject函数

//作为参数的 Promise 实例，自己定义了catch方法，那么它一旦被rejected，并不会触发Promise.all()的catch方法。


//例1 有自己的catch
const p1 = new Promise((resolve, reject) => {
    resolve('hello');
}).then(result => result)
    .catch(e => e);

const p2 = new Promise((resolve, reject) => {
    throw new Error('报错了');
}).then(result => result)
    .catch(e => e);

Promise.all([p1, p2])
    .then(result => console.log(result))//这个会打印
    .catch(e => console.log(e));//不会打印错误


//例2 没有自己的catch
const p3 = new Promise((resolve, reject) => {
    resolve('hello');
}).then(result => result);

const p4 = new Promise((resolve, reject) => {
    throw new Error('报错了');
}).then(result => result);

Promise.all([p3, p4])
    .then(result => console.log(result))//不打印这个
    .catch(e => console.log(e));//打印错误