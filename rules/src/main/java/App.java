/**
 * Created by pranet on 20/07/16.
 */
public class App {
    public static void main(String args[]) {
        Rule r1 = new rule1();
        Rule r2 = new rule2();
        Rule r3 = new rule3();
//        Rule r4 = new rule2();
        r1.execute();
        r2.execute();
        r3.execute();
//        r4.execute();

    }
}
