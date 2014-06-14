package Pacman;

import java.awt.Color;
import java.awt.Rectangle;

public class Pacman extends Rectangle
{
	private Color color;
	
	public Pacman()
	{
		super();
		color = Color.yellow;
	}
	public Pacman(int x, int y, int width, int height)
	{
		super(x,y,width,height);
		color=Color.yellow;
	}
	
	public Color getColor()
	{
		return color;
	}
}
