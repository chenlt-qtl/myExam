// 有时需要将现有对象转为 Promise 对象，Promise.resolve()方法就起到这个作用




Promise.resolve('foo')
// 等价于
new Promise(resolve => resolve('foo'))


//例1

const p = Promise.resolve('Hello');

p.then(function (s) {
  console.log(s)
});