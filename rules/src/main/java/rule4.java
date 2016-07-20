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
public class rule4 extends Rule {

    void badExample() {
        List a = null;
        try {
            throw new RuntimeException();
        }
        finally {
            return;
        }
    }

    void goodExample() {
        try {
            throw new RuntimeException();
        }
        finally {

        }
    }

}
