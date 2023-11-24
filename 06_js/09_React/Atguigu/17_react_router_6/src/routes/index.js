import App01 from '../pages/1_basic'
import Home from '../pages/1_basic/Home'
import About from '../pages/1_basic/About'
import App02 from '../pages/2_redirect'
import App021 from '../pages/2_redirect/Home'
import App022 from '../pages/2_redirect/About'
import App03 from '../pages/3_active'
import App031 from '../pages/3_active/Home'
import App032 from '../pages/3_active/About'
import App04 from '../pages/4_children'
import App041 from '../pages/4_children/Home'
import App042 from '../pages/4_children/About'
import App043 from '../pages/4_children/News'
import App044 from '../pages/4_children/Message'
import App05 from '../pages/5_params'
import App051 from '../pages/5_params/Home'
import App052 from '../pages/5_params/About'
import App053 from '../pages/5_params/News'
import App054 from '../pages/5_params/Message'
import App055 from '../pages/5_params/Detail'
import App06 from '../pages/6_search'
import App061 from '../pages/6_search/Home'
import App062 from '../pages/6_search/About'
import App063 from '../pages/6_search/News'
import App064 from '../pages/6_search/Message'
import App065 from '../pages/6_search/Detail'
import App07 from '../pages/7_state'
import App071 from '../pages/7_state/Home'
import App072 from '../pages/7_state/About'
import App073 from '../pages/7_state/News'
import App074 from '../pages/7_state/Message'
import App075 from '../pages/7_state/Detail'
import { Navigate } from 'react-router-dom'

export default [
    {
        path: "/01", element: <App01 />,
        children: [
            { path: "home", element: <Home /> },
            { path: "about", element: <About /> },
        ]
    },
    {
        path: "/02", element: <App02 />,
        children: [
            { path: "home", element: <App021 /> },
            { path: "about", element: <App022 /> },
        ]
    }, {
        path: "/03", element: <App03 />,
        children: [
            { path: "home", element: <App031 /> },
            { path: "about", element: <App032 /> },
        ]
    }, {
        path: "/04", element: <App04 />,
        children: [
            {
                path: "home", element: <App041 />,
                children: [
                    { path: "news", element: <App043 /> },
                    { path: "message", element: <App044 /> },
                ]
            },
            { path: "about", element: <App042 /> },
        ]
    }, {
        path: "/05", element: <App05 />,
        children: [
            {
                path: "home", element: <App051 />,
                children: [
                    { path: "news", element: <App053 /> },
                    {
                        path: "message", element: <App054 />,
                        children: [{ path: "detail/:id/:title/:content", element: <App055 /> }]
                    },
                ]
            },
            { path: "about", element: <App052 /> },
        ]
    }, {
        path: "/06", element: <App06 />,
        children: [
            {
                path: "home", element: <App061 />,
                children: [
                    { path: "news", element: <App063 /> },
                    {
                        path: "message", element: <App064 />,
                        children: [{ path: "detail", element: <App065 /> }]
                    },
                ]
            },
            { path: "about", element: <App062 /> },
        ]
    }, {
        path: "/07", element: <App07 />,
        children: [
            {
                path: "home", element: <App071 />,
                children: [
                    { path: "news", element: <App073 /> },
                    {
                        path: "message", element: <App074 />,
                        children: [{ path: "detail", element: <App075 /> }]
                    },
                ]
            },
            { path: "about", element: <App072 /> },
        ]
    }, {
        path: '/',
        element: <Navigate to='/01/about' />
    }
]