1. setState的两种写法
    1. setState(newState,[callback]) //newState是个对象
        callback在更新完state和render后会被调用
    
    2. setState(updater,[callback]) //updater是个函数
        - updater返回一个新的state对象
        - updater可以接收到两个参数, state和props

2. lazy load

3. hooks
    1. 16.8.0版本增加的新特性/新语法，可以在函数组件中使用state以及其他的React特性
    1. effect hooks
        1. 作用：在函数组件中实现副作用操作（生命周期）
        2. 语法和说明
            useEffect(()=>{
                //在此可以执行任何带副作用操作
                return ()=>{ //在组件卸载前执行
                //在些做一些收尾工作，比如清除定时器/取消订阅等
                }

            },[id])//如果指定的是[],回调函数只会在第一次render后执行
        3. 可以把useEffect hook看成如下三个函数的组合
            componentDidMount()
            componentDidUpdate()
            componentWillUnmount()
    2. ref hook
        1. ref hook可以在函数组件中存储、查找组件内的标签或任意其它数据
        2. 语法: const ref = useRef()
        3. 作用:保存标签对象，功能与React.createRef一样
    3. useState
        1. 实现 state
        2. 语法： const [xxx,setXxx] = React.useState(initValue)
        3. useState说明:
            参数:初始值
            返回值：一个数组，第一个为状态值，第二个为更新状态的函数
        4. setXxx的2种写法
            setXxx(newValue)：直接指定新状态值
            setXxx(value=>newValue):接收原本的状态，返回的值作为新状态值

4. Context
    1. 作用: 常用于祖组件与后代组件间通信
    2. 使用：   
        1. 创建Context容器对象
            const MyContext = React.createContext()
        2. 渲染子组件时，外面包裹MyContext.Provider, 通过value属性给后代组件传递数据：
            <MyContext.Provider value={数据}>
                {子组件}
            </MyContext.Provider>
        3. 后代组件读取数据
            - 第一种方式，仅适用于类组件
                static contextType = MyContext //声明接收context
                this.context //读取context中的value数据
            - 第二种方式：函数组件与类组件都可以
                <MyContext.consumer>
                    {value =><h4>用户名:{value}</h4>}
                </MyContext.consumer>
    3. 注意
        在应用开发中一般不用context, 一般都用它封装react插件, 用来传递公共参数，如主题

5. PureComponent
    1. 只有当组件的state或props数据发生改变时才重新render
    2. 原理：重写了shouldComponentUpdate, 只有state或props数据有变化才返回true
    3. 注意：只进行state和props数据㳀比较，如果是对象内部数据变了，则视为没有变化，不要直接修改state，而是要返回新的数据

6. render props
    1. 如果向组件内部动态传入带内容的结构？
        1. 使用children props:通过组件标签体传入结构
            <A>
                <B/>
            </A>
            A无法传递数据给B
        2. 使用render props:通过组件标签属性传入结构，而且可以携带参数，一般用render函数属性
            <A render={data=><B data={data/}>}/>

7. 组件通信方式总结
    1. 通信方式：
        - props:
            1. children props
            2. render props
        - 消息订阅-发布
            pubs-sub、event等等
        - 集中式管理
            redux、dva等
        - conText
            生产者-消费者模式
    2. 比较好的搭配方式
        1. 父子：props
        2. 兄弟:消息订阅-发布，集中式管理
        3. 祖孙组件：消息订阅、集中式管理，context(开发用的少，封装插件用的多)
    
