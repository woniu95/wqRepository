package src.base;

import java.math.BigDecimal;

/**
 * float/double  precise problem , use BigDecimal String constructor method.
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-11-30 17:12
 */
public class FloatPreciseProblem {

    public static void main(String[] args) {

        BigDecimal sumDeductAmount = new BigDecimal(0);
        sumDeductAmount =  sumDeductAmount.add(new BigDecimal("0.06")).add(new BigDecimal("0.06")).add(new BigDecimal("0.24"));
        float sum = 0.06f+0.06f+0.24f;
        float a = sum;
        System.out.println(sumDeductAmount.floatValue());
        System.out.println(sum);
        System.out.println(sumDeductAmount.doubleValue());

    }
}
