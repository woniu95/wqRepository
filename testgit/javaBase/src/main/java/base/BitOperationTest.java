package base;

/**
 * bit operation  按 二进制源码 -> 补码计算 -> 结果补码 -> 二进制源码
 * type extend byte -> char->int  左补符号位 char无符号 类型拓展时 补0
 * 需要注意的是java中的移位操作会模除位数  如 long 类型  :   1L <<65  => 1L << (65 % 64(long 8 Byte)) => 1L << 1 = 2
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-11-30 17:05
 */
public class BitOperationTest {

    public static void main(String[] args) {

        byte aByte = new Byte((byte) -1);
        System.out.println("byte -1 -> int = "+(int)aByte);
        System.out.println("byte -1 -> char -> int = "+(int)(char)aByte);
        System.out.println("byte:127+1 = "+(byte)(aByte+(byte)1));

    }
}
