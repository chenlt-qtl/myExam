//finally()方法用于指定不管 Promise 对象最后状态如何，都会执行的操作

//finally方法的回调函数不接受任何参数，不依赖于promise执行的结果

//finally本质上是then方法的特例
//源代码
// Promise.prototype.finally = function (callback) {
//     let P = this.constructor;
//     return this.then(
//       value  => P.resolve(callback()).then(() => value),
//       reason => P.resolve(callback()).then(() => { throw reason })
//     );
//   };
  

//finally方法总是会返回原来的值

