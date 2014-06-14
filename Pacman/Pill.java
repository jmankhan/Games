package Pacman;

import java.awt.Color;

public class Pill extends Block
{
	public Pill(int x, int y, int w, int h)
	{
		super(x,y,w,h);
		color = Color.yellow.darker();
	}
}
