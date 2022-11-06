package gamePanelPackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import bananaPackage.BananaRepublic;
import bananaPackage.MapPanel;
import bananaPackage.Republic;
import bananaPackage.Square;

/**
 * Abstract superclass for every main panel in the game
 */
public abstract class GamePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 5448578525064203835L;
	private static MapPanel currentMap;
	private static GamePanel currentPanel;
	protected JButton nextButton;
	protected JButton closeButton;
	protected JLabel panelTitle;
	protected JLabel squareInfo;
	protected RepublicInfoPanel repubInfoPanel;

	public static final NumberFormat numForm = NumberFormat.getInstance();

	public static final Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK);
	public static final Color backgroundColor = new Color(235, 235, 235);
	public static final Font defaultFont = new Font("Sans Serif", Font.PLAIN, 18);
	public static final Font infoFont = new Font("Sans Serif", Font.PLAIN, 24);
	public static final Font titleFont = new Font("Sans Serif", Font.PLAIN, 72);
	
	protected static int relWidth;

	protected Republic currentRepub;

	protected GamePanel()
	{
		super();

		try {currentRepub = Republic.getCurrentRepublic();}
		catch(Exception IndexOutOfBoundsError) {}

		this.setLayout(null);
		this.setBounds((int)(BananaRepublic.SCREEN_WIDTH*0.45), 0, (int)(BananaRepublic.SCREEN_WIDTH*0.55), BananaRepublic.SCREEN_HEIGHT);

		//This panel is the new current
		setCurrentPanel(this);

		this.setVisible(true);
		this.setBackground(backgroundColor);

		//Layout/size settings

		relWidth = this.getWidth();

		//Makes and resizes all of the constant things

		panelTitle = new JLabel();
		panelTitle.setFont(GamePanel.titleFont);
		panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.setVerticalAlignment(SwingConstants.CENTER);
		panelTitle.setSize(700, 100);
		panelTitle.setLocation((int)(relWidth*0.4) -
				(int)(panelTitle.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.02));

		closeButton = new JButton("Close Game");
		closeButton.setFont(defaultFont);
		closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		closeButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		closeButton.setSize(150, 60);
		closeButton.setLocation((int)(relWidth*0.89) -
				(int)(closeButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.03));
		closeButton.addActionListener(this);
		closeButton.setActionCommand("close");

		squareInfo = new JLabel();
		squareInfo.setSize((int)(relWidth*.2), (int)(BananaRepublic.SCREEN_HEIGHT*0.2));
		squareInfo.setLocation((int)(relWidth*0.03), (int)(BananaRepublic.SCREEN_HEIGHT*0.78));
		squareInfo.setBorder(defaultBorder);
		squareInfo.setVerticalAlignment(SwingConstants.TOP);

		try {
			updateSquareInfo(getCurrentMap().getCurrentSquare());
		}
		catch (Exception e)
		{
			squareInfo.setText("Select a square");
		}
		squareInfo.setFont(defaultFont);

		nextButton = new JButton();
		nextButton.setFont(titleFont);
		nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		nextButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		nextButton.addActionListener(this);
		nextButton.setActionCommand("next");

		this.add(squareInfo);
		this.add(panelTitle);
		this.add(closeButton);
		this.add(nextButton);
	}

	/**
	 * Changes the squareInfo label to a new square's information on the current panel
	 * 
	 * @param currSquare - The new square's info to display
	 */
	public static void updateCurrentSquareInfo(Square currSquare)
	{
		currentPanel.updateSquareInfo(currSquare);
	}

	/**
	 * Changes the squareInfo label to a new square's information
	 * @param currSquare - The new square's info to display
	 */
	public void updateSquareInfo(Square currSquare)
	{
		squareInfo.setText("<html>" +
				"Mine: " + currSquare.getMine() + "<br>" +
				"Fish: " + currSquare.getFish() + "<br>" +
				"Agriculture: " + currSquare.getAgr() + "<br>" +
				(currSquare.isLand() ? "Land" : "Ocean") + "<br>" +
				(currSquare.isMountain() ? "Mountainous" : "Flat") + "<br>" +
				"Owner: " + "<br>" +
				(currSquare.getOwner().getName()));
	}

	public static void setCurrentPanel(GamePanel currentPanel)
	{
		GamePanel.currentPanel = currentPanel;
	}

	public static GamePanel getCurrentPanel()
	{
		return currentPanel;
	}

	public static void setCurrentMap(MapPanel currentMap)
	{
		GamePanel.currentMap = currentMap;
	}

	/**
	 * Resets the republic info 
	 * Reloads the info panel
	 */
	public void redrawRepubInfo()
	{
		//The item is removed so that it redisplays correctly
		repubInfoPanel.setVisible(false);
		this.remove(repubInfoPanel);
		repubInfoPanel.redraw();
		repubInfoPanel.setVisible(true);
		this.add(repubInfoPanel);
	}

	public static MapPanel getCurrentMap()
	{
		return currentMap;
	}

	/**
	 * Closes the frame
	 */
	public void shutFrame()
	{
		BananaRepublic.shutFrame();
	}

	/**
	 * Removes the panel and readies the screen for another one
	 */
	public void shutPanel()
	{
		this.setVisible(false);
		BananaRepublic.removePanel(this);
	}

	public static boolean isIntegral(String strNum) {
	    if((strNum == null) || (strNum.length() == 0))
	    {
	    	return false;
	    }

	    try {
	        @SuppressWarnings("unused")
			int i = Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	/**
	 * Adds a panel to the frame
	 * @param p - The panel to add
	 */
	public void addPanel(JPanel p)
	{
		BananaRepublic.addPanel(p);
	}

	protected abstract void next();

	@Override
	public abstract void actionPerformed(ActionEvent e);

}
