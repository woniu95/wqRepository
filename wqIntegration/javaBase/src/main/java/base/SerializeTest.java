package base;

import java.io.*;
import java.io.ObjectInputStream;
import java.util.LinkedList;

/**
 * serialize mechanism .
 * Custom Serialize : override writeObject and readObject method, LinkedList Has override those method.
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-11-30 17:17
 */
public class SerializeTest {

    public static void main(String[] args) {

        LinkedList list = new LinkedList();
        list.push("firstIn");
        list.push("secondIn");
        list.push("thirdIn");
        list.pop();
        System.out.println(list);
        try{
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./list.object"));
//            list.pop();
//            objectOutputStream.writeObject(list);
//
//            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./list.object"));
//            LinkedList serialyzedList = (LinkedList)objectInputStream.readObject();
//
//            System.out.println(serialyzedList);
//            objectInputStream.close();
//            objectOutputStream.close();

            A a = new A();
            a.setA("aval");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./a.object"));
            objectOutputStream.writeObject(a);

            Thread.currentThread().setContextClassLoader(new ClassLoader() {
                @Override
                public Class<?> loadClass(String name) throws ClassNotFoundException {
                    return super.loadClass(name);
                }
            });
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./a.object"));
            a = (A)objectInputStream.readObject();
            System.out.println(a);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static class A implements Serializable {

        private String a;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "A{" +
                    "a='" + a + '\'' +
                    '}';
        }
    }

}
