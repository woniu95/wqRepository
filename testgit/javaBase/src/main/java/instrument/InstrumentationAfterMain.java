package instrument;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

/**
 * @program: instrumemtationTest
 * @description: 主程序启动之后运行
 * @author: 王强
 * @create: 2020-09-17 11:49
 */
public class InstrumentationAfterMain {

    private static Instrumentation inst;

    public static void agentmain(String agentArgs, Instrumentation instP){
        inst = instP;
       Class[] allLoadedClasses =  inst.getAllLoadedClasses();
       for(Class clazz: allLoadedClasses){
           System.out.println(clazz.getName());
       }
        try {
            Class springUtilsClass = Class.forName("com.mopon.scenic.cloud.spring.util.SpringUtils");
            Method getBeanMethod = springUtilsClass.getMethod("getBean", String.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void logic(String beanType, String beanClassName, String beanMethodName, Class... paramTypes){

    }

}
