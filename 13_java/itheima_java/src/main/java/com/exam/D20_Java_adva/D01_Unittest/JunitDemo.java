package com.exam.D20_Java_adva.D01_Unittest;


import org.junit.*;

/**
 * MyUtils测试类
 */
public class JunitDemo {

    @Before
    public void before(){
        System.out.println("----@Before----");
    }

    @After
    public void after(){
        System.out.println("----@After----");
    }

    @BeforeClass
    public static void beforeClass(){
        System.out.println("----@BeforeClass----");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("----@AfterClass----");
    }

    @Test
    public void testAdd(){
        System.out.println("测试add");
        MyUtils myUtils = new MyUtils();
        Assert.assertEquals(5,myUtils.add(1,4));
    }

    @Test
    public void testDivision(){
        System.out.println("测试division");
        MyUtils myUtils = new MyUtils();
        Assert.assertEquals((Double)0.25,myUtils.division(1,4));
    }
}
