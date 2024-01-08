
1. React Router 6 介绍               
    1. 包：
        - react-router 路由的核心库，提供了很多的组件、钩子
        - react-router-dom 包含react-router所有内容，并添加一些专门用于DOM的组件，例如<BrowserRouter>等
        - react-router-native 包括react-router所有内容，并添加一些专门用于ReactNative的API，如<NativeRouter>等
    2. 与5.x相比，改变了什么？
        1. 移除<Switch>,新增<Routes>
        2. 语法的变化：component={About}变为element={<About/>}等
        3. 新增多个hook: useParams, useNavigate, useMatch等
        4. 官方明确推荐函数式组件了

2. <Routes/>与<Route/>
    1. Routes与Route要配合使用，且必须要Routes包裹Route
    2. Route可以嵌套使用，且可配合useRoutes()配置“路由表”，但需要通过Outlet组件来渲染其子路由
    3. Route也可以不写element属性，这时就是用于展示嵌套路由，一般会有子路由
    4. 当URL发生变化时，Routes都会查看其所有子Route元素找到最佳匹配并呈现组件

3. 编程式路由
JS控制页面跳转