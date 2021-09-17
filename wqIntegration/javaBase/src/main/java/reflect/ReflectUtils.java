package reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 反射工具类
 * @author: 王强
 * @create: 2021-09-17 11:57
 */
public class ReflectUtils {

    public static List<String> getParameterNameJava8(Method method) {

        Parameter[] params = method.getParameters();
        List<String> paramterList = new ArrayList<>(params.length);
        for (Parameter parameter : params) {
            paramterList.add(parameter.getName());
        }

        return paramterList;
    }

}
