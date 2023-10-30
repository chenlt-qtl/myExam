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

5. render props
    1. 如果向组件内部动态传入带内容的结构？
        1. 使用children props:通过组件标签体传入结构
            <A>
                <B/>
            </A>
            A无法传递数据给B
        2. 使用render props:通过组件标签属性传入结构，而且可以携带参数，一般用render函数属性
            <A render={data=><B data={data/}>}/>

6. 错误边界
    

4. React Router 6               
    1. 包：
        - react-router 路由的核心库，提供了很多的组件、钩子
        - react-router-dom 包含react-router所有内容，并添加一些专门用于DOM的组件，例如<BrowserRouter>等
        - react-router-native 包括react-router所有内容，并添加一些专门用于ReactNative的API，如<NativeRouter>等
    2. 与5.x相比，改变了什么？
        1. 移除<Switch>,新增<Routes>
        2. 语法的变化：component={About}变为element={<About/>}等
        3. 新增多个hook: useParams, useNavigate, useMatch等
        4. 官方明确推荐函数式组件了