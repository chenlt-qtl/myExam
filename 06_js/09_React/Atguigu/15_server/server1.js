const express = require("express")
const app = express()

app.use((req, res, next) => {
    console.log("请求服务器1");
    console.log("请求来自于", req.get("Host"));
    console.log("请求的地址",req.method," ",req.url);
    next()
})

app.get("/student", (req, res) => {
    console.log(req.header("name"));
    const students = [
        { id: 1, name: "汤姆", age: 18 },
        { id: 2, name: "Mike", age: 19 },
        { id: 3, name: "Kate", age: 17 }
    ]

    res.send(students)
})

app.post("/student", (req, res) => {
    console.log(req.header("name"));
    console.log(req.get("key"));
    const result = {
        success: 1,
        message: "操作成功"
    }

    res.send(result)
})

app.put("/student", (req, res) => {
    res.send()
})

app.delete("/student", (req, res) => {
    res.send()
})

// 启动命令 node .\server1.js 
app.listen(5000, err => {
    if (!err) {
        console.log("服务器1启动成功，端口号为5000");
    }
})