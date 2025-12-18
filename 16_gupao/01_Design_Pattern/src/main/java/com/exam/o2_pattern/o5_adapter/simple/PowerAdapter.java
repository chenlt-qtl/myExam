package com.exam.o2_pattern.o5_adapter.simple;

public class PowerAdapter implements DC5 {

    private AC220 ac220;

    public PowerAdapter(AC220 ac220){
        this.ac220 = ac220;
    }

    @Override
    public int output5V() {
        int superOutPut = ac220.outputAC220V();
        int output = superOutPut / 44;
        System.out.println("使用Adapter输入AC" + superOutPut + "V, 输入" + output + "伏");
        return output;
    }
}
