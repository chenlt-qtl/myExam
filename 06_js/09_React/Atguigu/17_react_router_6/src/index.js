import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { BrowserRouter, NavLink, useRoutes } from 'react-router-dom';
import myRoutes from './routes'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Index></Index>
  </BrowserRouter>
);


export default function Index() {
  const myRoute = useRoutes(myRoutes)
  return (
    <>

      <div className='main'>
        <div className='menu'>
          <NavLink className="list-group-item" to="/01">1_一级路由</NavLink>
          <NavLink className="list-group-item" to="/02">2_重定向</NavLink>
          <NavLink className="list-group-item" to="/03">3_自定义高亮样式</NavLink>
          <NavLink className="list-group-item" to="/04">4_嵌套路由</NavLink>
          <NavLink className="list-group-item" to="/05">5_params参数</NavLink>
          <NavLink className="list-group-item" to="/06">6_search参数</NavLink>
          <NavLink className="list-group-item" to="/07">7_state参数</NavLink>
        </div>
        <div className='body'>
          {/* <Routes>
                <Route path='/01' component={<App01 />} />
              </Routes> */}
          {myRoute}
        </div>
      </div>
    </>
  )
}


