package Pacman;

import java.awt.Color;
import java.awt.Rectangle;

public class Ghost extends Rectangle
{
	private Color color;
	
	public Ghost(int x, int y, int w, int h, Color c)
	{
		super(x,y,w,h);
		color = c;
	}
	
	public Color getColor()
	{
		return color;
	}
}
