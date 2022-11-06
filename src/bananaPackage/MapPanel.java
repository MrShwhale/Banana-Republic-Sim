package bananaPackage;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import gamePanelPackage.GamePanel;

/**
 * Displays the map screen
 * Places resources automatically
 * Updates squares with owner colors
 * and the resource panel when clicked
 */
public class MapPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 215174827511353297L;
	
	public static final Border mountainBorder = BorderFactory.createLineBorder(new Color(255, 189, 43));
	public static final Border selectBorder = BorderFactory.createLineBorder(Color.WHITE, 2);
	public static final Color mapBackground = new Color(141, 223, 255);

	private Map map;
	private JLabel mapImageLabel;
	private JButton[][] mapButtons;

	public MapPanel()
	{
		GamePanel.setCurrentMap(this);
		//Create the base panel for the map
		this.setBounds(0, 0, (int)(BananaRepublic.SCREEN_WIDTH*0.45), BananaRepublic.SCREEN_HEIGHT);
		this.setLayout(null);
		this.setBackground(mapBackground);

		map = new Map(BananaRepublic.MAPFILE);

		//Map autoscaling algorithm
		double imageRatio = map.getImageIcon().getIconWidth()/(double)map.getImageIcon().getIconHeight();
		double mapRatio = this.getWidth()/(double)this.getHeight();

		double scaleMod = (mapRatio < imageRatio) ?
				this.getWidth()/(double)map.getImageIcon().getIconWidth() :
				this.getHeight()/(double)map.getImageIcon().getIconHeight();

		map.scaleMap(scaleMod);

		mapImageLabel = new JLabel();
		mapImageLabel.setBounds(0, 0, map.getImageIcon().getIconWidth(),
				map.getImageIcon().getIconHeight());

		mapImageLabel.setIcon(map.getImageIcon());
		mapImageLabel.setBorder(GamePanel.defaultBorder);
		mapImageLabel.setLayout(new GridLayout(map.getRows(), map.getCols()));

		GridLayout iconGrid = new GridLayout(3, 3);
		mapButtons = new JButton[map.getRows()][map.getCols()];

		ImageIcon mineIcon = new ImageIcon(BananaRepublic.RELPATH + "/src/Drop.png");
		ImageIcon fishIcon = new ImageIcon(BananaRepublic.RELPATH + "/src/Fish.png");
		ImageIcon agrIcon = new ImageIcon(BananaRepublic.RELPATH + "/src/Bana.png");
		
		for(int row = 0; row < map.getRows(); row++)
		{
			for(int col = 0; col < map.getCols(); col++)
			{
				mapButtons[row][col] = new JButton();
				mapButtons[row][col].addActionListener(this);
				mapButtons[row][col].setActionCommand(row + "," + col);
				mapButtons[row][col].setContentAreaFilled(false);
				mapButtons[row][col].setOpaque(false);
				mapButtons[row][col].setBorder(map.getSquare(row, col).isMountain() ? mountainBorder : GamePanel.defaultBorder);

				mapButtons[row][col].setLayout(iconGrid);
				
				//Place the resource values

				//Place mine symbol and value
				if(map.getSquare(row, col).getMine() > 0)
				{
					mapButtons[row][col].add(new JLabel(Integer.toString(map.getSquare(row, col).getMine())));
					mapButtons[row][col].add(new JLabel(mineIcon));
				}
				else
				{
					mapButtons[row][col].add(new Container());
					mapButtons[row][col].add(new Container());
				}

				mapButtons[row][col].add(new JLabel());

				//Place agriculture symbol and value
				if(map.getSquare(row, col).getAgr() > 0)
				{
					mapButtons[row][col].add(new JLabel(Integer.toString(map.getSquare(row, col).getAgr())));
					mapButtons[row][col].add(new JLabel(agrIcon));
				}
				else
				{
					mapButtons[row][col].add(new Container());
					mapButtons[row][col].add(new Container());
				}

				mapButtons[row][col].add(new JLabel());

				//Place fish symbol and value
				if(map.getSquare(row, col).getFish() > 0)
				{
					mapButtons[row][col].add(new JLabel(Integer.toString(map.getSquare(row, col).getFish())));
					mapButtons[row][col].add(new JLabel(fishIcon));
				}
				else
				{
					mapButtons[row][col].add(new Container());
					mapButtons[row][col].add(new Container());
				}

				mapButtons[row][col].add(new Container());

				mapImageLabel.add(mapButtons[row][col]);
			}
		}

		this.add(mapImageLabel);
	}

	public void updateSquareInfo()
	{
		try
		{
			GamePanel.updateCurrentSquareInfo(map.getSquare(Point.getCurrentPoint()));

		}
		catch(Exception e){}
	}

	/**
	 * Changes the color of the square and adds it to the passed republic
	 *
	 *
	 * @param r - Republic to add the square to
	 */
	public void acquireCurrentSquare(Republic r)
	{
		Square currSquare = map.getSquare(Point.getCurrentPoint());

		//Add values to the republic
		r.addSquare(currSquare);

		//Change square's ownership
		currSquare.setOwner(r);

		//Change the color of the button

		mapButtons[Point.getCurrentPoint().getY()]
				[Point.getCurrentPoint().getX()].setOpaque(true);

		mapButtons[Point.getCurrentPoint().getY()]
			[Point.getCurrentPoint().getX()].setBackground(r.getColor());
	}

	public Square getCurrentSquare()
	{
		return map.getCurrentSquare();
	}

	/**
	 * Used for finding if the current republic has a space adjacent to the current square selected
	 * Used in the BuySquarePanel for checks
	 *
	 * @return if the current point has a current republic square adjacent
	 */
	public boolean getCurrentAdjacent()
	{
		return map.hasRepubAdjacent(Point.getCurrentPoint(), Republic.getCurrentRepublic());
	}

	//Change the selected border to whatever it was before
	private void deselectPrevious()
	{
		mapButtons[Point.getCurrentPoint().getY()][Point.getCurrentPoint().getX()].setBorder(
				map.getSquare(Point.getCurrentPoint()).isMountain() ?
				mountainBorder : GamePanel.defaultBorder);
	}

	//Change current point and set the border to the selectedborder
	private void selectCurrent(int squareCol, int squareRow)
	{
		Point.setCurrentPoint(squareCol, squareRow);
		mapButtons[Point.getCurrentPoint().getY()][Point.getCurrentPoint().getX()].setBorder(selectBorder);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//If the button clicked is a square
		String command = e.getActionCommand();

		int squareRow = Integer.parseInt(command.split(",")[0]);
		int squareCol = Integer.parseInt(command.split(",")[1]);

		//try block so that it doesn't break when currentPoint is null
		try
		{
			//Deselect previous square
			deselectPrevious();
		}
		catch(NullPointerException noCurrentPoint){}
		selectCurrent(squareCol, squareRow);

		//Update square information display
		updateSquareInfo();
	}
}
