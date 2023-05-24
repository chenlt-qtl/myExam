//加载图片
const preloadImage = function (path) {
    return new Promise(function (resolve, reject) {
        const image = new Image();
        image.onload = resolve;
        image.onerror = reject;
        image.src = path;
    });
};

preloadImage("http://42.192.15.59/upload/note/damu/20230112/1673491688905.png")
    .then(result => console.log(result))
    .catch(error => console.log(error))




//Generator 函数与 Promise 的结合

//使用 Generator 函数管理流程，遇到异步操作的时候，通常返回一个Promise对象。

function getFoo() {
    return new Promise(function (resolve, reject) {
        resolve('foo');
    });
}

const g = function* () {
    try {
        const foo = yield getFoo();
        console.log(foo);
    } catch (e) {
        console.log(e);
    }
};

function run(generator) {
    const it = generator();

    function go(result) {
        if (result.done) return result.value;

        return result.value.then(function (value) {
            return go(it.next(value));
        }, function (error) {
            return go(it.throw(error));
        });
    }

    go(it.next());
}

run(g);
