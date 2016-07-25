/**
 * Created by pranet on 20/07/16.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Rule : findbugs:NP_ALWAYS_NULL_EXCEPTION
 * The object being dereferenced may be null on the exception path
 */
public class rule3 extends Rule {

    void goodExample() {
        List a = null;
        if (1 > 2) {
            a = new ArrayList();
        }
        else {
            a = new LinkedList();
        }
        printLength(a);
    }

    void badExample() {
        List a = null;
        if (1 > 2) {
            a = new ArrayList();
        }
        else if (2 > 3) {
            a = new LinkedList();
        }
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
