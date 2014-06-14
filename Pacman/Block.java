package Pacman;

import java.awt.Color;
import java.awt.Rectangle;

public class Block extends Rectangle
{
	static Color color = Color.black;
	boolean canHit = false;
	
	public Block()
	{
		super();
	}
	public Block(int x, int y, int w, int h)
	{
		super(x,y,w,h);
	}
	public static Color getColor()
	{
		return color;
	}
	public boolean canHit()
	{
		return canHit;
	}
}
