
/**
 * Write a description of class Graphical here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;


public class Graphical extends JPanel implements Interface		//interface as interface
{
	/**
	 * Added by Eclipse, JPanel seems to implement java.io.Serializable --> The serialization runtime associates with each serializable class a version number, called a serialVersionUID, which is used during deserialization to verify that the sender and receiver of a serialized object have loaded classes for that object that are compatible with respect to serialization.
	 */
	private static final long serialVersionUID = 1L;

	public Graphical()
    {		
    }

    public void printBoard(Board b) {
    	 JFrame window = new JFrame();
         window.setTitle("openHalma");
         window.setSize(800,600);
    	 
    	 JPanel panel = new JPanel();
		 panel.setBackground(Color.white);
		 window.add(panel);
		 window.setVisible(true);
		 return;
	 }
    
    public Move requestMove(Player p) {
        return null;
    }
}
