package bananaPackage;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;


/**
 * Creates a grid of squares, and gets the background image for the map
 * The grid is created based off of a raw text file
 */
public class Map {

	private Square[][] grid;

	private ImageIcon image;

	/*
	 * Generation specifics
	 * The file has 3 sections:
	 	 *  Image path (Absolute)
		 * 	Rows and coloumns
		 	* This is stored in row,col form
		 	* This information is stored because arrays have fixed size in Java
		 * 	Data for each square
		 	* The data is in form (mountain, land, mine, agr, fish)
	 * Maybe I will add a GUI to help facilitate creation of the txt file
	 */
	public Map(String file)
	{
		try
		{

			//Parse the file
			Scanner mapReader = new Scanner(new File(file));

			String imageFile = mapReader.nextLine();

			image = new ImageIcon(BananaRepublic.RELPATH + imageFile);

			String[] rowCol = mapReader.nextLine().split(",");

			grid = new Square[Integer.parseInt(rowCol[0])][Integer.parseInt(rowCol[1])];
			
			//Turn each serialized square into a square object
			for(int row = 0; row < getRows(); row++)
			{
				String[] squareString = mapReader.nextLine().split(",");
				int col = 0;
				for (String i : squareString)
				{
					grid[row][col] = new Square(i.charAt(0) == 't', i.charAt(1) == 't',
							i.charAt(2) - 48, i.charAt(3) - 48, i.charAt(4) - 48);

					col++;
				}
			}

			mapReader.close();
		}
		//Throw with different error messages based on what went wrong
		catch (FileNotFoundException fileException)
		{
			BananaRepublic.throwError("Map file not found");
		}
		catch (NumberFormatException numberException)
		{
			BananaRepublic.throwError("Invalid row/col in file");
		}
		catch (IndexOutOfBoundsException Exception)
		{
			BananaRepublic.throwError("Invalid square in file");
		}
	}

	/**
	 * Scales the background image based on a decimal value
	 * 
	 * @param scaleMod - The decimal number to use as the scale factor
	 */
	public void scaleMap(double scaleMod)
	{
		image.setImage(image.getImage().getScaledInstance(
				(int)(image.getIconWidth()*scaleMod),
				(int)(image.getIconHeight()*scaleMod), Image.SCALE_DEFAULT));
	}

	public int getRows()
	{
		return grid.length;
	}

	public int getCols()
	{
		return grid[0].length;
	}

	public ImageIcon getImageIcon()
	{
		return image;
	}

	public Square getCurrentSquare()
	{
		return getSquare(Point.getCurrentPoint());
	}

	public Square getSquare(int row, int col)
	{
		return grid[row][col];
	}

	public Square getSquare(Point p)
	{
		return grid[p.getY()][p.getX()];
	}

	/**
	 * Returns if a certain Republic has a space adjacent to a given Point
	 * 
	 * @param p - The Point to check for adjacent Republics
	 * @param r - The Republic to compare the owners to
	 * @return If a square adjacent to the Point 
	 */
	public boolean hasRepubAdjacent(Point p, Republic r)
	{
		for(int i = -1; i < 2; i++)
		{
			for(int j = -1; j < 2; j++)
			{
				try
				{
					if(getSquare(new Point(p.getX()+i, p.getY()+j)).getOwner().equals(r))
					{
						return true;
					}
				}
				catch(Exception e) {}
			}
		}
		return false;
	}
}
