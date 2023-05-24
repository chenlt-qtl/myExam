let str1 = "/abc/ttt.html";
let str2 = "abc/ttt.html";
let reg = /^\//
console.log(reg.test(str1));
console.log(str1.replace(reg,""));
console.log(str2.replace(reg,""));