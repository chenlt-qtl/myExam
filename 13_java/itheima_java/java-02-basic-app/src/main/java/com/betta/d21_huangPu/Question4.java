package com.betta.d21_huangPu;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * **需求：**
 * <p>
 * ArrayList集合是很重要的一种集合，请手工书写一个MyArrayList集合模拟ArrayList集合。
 * <p>
 * **具体功能点的要求如下：**
 * <p>
 * 1、MyArrayList需要支持泛型，内部使用数组作为容器。
 * <p>
 * 2、在MyArrayList中开发add方法，用于添加数据的，需要遵循ArrayList的扩容机制（自行设计代码，不需要与ArrayList的源代码一样，思想一致即可）
 * <p>
 * 3、在MyArrayList中开发根据索引查询数据的get方法。
 * <p>
 * 4、在MyArrayList中开发根据索引删除数据的remove方法。
 * <p>
 * 5、在MyArrayList中开发一个获取集合大小的size ()方法。
 * <p>
 * 6、能够在MyArrayList集合中开发一个forEach方法，这个方法支持使用Lambda进行遍历，至于函数式接口叫什么名称无所谓。
 * <p>
 * 7、编写测试用例对自己编写的MyArrayList集合进行功能正确性测试。
 */
public class Question4 {
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("张三1");
        list.add("李四2");
        list.add("张三3");
        list.add("李四4");
        list.add("张三5");
        list.add("李四6");
        System.out.println(list);

        System.out.println(list.get(1));
        System.out.println(list.remove(3));
        System.out.println(list);
        System.out.println(list.size());

        list.add("张三");
        list.add("李四");
        System.out.println(list);
        list.forEach(System.out::println);

    }
}

class MyArrayList<T> {
    private Object[] arr;
    private int length = 0;
    private static final int SIZE = 2;

    public MyArrayList() {
        arr = new Object[SIZE];
    }

    public MyArrayList(int size) {
        arr = new Object[size];
    }

    public void add(T obj) {
        if (length == arr.length) {
            //扩容
            arr = Arrays.copyOf(arr, arr.length + (arr.length >> 1));
        }
        arr[length++] = obj;
    }

    public T get(int index) {
        checkIndex(index);
        return (T) arr[index];
    }

    private void checkIndex(int index) {
        if (index > length - 1 || index < 0) {
            throw new RuntimeException("超过范围");
        }
    }

    //根据索引删除数据的remove方法。
    public T remove(int index) {
        checkIndex(index);
        T obj = (T) arr[index];

        if(index < length-1) {
            System.arraycopy(arr,index+1,arr,index,length-index-1);
        }
        length--;
        return obj;
    }

    //获取集合大小的size ()方法。
    public int size() {
        return length;
    }
    //开发一个forEach方法，这个方法支持使用Lambda进行遍历，至于函数式接口叫什么名称无所谓。

    public void forEach(MyInterface<T> myInterface) {
        for (int i = 0; i < length; i++) {
            myInterface.each((T) arr[i]);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(arr, 0, length));
    }
}

@FunctionalInterface
interface MyInterface<T> {
    void each(T t);
}