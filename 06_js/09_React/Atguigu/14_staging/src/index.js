import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App02 from './pages/02_hello_react';
import App03 from './pages/03_todoList';
import App04 from './pages/04_proxy';
import App05 from './pages/05_githubSearch_axios';
import App06 from './pages/06_githubSearch_pubsub';
import App07 from './pages/07_githubSearch_fetch';
import App08 from './pages/08_router_5';
import App09 from './pages/09_NavLink';
import App10 from './pages/10_MyNavLink';
import App11 from './pages/11_Switch';
import App13 from './pages/13_exact';
import App14 from './pages/14_redirect';
import App15 from './pages/15_nest';
import App16 from './pages/16_params';
import App17 from './pages/17_search';
import App18 from './pages/18_state';
import App19 from './pages/19_pushReplace';
import App20 from './pages/20';
import App21 from './pages/21_withRouter';
import App22 from './pages/22_antd';
import { Route, Link } from 'react-router-dom'
//5之前的版本要引入样式
import 'antd/dist/antd.css'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(

    <BrowserRouter>
        <div style={{ display: "flex", height: "100vh", overflow: "hidden" }}>
            <div style={{ width: "200px", padding: "20px", borderRight: "1px solid #eee", display: "flex", flexDirection: "column", gap: "10px" }}>
                <Link to="/02">02_hello,React</Link>
                <Link to="/03">03_Todo List 案例</Link>
                <Link to="/04">04_配置代理</Link>
                <Link to="/05">05_git搜索案例axios</Link>
                <Link to="/06">06_git搜索案例pubsub</Link>
                <Link to="/07">07_git搜索案例fetch</Link>
                <Link to="/08">08_路由的基本使用</Link>
                <Link to="/09">09_NavLink</Link>
                <Link to="/10">10_封装NavLink</Link>
                <Link to="/11">11_Switch</Link>
                <Link to="/13">13_精准匹配与模糊匹配</Link>
                <Link to="/14">14_Redirect</Link>
                <Link to="/15">15_嵌套路由</Link>
                <Link to="/16">16_Params</Link>
                <Link to="/17">17_Search参数</Link>
                <Link to="/18">18_State参数</Link>
                <Link to="/19">19_push_replace模式</Link>
                <Link to="/20">20_编程式路由导航</Link>
                <Link to="/21">21_withRouter的使用</Link>
                <Link to="/22">22_antd组件库的使用</Link>
            </div>
            <div style={{ flex: 1, overflow: "auto" }}>
                <Route path="/02" component={App02}></Route>
                <Route path="/03" component={App03}></Route>
                <Route path="/04" component={App04}></Route>
                <Route path="/05" component={App05}></Route>
                <Route path="/06" component={App06}></Route>
                <Route path="/07" component={App07}></Route>
                <Route path="/08" component={App08}></Route>
                <Route path="/09" component={App09}></Route>
                <Route path="/10" component={App10}></Route>
                <Route path="/11" component={App11}></Route>
                <Route path="/13" component={App13}></Route>
                <Route path="/14" component={App14}></Route>
                <Route path="/15" component={App15}></Route>
                <Route path="/16" component={App16}></Route>
                <Route path="/17" component={App17}></Route>
                <Route path="/18" component={App18}></Route>
                <Route path="/19" component={App19}></Route>
                <Route path="/20" component={App20}></Route>
                <Route path="/21" component={App21}></Route>
                <Route path="/22" component={App22}></Route>
            </div>
        </div>
    </BrowserRouter>

);



