package cn.syl.java.proxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Field;
import java.util.Properties;

public class CglibTest {

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        System.getProperties().put( "sun.misc.ProxyGenerator.saveGeneratedFiles" , "true" );
        saveGeneratedCGlibProxyFiles(System.getProperty("user.dir"));

        MethodInterceptor methodInterceptor = (object,method,argss,proxyMethod)->{
            System.out.println("----before");
//            Object result = proxyMethod.invokeSuper(object,argss);
            method.invoke(object,args);
            System.out.println("----after");
            return "result";
        };
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(methodInterceptor);
        enhancer.setSuperclass(Subject.class);
        Subject proxy = (Subject) enhancer.create();
        proxy.hello("hello world");


    }
    public static void saveGeneratedCGlibProxyFiles(String dir) throws Exception {
        Field field = System.class.getDeclaredField("props");
        field.setAccessible(true);
        Properties props = (Properties) field.get(null);
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, dir);//dir为保存文件路径
        props.put("net.sf.cglib.core.DebuggingClassWriter.traceEnabled", "true");
    }
    static class Subject{
        public Subject() {
        }

        public void hello(String msg){
            System.out.println(msg);
        }
    }

    static interface ll{
        void hello(String msg);
    }


}
