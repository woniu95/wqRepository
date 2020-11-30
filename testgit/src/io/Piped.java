package src.io;

import java.io.*;

/**
 *
 * communicate main thread to sub thread implement by Pipe
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-11-30 16:47
 */

public class Piped {
    public static void main(String[] args) throws Exception {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        // 将输出流和输入流进行连接，否则在使用时会抛出IOException
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        BufferedReader filein = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\wqdaily\\codespaces\\testgit\\test.txt"),"utf-8"));

        char[] cbuffer = new char[1024];

        int receive = 0;
        try {
            while ((receive = filein.read(cbuffer)) != -1) {
                out.write(cbuffer,0,receive);
                out.write("----------------one write one write -------------");
            }
        } finally {
            out.close();
        }
    }
    static class Print implements Runnable {
        private PipedReader in;
        public Print(PipedReader in) {
            this.in = in;
        }
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (IOException ex) {
            }
        }
    }
}