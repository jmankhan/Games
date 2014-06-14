package Pacman;

import java.awt.Color;

public class Wall extends Block
{
	public Wall()
	{
		super();
	}
	public Wall(int x,int y, int w, int h)
	{
		super(x,y,w,h);
		color = Color.blue;
	}
	public static Color getColor()
	{
		return color;
	}
}
