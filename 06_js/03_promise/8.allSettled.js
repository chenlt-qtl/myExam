//用来确定一组异步操作是否都结束了（不管成功或失败)

//该方法返回的新的 Promise 实例，一旦发生状态变更，状态总是fulfilled，不会变成rejected。

//状态变成fulfilled后，它的回调函数会接收到一个数组作为参数，该数组的每个成员对应前面数组的每个 Promise 对象。

//例1
const test = async () => {
    const promises = [
        fetch('/api-1'),
        fetch('/api-2'),
        fetch('/api-3'),
    ];

    await Promise.allSettled(promises);
    console.log("finish");
}
test();

//例2
const resolved = Promise.resolve(42);
const rejected = Promise.reject(-1);

const allSettledPromise = Promise.allSettled([resolved, rejected]);

allSettledPromise.then(function (results) {
    results.map(i => console.log(JSON.stringify(i)))
});

//例3
const test1 = async () => {
    const promises = [fetch('index.html'), fetch('https://does-not-exist/')];
    const results = await Promise.allSettled(promises);

    // 过滤出成功的请求
    const successfulPromises = results.filter(p => p.status === 'fulfilled');

    // 过滤出失败的请求，并输出原因
    const errors = results
        .filter(p => p.status === 'rejected')
        .map(p => console.log("reason:" + p.reason));
}
test1();
