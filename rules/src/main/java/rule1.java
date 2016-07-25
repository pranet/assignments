/**
 * Created by pranet on 20/07/16.
 */

/**
 * Rule : findbugs:ICAST_IDIV_CAST_TO_DOUBLE
 * When a and b are integers, a / b is evaluated by entirely ignoring the fractional part.
 * So, (2 / 5) and (-4 / 5) will both be evaluated as 0;
 * NOTE : It is incorrect to say that (a / b) is evaluated as floor(a / b)
 * (due to negative values being rounded up instead of down)
 */
public class rule1 extends Rule {

    void goodExample() {
        int a = 2;
        int b = 5;
        double c = (double)a / b;
        System.out.println(c);
    }

    void badExample() {
        int a = 2;
        int b = 5;
        double c = a / b;
        System.out.println(c);
    }
}
