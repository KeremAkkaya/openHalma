package oss.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.Color;
/**
 * Write a description of class LocalPlayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LocalPlayer extends Player
{
    public LocalPlayer(FIELD_VALUE fieldValue, Color color) {
        super(fieldValue, color);
    }
    
    public Move requestMove(Interface iface, Board board) {
        return null;
    }
}
