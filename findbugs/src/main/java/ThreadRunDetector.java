import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.classfile.CheckedAnalysisException;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.DescriptorFactory;
import edu.umd.cs.findbugs.classfile.MethodDescriptor;
import java.lang.*;

public class ThreadRunDetector extends BytecodeScanningDetector {
    private static final ClassDescriptor THREAD_CLASS = DescriptorFactory.createClassDescriptor(Thread.class);

    final BugReporter bugReporter;

    public ThreadRunDetector(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    @Override
    public void sawMethod() {
        ClassDescriptor invokedClass = getClassDescriptorOperand();
        MethodDescriptor invokedMethod = getMethodDescriptorOperand();
        Class<?> classType = null;
        try {
            classType = Class.forName(invokedClass.getXClass().toString().replaceAll("/", "."));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (Thread.class.isAssignableFrom(classType) && invokedMethod != null
                && "run".equals(invokedMethod.getName())) {
                    bugReporter.reportBug(
                    new BugInstance("THREAD_RUN", Priorities.HIGH_PRIORITY).
                            addClassAndMethod(this).
                            addSourceLine(this));
        }
    }
}
