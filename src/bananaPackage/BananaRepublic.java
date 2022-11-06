package bananaPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
/**
 * This is a simulation game made for a CSIA in 2022-3
 * It is based on a game by an economics teacher
 * Rules: https://docs.google.com/document/d/1sXfYqytE1hCIdbrrkHhXewcwPoE4zzC7
 * Quick change guide:
 	* Change map file here
 	* Change Productivity values/max loan/price in Development 
 	* Change/add cards in Republic (Further documentation in the class)
 */

/**
 *  POTENTIAL FIXES
 * 
 *	Name
 *	New map
 *	Hide square values
 *	'Acquiring' vs buying
 *	Random land home square
 *	Color coding upgrades
 */

public class BananaRepublic {
	//Screen size constants
	private static final Dimension SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int SCREEN_WIDTH = (int)SIZE.getWidth();
	public static final int SCREEN_HEIGHT = (int)SIZE.getHeight();

	//Frame where the game will take place
	private static final JFrame GAMEFRAME = new JFrame();

	//Ownership color map
	private static final HashMap<String, Color> OWNERSHIPCOLORS = new HashMap<>(10);

	public static final String RELPATH = new File("").getAbsolutePath();
	
	//The map txt file
	public static final String MAPFILE = RELPATH + "/src/map.txt";
	
	public static void main(String[] args) {
		
		//Make the frame
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		DisplayMode defaultDisplayMode = gd.getDisplayMode();

		//Set up the color map
		setColorMap();

		//Set up the frame
		GAMEFRAME.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		//Set default state of the frame
		GAMEFRAME.setUndecorated(false);
		GAMEFRAME.setIgnoreRepaint(true);
		GAMEFRAME.setResizable(false);
		GAMEFRAME.setAlwaysOnTop(false);
		GAMEFRAME.setLayout(null);

		//Add the panel to the frame
		GAMEFRAME.add(new StartPanel());

		//Finalize the frame
		GAMEFRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		GAMEFRAME.setTitle("Banana Republic");
		GAMEFRAME.pack();

		gd.setFullScreenWindow(GAMEFRAME);
		gd.setDisplayMode(defaultDisplayMode);
	}

	/**
	 * Adds a panel to the game frame
	 * 
	 * @param p - The panel to add
	 */
	public static void addPanel(JPanel p)
	{
		GAMEFRAME.add(p);
	}

	
	/**
	 * Returns the value associated with the String key passed
	 * 
	 * @param colorString - the name of the color to search the map for
	 * @return The Color that corresponds to the name, or null if none is found
	 */
	public static Color stringToColor(String colorString)
	{
		return OWNERSHIPCOLORS.get(colorString);
	}

	/**
	 * Returns the string array of all the color names for ownership
	 */
	public static String[] ownershipColorsKeys()
	{
		return OWNERSHIPCOLORS.keySet().toArray(new String [1]);
	}

	/**
	 * Maps traditional color names to hex values 
	 * Mappings are stored in the OWNERSHIPCOLORS map
	 */
	private static void setColorMap()
	{
		OWNERSHIPCOLORS.put("Red", Color.decode("0xff0000"));
        OWNERSHIPCOLORS.put("Maroon", Color.decode("0x822e2e"));
        OWNERSHIPCOLORS.put("Orange", Color.decode("0xff7700"));
        OWNERSHIPCOLORS.put("Yellow", Color.decode("0xffee00"));
        OWNERSHIPCOLORS.put("Lime", Color.decode("0x89ff85"));
        OWNERSHIPCOLORS.put("Green", Color.decode("0x006609"));
        OWNERSHIPCOLORS.put("Teal", Color.decode("0x48bdb1"));
        OWNERSHIPCOLORS.put("Blue", Color.decode("0x415aef"));
        OWNERSHIPCOLORS.put("Purple", Color.decode("0x5b369c"));
        OWNERSHIPCOLORS.put("Pink", Color.decode("0xff2ef8"));
	}

	/**
	 * Disposes the frame and shuts down the program
	 */
	public static void shutFrame()
	{
		GAMEFRAME.dispose();
	}

	/**
	 * Removes a panel from the frame
	 * 
	 * @param gamePanel - the JPanel to remove
	 */
	public static void removePanel(JPanel gamePanel)
	{
		GAMEFRAME.remove(gamePanel);
	}
	
	/**
	 * Displays and error to the user, then closes the frame
	 * Code from: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
	 * 
	 * @param message - The error message to display
	 */
	public static void throwError(String message)
	{
		shutFrame();
		JFrame error = new JFrame();
		JOptionPane.showMessageDialog(error,
			    message,
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
}
