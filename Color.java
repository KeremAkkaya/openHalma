
/**
 * Write a description of class Color here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Color
{
    private byte r,g,b;
    public static Color black = new Color();
    public Color() {
        this(((byte)0),((byte)0),((byte)0));
    }

    public Color(byte r, byte g, byte b)
    {
        this.r=r;
        this.g=g;
        this.b=b;
    }

    public byte getR() {
        return r;
    }

    public byte getG() {
        return g;
    }

    public byte getB() {
        return b;
    }
}
