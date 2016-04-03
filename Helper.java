
/**
 * Helper class to simplify writeToString and readFromString methods
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Helper
{

    public Helper()
    {

    }

    //returns the substring til the next occurrence of ';' and removes the substring + ';'
    //StringBuilder is only used because it is passed by reference in contrast to string
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
