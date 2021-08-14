package cn.syl.java.jvm;

import java.io.FileInputStream;
import java.io.IOException;

public class MyClassLoaderTest {
    static class MyClassLoader extends ClassLoader{
        public MyClassLoader(ClassLoader parent, String classPath) {
            super(parent);
            this.classPath = classPath;
        }

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private String classPath;

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                return defineClass(name,data,0,data.length);
            }catch (Exception e){
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

        private byte[] loadByte(String name) throws IOException {
            name = name.replaceAll("\\.","/");
            FileInputStream fis = new FileInputStream(classPath+"/"+name+".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;

        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)){
                Class loadClass = findLoadedClass(name);
                if (loadClass == null){
                    if (name.startsWith("cn.syl.java")){
                        loadClass = findClass(name);
                        if (resolve){
                            resolveClass(loadClass);
                        }
                    }else {
                        return getParent().loadClass(name);
                    }
                }
                return loadClass;
            }
        }
    }
}
