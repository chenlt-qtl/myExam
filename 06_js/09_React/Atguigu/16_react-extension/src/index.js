import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Link, Switch } from 'react-router-dom';

import './styles.css'

import App01 from './pages/1_setState';
import App02 from './pages/2_lazyLoad';
import App03 from './pages/3_hooks';
import App04 from './pages/4_Content';
import App05 from './pages/5_pureComponent';
import App06 from './pages/6_renderProps';
import App07 from './pages/7_ErrorBoundary/Parent';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>

        <div style={{ display: "flex", height: "100vh", overflow: "hidden" }}>
            <div className='menu'>
                <Link to="/01">01_setStage</Link>
                <Link to="/02">02_lazyLoad</Link>
                <Link to="/03">03_hooks</Link>
                <Link to="/04">04_Context对象</Link>
                <Link to="/06">06_renderProps</Link>
                <Link to="/07">07_ErrorBoundary</Link>
            </div>

            <div style={{ flex: 1, overflow: "auto", padding: "20px" }}>
                <Switch>
                    <Route path="/01" component={App01}></Route>
                    <Route path="/02" component={App02}></Route>
                    <Route path="/03" component={App03}></Route>
                    <Route path="/04" component={App04}></Route>
                    <Route path="/05" component={App05}></Route>
                    <Route path="/06" component={App06}></Route>
                    <Route path="/07" component={App07}></Route>
                </Switch>
            </div>

        </div>
    </BrowserRouter>
);

