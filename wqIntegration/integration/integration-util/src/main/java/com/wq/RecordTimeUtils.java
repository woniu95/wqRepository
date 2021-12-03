package com.wq;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @PackageName:com.wq
 * @ClassName:RecordTimeUtils
 * @Description:
 * @author: wq
 * @date 2021/12/3 21:47
 */
public class RecordTimeUtils {
    private static ThreadLocal<LocalDateTime> timers = new InheritableThreadLocal();

    private  ThreadLocal<LocalDateTime> varTimers = new InheritableThreadLocal();

    public static void start(){
        start(timers);
    }

    public static long end(){
       return end(timers);
    }

    public  void varstart(){
        start(varTimers);
    }

    public  long varend(){
        return end(varTimers);
    }

    private static void start(ThreadLocal<LocalDateTime> timers){
        end(timers);
        timers.set(LocalDateTime.now());
    }

    private static long end(ThreadLocal<LocalDateTime> timers){
        try{
            LocalDateTime start = timers.get();
            if(start != null ){
                LocalDateTime currentTime = LocalDateTime.now();
                return Duration.between(start, currentTime).toMillis();
            }
            return 0;
        }finally {
            timers.remove();
        }
    }


}
