/**
 * Created by pranet on 20/07/16.
 */
public abstract class Rule {
    abstract void goodExample();

    abstract void badExample();

    void execute() {
        System.out.println("Executing good example");
        goodExample();
        System.out.println("Executing bad example");
        badExample();
    }
}
