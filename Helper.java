
/**
 * Write a description of class Helper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Helper
{

    public Helper()
    {

    }

    public static String popString(StringBuilder sb) {
        String s = sb.substring(0,1);
        sb.delete(0,1);
        if (s.equals(";")) return s;
        while (!sb.substring(0,1).equals(";")) {
            s += sb.substring(0,1);
            sb.delete(0,1);
        }
        return s;
    }
    
    public static boolean isValue(StringBuilder sb, String value, String error) {
        if (!Helper.popString(sb).equals(value)) {
                System.out.println(error);
                return false;
        }
        return true;
    }
}
