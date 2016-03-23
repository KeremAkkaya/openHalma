
/**
 * Write a description of class BoardFactory here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoardFactory
{
    private static BoardFactory instanceBoardFactory = null;
    
    private BoardFactory()
    {
        
    }
    
    
    public static BoardFactory getInstance() {
        if (instanceBoardFactory == null) {
            instanceBoardFactory = new BoardFactory();
        }
        return instanceBoardFactory;
    }
    
}
