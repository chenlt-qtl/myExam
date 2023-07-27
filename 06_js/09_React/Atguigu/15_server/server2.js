const express = require("express")
const app = express()

app.use((req, res, next) => {
    console.log("请求服务器2");
    console.log("请求来自于",req.get("Host"));
    console.log("请求的地址",req.url);
    next()
})

app.get("/cars", (req, res) => {
    const cars = [
        { id: 1, name: "奔驰", price: 188 },
        { id: 2, name: "宝马", price: 128 },
        { id: 3, name: "奥迪", price: 199 }
    ]

    res.send(cars)
})

app.listen(5001,err=>{
    if(!err){
        console.log("服务器2启动成功，端口号为5001");
    }
})