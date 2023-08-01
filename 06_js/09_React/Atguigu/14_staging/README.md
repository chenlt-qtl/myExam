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


## github搜索案例相关知识点
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


## 路由的基本使用
    1. 明确好界面中的导航区，展示区
    2. 导航区的A标签改为Link标签
        <Link to="xxx">Demo</Link>
    3. 展示区写Router标签进行路径的匹配
        <Route path="/xxx" component={Demo}/>
    4. <App>的最外侧包裹了一个<BrowserRouter>或<HashRouter>