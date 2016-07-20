/**
 * Created by pranet on 20/07/16.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Rule : squid:S1143
 * Do not have a return statement in fianlly block as it may prevent error from being propagated
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
