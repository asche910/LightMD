package util;

/**
 * Print Utils
 */
public class PrintUtils {

    public static void println(){
        System.out.println();
    }

    public static void println(Object object){
        if (object == null){
            System.out.println("null");
        }else {
            System.out.println(object.toString());
        }
    }

    public static void printf(String format, Object ... args){
        System.out.printf(format, args);
    }
}
