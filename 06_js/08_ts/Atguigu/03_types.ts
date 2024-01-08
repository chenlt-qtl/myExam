{
    //字面量类型
    let a: 10 = 10;
    // a=5;
    let b: "male" | "female";
    b = "male"
    b = "female"
    // b="test"

    let c: boolean | string;
    c = true
    c = 'hello'

    //any 相当于关闭了于该变量的TS类型检测
    // let d: any;

    //隐式any  声明变量不指定类型，TS解析器会自动判断变量的类型为any
    let d;
    d = "hello"
    d = true
    d = 10;
    c = d;


    //unknown 表示未知的类型 是一个类型安全的any
    //unknown 类型的变量，不能直接赋值给其他变量
    let e: unknown;
    e = true
    e = 10
    e = "hello"
    // c=e;  不能直接赋值
    if (typeof e == "string") {
        c = e;
    }

    c = e as string
    c = <string>e

    //void用来表示空
    function fun(): void {
        // return 123;
    }

    //never用来表示永远不会返回结果
    function fun1(): never {
        throw new Error('报错了！');
    }

    //对象
    let obj: { name: string };
    obj = { name: "Tom" }

}
