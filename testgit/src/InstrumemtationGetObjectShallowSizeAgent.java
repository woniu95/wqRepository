package src;

import java.lang.instrument.Instrumentation;

public class InstrumemtationGetObjectShallowSizeAgent {

        private static  Instrumentation inst;

        public static void premain(String agentArgs, Instrumentation instP){
            inst = instP;
        }

        public static long sizeOf(Object obj){
            return inst.getObjectSize(obj);
        }

}
