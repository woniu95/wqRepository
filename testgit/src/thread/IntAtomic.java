package src.thread;

import sun.misc.Unsafe;

/**
 *
 * Unsafe can do some like operate memory, CAS(used by Atomic* class to implement atomic operate),...
 * CAS implement Atomic
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-11-30 16:57
 */

 public class IntAtomic{

    private int value;

    public  IntAtomic( int value){
        this.value = value;
    }

    public int getAndIncrease(){
        final Unsafe unsafe = Unsafe.getUnsafe();
        long valueOffset = 0;
        try {
            valueOffset = unsafe.objectFieldOffset(IntAtomic.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        int old;
        int newint;
        do{
            old = this.value;
            newint = old + 1;
        } while(unsafe.compareAndSwapInt(this,valueOffset,old,newint));
        return newint;
    }

    public static void main(String[] args) {
        IntAtomic intAtomic = new IntAtomic(0);
        System.out.println(intAtomic.getAndIncrease());

    }
}