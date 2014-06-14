package Pacman;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class World 
{
	private final int SIZE = 15;
	
	private String map;
	private ArrayList<Rectangle> walls = new ArrayList<Rectangle>(), pills = new ArrayList<Rectangle>(),
			specials = new ArrayList<>();
	private BufferedReader in;
	private File file = new File("src/Pacman/world.txt");

	public World()
	{
//		try
//		{
//			in = new BufferedReader(new FileReader(file));
//			StringBuilder sb = new StringBuilder();
//			String line = in.readLine();
//			while(line != null)
//			{
//				sb.append(line);
//				line = in.readLine();
//			}
			map = "0000000000000000000000000000000011111111111110000111111111111001000001000001000010000100000100200000100000100001000010000020011111111111111111111111111111001000001001000000000010100000100100000100100000000001010000010010000010010000000000101000001001111111001111100111110111111100000000100000090090000010000000999999010099999999999001099999999999901009900000009900109999990000000100990999990990010000000999999919999099999099991999999900000001009900000009900100000009999990100999999999990010999999999999010099000000099001099999900000001009900000009900100000000111111111111110111111111111110010000010000001010000001000001002000001000000101000000100000200111100111111111111111110011110000010010010000000001001001000001111111001111000111100111111100100000000000100010000000000010010000000000010001000000000001001111111111111111111111111111100000000000000000000000000000000";		
//			}		
//		catch(IOException ioe)
//		{
//			ioe.printStackTrace();
//		}
//		finally
//		{
//			try 
//			{
//				in.close();
//			} 
//			catch (IOException ioe) 
//			{
//				ioe.printStackTrace();
//			}
//		}
		for(int j = 0; j < 28; j++)
		{
			for(int i = 0; i < 31; i++)
			{
				if(map.charAt(i+j*31) == '0')
				{
					walls.add(new Rectangle(i*SIZE, j*SIZE, SIZE, SIZE));
				}
				if(map.charAt(i+j*31) == '1')
				{
					pills.add(new Rectangle(i*SIZE, j*SIZE, SIZE, SIZE));
				}
				if(map.charAt(i+j*31) == '2')
				{
					specials.add(new Rectangle(i*SIZE, j*SIZE, SIZE, SIZE));
				}
			}
		}

	}

	public String getMap()
	{
		return map;
	}
	public ArrayList<Rectangle> getWalls()
	{
		return walls;
	}
	public ArrayList<Rectangle> getPills()
	{
		return pills;
	}
	public ArrayList<Rectangle> getSpecials()
	{
		return specials;
	}
}
