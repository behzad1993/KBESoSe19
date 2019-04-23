package de.htw.ai.kbe.runmerunner;


import java.util.logging.Logger;


public class ClassWithAnnotations {

    private static final Logger LOGGER = Logger.getLogger(ClassWithAnnotations.class.getName());

    @RunMe
    private boolean privateNotStaticMethod() {
        LOGGER.info("Private method (not static) - not invokable.");
        return true;
    }

    @RunMe
    private static boolean privateStaticMethod() {
        LOGGER.info("Private static method - not invokable.");
        return true;
    }

    @RunMe
    /*package-private*/ String packagePrivateMethod() {
        return "Method - package-private (but not static)";
    }

    @RunMe
    /*package-private*/ static String packagePrivateStaticMethod() {
        return "Method - package-private and static";
    }

    @RunMe
    public String publicNotStaticMethod() {
        return "Method - public (but not static)";
    }

    @RunMe
    public static String publicAndStaticMethod() {
        return "Method - public and static";
    }

    @RunMe
    public boolean noParamMethod(String in) {
        LOGGER.info("No param method: need in param");
        return true;
    }

    @RunMe
    public void methodThrowsException() {
        throw new RuntimeException();
    }

    public void notAnnotatedMethod() {
        LOGGER.info("Not annotated. (Not invoked.)");
    }

    @RunMe
    protected String protectedNotStaticMethod() {
        return "Method - protected (but not static)";
    }
}
