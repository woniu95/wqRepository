package instrument;

import java.lang.instrument.Instrumentation;


/**
 *   //Instrumentation can be used for change class-code , getObjectSize , get jvm run object info.
 *   //run  Instrumentation jvm init need agent jar. jvm param add : -javaagent:D:\\instrumemtationTest-1.0-SNAPSHOT.jar
 *   //agent class see InstrumemtationGetObjectShallowSizeAgent.java
 *   // other way is
 *   System.out.println("Object size: "+ InstrumemtationGetObjectShallowSizeAgent.sizeOf(new Object()));
 */
public class InstrumemtationGetObjectShallowSizeAgent {

        private static  Instrumentation inst;

        public static void premain(String agentArgs, Instrumentation instP){
            inst = instP;
        }

        public static long sizeOf(Object obj){
            return inst.getObjectSize(obj);
        }

}
