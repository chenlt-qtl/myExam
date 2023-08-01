import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
// import App02 from './pages/02_hello_react';
// import App03 from './pages/03_todoList';
// import App04 from './pages/04_proxy';
// import App05 from './pages/05_githubSearch_axios';
// import App06 from './pages/06_githubSearch_pubsub';
// import App07 from './pages/07_githubSearch_fetch';
import App08 from './pages/08_router';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter><App08 /></BrowserRouter>
);



