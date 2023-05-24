//只要参数实例有一个变成fulfilled状态，包装实例就会变成fulfilled状态；如果所有参数实例都变成rejected状态，包装实例就会变成rejected状态。

////如果所有操作都reject,会抛出错误,抛出的错误是一个 AggregateError 实例,这个 AggregateError 实例对象的errors属性是一个数组，包含了所有成员的错误。

//例1
const test1 = async () => {
    const promises = [
        fetch('/endpoint-a').then(() => 'a'),
        fetch('/endpoint-b').then(() => 'b'),
        fetch('/endpoint-c').then(() => 'c'),
    ];

    try {
        const first = await Promise.any(promises);
        console.log(first);
    } catch (error) {
        console.log(error);
    }
}

test1();

//例2
var resolved = Promise.resolve(42);
var rejected = Promise.reject(-1);
var alsoRejected = Promise.reject(Infinity);

Promise.any([resolved, rejected, alsoRejected]).then(function (result) {
    console.log("result:", result); // 42
});

Promise.any([rejected, alsoRejected]).catch(function (results) {
    console.log("instanceof AggregateError:", results instanceof AggregateError); // true
    console.log("errors:", results.errors); // [-1, Infinity]
});
