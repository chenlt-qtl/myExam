package com.exam.o2_pattern.o7_proxy.o4_my_proxy.proxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyProxy {
    public static Object newProxyInstance(MyClassLoader loader,
                                          Class<?>[] interfaces,
                                          MyInvocationHandler h) {

        //1.动态生成源码.java文件
        String src = generateSrc(interfaces);

        System.out.println(src);

        //2.java文件输出到磁盘，保存为文件$Proxy0.java
        String filePath = MyProxy.class.getResource("").getPath();
        File f = new File(filePath + "$Proxy0.java");
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(src);
            fw.flush();
            fw.close();
            //3.把.java文件编译成$Proxy
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(f);
            JavaCompiler.CompilationTask task = compiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
            task.call();
            standardFileManager.close();

            //4.把生成的.class文件加载到JVM中
            Class<?> proxyClass = loader.findClass("$Proxy0");
            Constructor<?> constructor = proxyClass.getConstructor(MyInvocationHandler.class);
            constructor.setAccessible(true);
            return constructor.newInstance(h);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateSrc(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.exam.o2_pattern.o7_proxy.o4_my_proxy.proxy;\n");
        sb.append("import java.lang.reflect.Method;\n");
        sb.append("public class $Proxy0 implements ");
        for (int i = 0; i < interfaces.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(interfaces[i].getName());
        }
        sb.append("{\n");
        sb.append("    MyInvocationHandler h;\n");
        sb.append("    public $Proxy0(MyInvocationHandler h) {\n");
        sb.append("        this.h = h;\n");
        sb.append("    }\n");
        sb.append("\n");

        //生成代理方法
        for (Class<?> anInterface : interfaces) {
            for (Method m : anInterface.getMethods()) {

                Class<?>[] parameterTypes = m.getParameterTypes();
                Class<?> returnType = m.getReturnType();
                sb.append("    public "+returnType.getName()+" "+m.getName()+"() {\n");
                sb.append("        try {\n");
                sb.append("            Method method = "+anInterface.getName()+".class.getMethod(\""+m.getName()+"\",new ");
                sb.append("Class[]{});\n");
                sb.append("            Object object = h.invoke(this, method, new Object[]{});\n");
                if(!returnType.isAssignableFrom(void.class)) {
                    sb.append("            return object;\n");
                }
                sb.append("        } catch (Exception e) {\n");
                sb.append("            throw new RuntimeException(e);\n");
                sb.append("        } catch (Throwable e) {\n");
                sb.append("            throw new RuntimeException(e);\n");
                sb.append("        }\n");
                sb.append("\n");
                sb.append("}");
            }
        }
        sb.append("\n");
        sb.append("}\n");

        return sb.toString();
    }
}
