
//Promise.prototype.catch()方法是.then(null, rejection)或.then(undefined, rejection)的别名，用于指定发生错误时的回调函数。

//Promise 对象的错误具有“冒泡”性质，会一直向后传递，直到被捕获为止。也就是说，错误总是会被下一个catch语句捕获。

//一般来说，不要在then()方法里面定义 Reject 状态的回调函数（即then的第二个参数），总是使用catch方法。

//catch()方法返回的还是一个 Promise 对象，因此后面还可以接着调用then()方法。

//例1
const promise1 = new Promise(function (resolve, reject) {
    throw new Error('test1');
});
promise1.catch(function (error) {
    console.log(error);
});


// 例2 bad
const promise2 = new Promise(function (resolve, reject) {
    try {
        throw new Error('test2');
    } catch (e) {
        reject(e);
    }
});
promise2.catch(function (error) {
    console.log(error);
});

// 例3 good
const promise3 = new Promise(function (resolve, reject) {
    reject(new Error('test3'));
});
promise3.catch(function (error) {
    console.log(error);
});


//例4
const someAsyncThing = function () {
    return new Promise(function (resolve, reject) {
        // 下面一行会报错，因为x没有声明
        resolve(x + 2);
    });
};

someAsyncThing()
    .catch(function (error) {//如果没有抛出错误，则不执行catch代码，接着执行then方法
        console.log('oh no', error);
        return "next"
    })
    .then(function (text) {
        console.log('carry on ' + text);
    });