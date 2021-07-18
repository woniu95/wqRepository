package bugs;

public class _Bug {


    public void test() {
        int i = 8;
        while ((i -= 3) > 0);
        System.out.println("i = " + i);
    }

    public static void main(String[] args) {
        _Bug hello = new _Bug();
        System.out.println(50_000);
        for (int i = 0; i < 50_000; i++) {
            hello.test();
        }
    }
}
