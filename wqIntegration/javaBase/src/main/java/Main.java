import java.util.*;

public class Main{

    public static void main(String[] args) throws InterruptedException {

       HashMap map = new HashMap();
       map.put("1", "a");
       int hash = 16;
       int length = 32;
       int mask = length -1;
       System.out.println(Integer.toBinaryString(hash));
       System.out.println(Integer.toBinaryString(length));
       System.out.println(Integer.toBinaryString(mask));
       System.out.println(hash&length);
       System.out.println( hash % length);
       System.out.println( hash & mask);

        /**1234
         * 8
         * 00000010000
         * 00000010000
         * 00000001111
         * 0
         * 2
         *
         */

    }
    static class OBj{
        public String a;

        public OBj(String a) {
            this.a = a;
        }

    }

}






