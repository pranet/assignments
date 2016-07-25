/**
 * Created by pranet on 20/07/16.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Rule : findbugs:NP_ALWAYS_NULL
 * Dereferencing a null pointer will cause NullPointerException
 */
public class rule2 extends Rule {

    void goodExample() {
        List a = new ArrayList();
        printLength(a);
    }
    void badExample() {
        List a = null;
        printLength(a);
    }
    void printLength(List a) {
        try {
            System.out.println("Size of list is" + a.size());
            System.out.println("Success");
        }
        catch (NullPointerException e) {
            System.out.println("Encountered null pointer exception");
        }
    }
}
