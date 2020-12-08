package base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * serialize mechanism . Custom Serialize : override writeObject and readObject method, LinkedList Has override those method.
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
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./list.object"));
            list.pop();
            objectOutputStream.writeObject(list);

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./list.object"));
            LinkedList serialyzedList = (LinkedList)objectInputStream.readObject();

            System.out.println(serialyzedList);
            objectInputStream.close();
            objectOutputStream.close();
        }catch(Exception e){

        }
    }


}
