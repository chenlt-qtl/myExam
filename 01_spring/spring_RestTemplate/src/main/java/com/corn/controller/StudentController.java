package com.corn.controller;

import com.corn.entity.ResponseBean;
import com.corn.entity.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping
    public ResponseBean getList(@RequestHeader Map<String, String> headers, @RequestParam String key) {
        List<Student> list = new ArrayList();
        list.add(new Student("汤姆", 12));
        list.add(new Student("Mike", 15));
        return ResponseBean.successData(list);
    }

    @PostMapping("/entity")
    public ResponseBean addStudent(Student student) {
        return ResponseBean.successData("操作成功，数据" + student.toString());
    }

    @PostMapping("/body")
    public ResponseBean addStudent1(@RequestBody Student student) {
        return ResponseBean.successData("操作成功，数据" + student.toString());
    }

    @PostMapping("/basic")
    public ResponseBean addStudent2(String name, int age) {
        Student student = new Student(name, age);
        return ResponseBean.successData("操作成功，数据" + student.toString());
    }
}

