## 新建项目
1. 安装工具：npm install -g create-react-app 
2. 切换到想创建项目的目录，create-react-app hello-react
3. cd hello-react
4. 启动:npm start

## todoList案例相关知识点
    1. 拆分组件、实现静态组件。注意：className,style的写法
    2. 动态初始化列表，如何确定将数据放在哪个组件的state中？
        - 某个组件使用：放在其自身的state中
        - 某个些组件使用：放在他们共同的父组件state中（官方称此操作为:状态提升）
    3. 关于父子之间通信
        1. 父组件给子组件传递数据：props
        2. 子组件给父组件传递数据：通过props传递，要求父提前给子传递一个函数
    4. 注意defaultChecked和checked的区别, 类似还有：defaultValue和value
    5. 状态在哪里，操作状态的方法就在哪里！！ 

## 代理

    1.方法一
    在package.json中加入"proxy":"http://localhost:5000"，本地找不到的链接就会转发到这个地址


    2.方法二
        1. 安装：npm install --save-dev http-proxy-middleware
        2. 增加 /src/setupProxy.js 文件
            const { createProxyMiddleware: proxy } = require('http-proxy-middleware')

            module.exports = function (app) {
                app.use(
                    proxy('/api1', {//遇到/api1前缀的请求，就会触发该代理配置
                        target: 'http://localhost:5000',//请求转发给5000
                        // changeOrigin: true,//控制服务器收到的请求头中Host字段的值
                        /**
                        * changeOrigin设置为true时，服务器收到的请求头中的host为:localhost:5000
                        * changeOrigin设置为false时，服务器收到的请求头中的host为:localhost:3008
                        * 默认false,一般要改成true
                        */
                        pathRewrite: { '^/api1': "" }//重写请求路径,去掉/api1前缀
                    })
                )
            }


## 二、github搜索案例相关知识点
    1. 设计状态时要考虑全面，例如带有网络请求的组件，要考虑请求失败怎么办。
    2. es6小知识点：解构赋值+重命名
        let obj = {a:{b:1}}
        const {a} = obj;//解构赋值
        const {a:{b}} = obj;//连续解构赋值
        const {a:{b:value}} = obj;//连续解构赋值+重命名
    3. 消息订阅与发布机制
        1. 先订阅，再发布
        2. 适用于任意组件间通信
        3. 要在组件的componentWillUnmount中取消订阅
    4. fetch发送请求(关注分离的设计思想)


## 三、路由的基本使用
    1. 明确好界面中的导航区，展示区
    2. 导航区的A标签改为Link标签
        <Link to="xxx">Demo</Link>
    3. 展示区写Router标签进行路径的匹配
        <Route path="/xxx" component={Demo}/>
    4. <App>的最外侧包裹了一个<BrowserRouter>或<HashRouter>


## 四、路由组件与一般组件
    1. 写法不同：
        一般组件：<Demo/>
        路由组件: <Route path="/demo" component={Demo}>
    2. 存放位置不同:
        一般组件: components
        路由组件: pages
    3. 接收到的props不同:
        一般组件: 写组件标签时传递了什么，就能接收到什么
        路由组件: 接收到三个固定的属性
        history: 
            go: function go(n)​​
            goBack: function goBack()​​
            goForward: function goForward()
            push: function push(path, state)​​
            replace: function replace(path, state)​​
            ​
        location:
            pathname: "/about"
            search: ""
            state: undefined

        match: 
            params: Object {  }
            path: "/about"
            url: "/about"

## 五、NavLink与封装NavLink
    1. NavLink 可以实现路由链接的高亮，通过activeClassName指定样式名
    2. 标签体内容是一个特殊的标签属性
    3. 通过this.props.children可以获取标签体内容

## 六、Switch的使用
    1. 通常情况下，path和component是一一对应的关系
    2. Switch可以提高路由匹配效率(单一匹配，跟find原理一样)

## 七、解决多级路径刷新页面样式丢失的问题
    1. public/index.html中引入样式时不写./写/(常用)
    2. public/index.html中引入样式时不写./写%PUBLIC_URL%（常用）
    3. 使用HashRouter

## 八、路由严格匹配与模糊匹配
    1. 默认使用的是模糊匹配
    2. 开启严格匹配:<Route exact={true} path="/about" component={About}/>
    3. 严格匹配不要随便开启，需要再开，有时候开启会导致无法继续匹配二级路由

## 九、Redirect的使用
    1. 一般写在所有路由注册的最下方 当所有路由都无法匹配时，跳转到Redirect指定的路由
    2. 具体编码：
        <Switch>
            <Route path='/13/about' component={About} />
            <Route path='/13/home' component={Home} />
            <Redirect to="/about"></Redirect>
        </Switch>

## 十、嵌套路由

    1. 注册子路由时要写上父路由的path值
    2. 路由的匹配是按照注册路由的顺序进行的，会先匹配先注册的路由

## 十一、向路由组件传递参数
    1. params参数
        路由链接携带参数:<Link to='/demo/test/tom/18'>详情</Link>
        注册路由声明接收:<Route path='/demo/test/:name/:age' component={Test}>
        接收参数：this.props.match.params
    2. search参数
        路由链接携带参数:<Link to='/demo/test?name=to&age=18'>详情</Link>
        注册路由无需声明接收:<Route path='/demo/test' component={Test}>
        接收参数：const {search} = this.props.location
        备注：获取到的search是urlencoded编码字符串，需要借助querystring解析
    3. state参数
        路由链接携带参数:<Link to={{ pathname: '/home/message/detail', state: { id:18 } }}>详情</Link>
        注册路由无需声明接收:<Route path='/demo/test' component={Test}>
        接收参数：this.props.location.state
        备注：刷新也可以保留住参数


## 十二、编程式路由导航
    借助this.props.history对象上的API操作路由跳转、前进、后退
        - this.props.history.push()
        - this.props.history.replace()
        - this.props.history.goBack()
        - this.props.history.goForward()
        - this.props.history.go()

## 十三、BrowserRouter和HashRouter的区别
    1. 底层原理不一样
        BrowserRouter使用的是H5的history API,不兼容IE9及以下版本
        HashRouter使用的是URL的哈希值
    2. url表现形式不一样
        BrowserRouter的路径中没有#
        HashRouter的路径包含#
    3. 刷新后对路由state参数的影响
        1. BrowserRouter没有任务影响，因为state保存在history对象中
        2. HashRouter刷新后会导致路由state参数的丢失
    4. 备注
        HashRouter可以用于解决一些路径错误相关的问题

## 十四、withRouter
    1. withRouter可以加工一般组件，让一般组件具备路由组件所特有的API
    2. withRouter的返回值是一个新组件
## 十五、antd按需引入
    1. 安装依赖: npm i --save react-app-rewired customize-cra babel-plugin-import less less-loader
    2. 修改package.json
        "scripts":{
            "start": "set PORT=3008 && react-app-rewired start",
            "build": "react-app-rewired build",
            "test": "react-app-rewired test",
        }
    3. 根目录下创建config-overrides.js
        //配置具体的修改规则
        const { override, fixBabelImports, addLessLoader } = require('customize-cra');
        module.exports = override(
            fixBabelImports('import', {
                libraryName: 'antd',
                libraryDirectory: 'es',
                style: true,
            }),
            //修改主题
            addLessLoader({
                lessOptions: {
                    javascriptEnabled: true,
                    modifyVars: { '@primary-color': '#1DA57A' },
                }
            }),
        );
    4. 备注：不用在组件里亲自引入样式了，即: import 'antd/dist/antd.css'应该删除掉

## 十六、redux
    1. 文档: https://redux.js.org/

    2. 求和案例_redux精简版

    6. 求和案例 react-redux优化
        1. 容器组件和UI组件整合一个文件
        2. 无需自己给容器组件传递store,给<App/>包裹一个<Provider store={store}>即可
        3. 使用了react-redux后也不用再自己检测redux中状态的改变了，容器组件可以自动完成这个工作
        4. mapDispatchToProps也可以简单的写成一个对象
        5. 一个组件要和redux打交道要经过哪几步？
            1. 定义好UI组件---不暴露
            2. 引入count生成一个容器组件，并暴露：
                connect(state=>({key:value}),//映射状态
                {key:***Action}//映射操作状态的方法
                )(UI组件)
            3. 在UI组件中通过props.****来读取和操作状态

    7. 求和案例 react-redux数据共享版
        1. 定义一个Person组件，和Count组件通过redux共享数据
        2. 为Person组件编写:reducer, action,配置constant常量
        3. 重点:Person的reducer和Count的Reducer要使用combineReducers进行合并，合并后总状态是一个对象
        4. 交给store的总reducer,最后注意在组件中取出状态的时候，记得取到位

    8. react-redux开发者工具的使用
        1. npm i --save redux-devtools-extension
        2. store中进行配置
            import {composeWithDevTools} from 'redux-devtools-extension'
            const store = createStore(allReducer,composeWithDevTools(applyMiddleware(thunk)))

## 24. 求和案例精简版
    1. 去除Count组件自身的状态
    2. src下建立:
        - src
            -redux
                - store.js
                - count_reducer.js
    3. store.js
        - 引入redux中的createStore函数，创建一个store
        - createStore调用时要传入一个为期服务的reducer
        - 暴露store对象
    4. count_reducer.js
        - reducer的本质是一个函数，接收:preState,action, 返回加工后的状态
        - reducer有两个作用：初始化状态，加工状态
        - reducer被第一次调用时，是store自动触发的，传递的preState是undefined
    
    5. 在index.js中检测store中状态的改变，一旦发生改变重新渲染<App/>
        备注: redux中负责管理状态，至于状态的改变驱动着页面的展示，要靠我们自己写。

## 25.求和案例完整版
    新增文件:
        1. count_action.js 专门用于创建action对象
        2. constant.js 放置action中的type
## 26.求和案例redux异步action版
        1. 明确: 延迟的动作不想交给组件自身,想交给action
        2. 何时需要异步action: 想要对状态进行操作,但是具体的数据靠异步任务返回.
        3. 具体编码:
            1. yarn add redux-thunk ,并配置在store中
            2. 创建action的函数不再返回一般对象,而是一个函数,该函数中写异步任务
            3. 异步任务有结果后,分发一个同步的action去真正操作数据
        4. 备注: 异步action不是必须要写的,完全可以自己等待异步任务的结果了再去分发同步action
    
## 28.求和案例react-redux基本使用
    1. 明确两个概念
        1. UI组件不能使用任何redux的api,中负责页面的呈现,交互等.
        2. 容器组件:负责和redux通信,将结果交给UI组件
    2. 如何创建一个容器组件---靠react-redux的connect函数
        connect(mapStateToProps,mapDispatchToProps)(UI组件)
            - mapStateToProps:映射操作状态的方法,返回值是一个对象
            - mapDispatchToProps:映射操作状态的方法,返回的是一个对象
    3. 备注1:容器组件中的store是靠props传进去的,而不是在容器组件中直接引入
    4. 备注2:mapDispatchToProps,也可以是一个对象，对象的key是参数名,value是一个函数，返回一个action,dispatch会自已调用

## 29.求和案例react-redux优化
    1. 容器组件和UI组件整合一个文件
    2. 无需自己给容器组件传递store,给<App/>包裹一个<Provider store={store}>即可, 这样里面的所有组件都能读到store
    3. 使用了react-redux后也不用再自己检测redux中状态的改变了，容器组件可以自动完成这个动作
    4. mapDispatchToProps也可以简单的写成一个对象
    5. 一个组件要和redux打交道要经过哪几步？
        1. 定义好UI组件--不暴露
        2. 引入connect生成一个容器组件，并暴露，写法如下：
            connect(state = >({key:value}),{key:****Action})(UI组件)
        3. 在UI组件中通过this.props.****读取和操作状态


## 30.求和案例react-redux数据共享版
    1. 定义一个Person组件，和Count组件通过redux共享数据
    2. 为Person组件编写: reducer,action,配置constant常量
    3. 重点：Person的reducer和Count的Reducer要使用combineReducers进行合并，合并后的总状态是一个对象
    4. 交给store的是总reducer,最后注意在组件中取出状态的时候，使用键值对

## 31.求和案例react-redux开发者工具的使用
    1. yarn add redux-devtools-extension
    2. store中进行配置
        import {composeWithDevTools} from 'redux-devtools-extension'
        const store = createStore(allReducer,composeWithDevTools(applyMiddleware(thunk)))

## 32.纯函数
    1. 一类特别的函数，只要同样的输入，必定得到同样的输出
    2. 必须遵守以下约束：
        1. 不得改写参数数据
        2. 不会产生副作用，例如网络请求，输入和输出设备
        3. 不能调用Date.now()或者Math.random()等不纯的方法
    3. redux的reducer函数必须是一个纯函数

## 33.求和案例_react-redux最终版
    1. 所有变量名字要规范，尽量触发对象的简写形式
    2. reducers文件夹中，编写index.js专门用于汇总所有的reducer

## 部署运行项目
    1. npm run build
    2. npm i serve -g
    3. serve build