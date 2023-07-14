const express = require("express")
const app = express()

app.use((req, res, next) => {
    console.log("请求服务器1");
    next()
})

app.get("/students", (req, res) => {
    const students = [
        { id: 1, name: "Tom", age: 18 },
        { id: 2, name: "Mike", age: 19 },
        { id: 3, name: "Kate", age: 17 }
    ]

    res.send(students)
})

app.listen(5000,err=>{
    if(!err){
        console.log("服务器1启动成功，端口号为5000");
    }
})