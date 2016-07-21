package agent;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.*;
/**
 * Created by pranet on 20/07/16.
 */
public class SimpleTransformer implements ClassFileTransformer {
    public SimpleTransformer() {
        super();
    }

    public byte[] transform(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {
        if (className.contains("java") || className.contains("sun")) {
            return bytes;
        }
        try {
            try {
                return transformClass(redefiningClass,bytes);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] transformClass(Class classToTransform, byte[] b) throws IOException, CannotCompileException, NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        cl = pool.makeClass(new java.io.ByteArrayInputStream(b));
        CtBehavior[] methods = cl.getDeclaredBehaviors();
            for (int i = 0; i < methods.length; i++) {
                System.out.println(methods[i].getLongName());
                if (methods[i].isEmpty() == false) {
                    changeMethod(methods[i]);
                }
            }
        b = cl.toBytecode();
        cl.detach();
        return b;
    }

    private void changeMethod(CtBehavior method) throws NotFoundException, CannotCompileException {
        String methodName = method.getLongName();
        String className = method.getDeclaringClass().getName();
        method.insertBefore("{for (int i=0; i < $args.length; i++) {System.out.println($args[i]);}}");
        method.insertBefore("{System.out.println(\"It belongs to " + className + "\");}");
        method.insertBefore("{System.out.println(\"Entering " + methodName + "\");}");
        method.insertAfter("{System.out.println(\"Returning \" + $_ + \" from " + methodName + "\");}");
    }
}
