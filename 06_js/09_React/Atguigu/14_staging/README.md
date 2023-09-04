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