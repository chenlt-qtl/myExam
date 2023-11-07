## RestTemplate练习

测试方法：
 1. 启动Application.java.main
 2. 测试链接 http://localhost:8099/test/{method}, 具体链接看TestController.java
 3. test会去请求http://localhost:8099/student的API并返回结果
 
 ### http 状态码
 1** 通知
 
 2** 请求成功返回
 200 ok
 201 created
 202 accepted
 
 301 重定向，永久迁移 
 302 重定向，临时迁移
 
 4** 客户端异常
 
 5** 服务端异常