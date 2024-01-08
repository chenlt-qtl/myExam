1. 当变量的声明和赋值是同时进行的，TS编译器会自动判断变量的类型，可以省略掉类型声明
2. 基本类型：
    - number 
    - string
    - boolean
    - 字面量：如 let a:"A"|"B",则a只能是"A"或者"B"
    - any:少用,除了霍霍自己还会霍霍别人
    - unknown:类型安全的any
    - void：不返回或返回空
    - never：函数不能返回任何东西，一般会抛异常
    - object
    - array
    - tuple:固定长度数组
    - enum:枚举
