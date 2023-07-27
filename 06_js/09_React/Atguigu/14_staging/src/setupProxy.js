//低版本写法
//const { proxy } = require('http-proxy-middleware')

//高版本写法
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
            pathRewrite: { '^/api1': "" }//重写请求路径
        }),
        proxy('/api2', {
            target: 'http://localhost:5001',//请求转发给5001
            changeOrigin: true,//控制服务器收到的请求头中Host字段的值
            pathRewrite: { '^/api2': "" }
        })
    )
}