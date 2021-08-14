package cn.syl.java.proxy.cglib;

import lombok.Data;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;

public class Lazy {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(User.class);
        enhancer.setCallback(new LazyLoader() {
            @Override
            public Object loadObject() throws Exception {
                System.out.println("init target object");
                User user = new User();
                user.setId("1");
                user.setName("LazyLoader");
                return user;
            }
        });
        User user = (User) enhancer.create();
        System.out.println(user.getId());
        System.out.println(user.getName());

        System.out.println("==============");
        Enhancer e = new Enhancer();
        e.setSuperclass(Course.class);
        e.setCallback(new Dispatcher() {
            @Override
            public Object loadObject() throws Exception {
                System.out.println("init target object");
                Course c = new Course();
                c.setClazName("数学");
                c.setUser(new User());
                c.getUser().setId("2");
                c.getUser().setName("xxx");
                return c;
            }
        });
        Course c = (Course) e.create();
        System.out.println(c.getUser());
        System.out.println(c.getClazName());

    }
    @Data
    static class Course{
        private String clazName;

        private User user;
    }
    @Data
    static
    class User{
        private String id;

        private String name;


        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
