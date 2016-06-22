package oss.core;

/**
 * Write a description of class ComputerPlayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ComputerPlayer extends Player
{
    

    //for the computerplayer the next move is chosen by an ai
    public Move requestMove(Interface iface, Board board) {
        return AI.move(board, 3);
    }
    
}
