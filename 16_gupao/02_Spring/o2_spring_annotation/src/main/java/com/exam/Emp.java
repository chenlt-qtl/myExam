package com.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {

    private String id;
    private String name;
    private String gander;
    private Integer age;

}
