
//声明完变量直接进行赋值

let c:boolean=false;
//如果变量声明和赋值是同时进行的，TS可以自动对变量进行类型检测
let c1 = false
c1 = true

//函数
function sum(a:number,b:number):number{
    return a+b;
}

sum(123,456)
// sum(123,"456")