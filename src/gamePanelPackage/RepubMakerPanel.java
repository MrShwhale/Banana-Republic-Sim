package gamePanelPackage;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import bananaPackage.BananaRepublic;
import bananaPackage.Point;
import bananaPackage.Republic;
/**
 * Displays a screen where the user inputs name and chooses color
 * It does this a number of tyimes based on numTeams
 *
 * @author williampatmore
 *
 */
public class RepubMakerPanel extends GamePanel {

	private static final long serialVersionUID = 3596692455093864172L;

	private ArrayList<String> ownershipColorListModifiable;

	private JList<String> colorPicker;
	private JLabel colorDescription;
	private JTextField republicName;
	private JLabel nameDescription;
	private JLabel repubErrorWarning;
	private JLabel squareInfoDescription;

	public RepubMakerPanel()
	{
		super();

		ownershipColorListModifiable = new ArrayList<>(Arrays.asList(BananaRepublic.ownershipColorsKeys()));

		panelTitle.setText("New Republic");

		//Abnormal squareInfo spot: higher than usual, because it is part of the main content
		squareInfo.setLocation((int)(relWidth*0.03), (int)(BananaRepublic.SCREEN_HEIGHT*0.4));

		nextButton.setText("Create Republic");
		nextButton.setSize(600, 120);
		nextButton.setLocation((int)(relWidth*0.5) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.8));

		colorPicker = new JList<String>(ownershipColorListModifiable.toArray(new String[1]));
		colorPicker.setFont(defaultFont);
		colorPicker.setSize(140, 250);
		colorPicker.setLocation((int)(relWidth*0.7) -
				(int)(colorPicker.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.4));

		colorDescription = new JLabel("Pick a color");
		colorDescription.setFont(defaultFont);
		colorDescription.setSize(140, 24);
		colorDescription.setLocation((int)(relWidth*0.7) -
				(int)(colorPicker.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.37));

		republicName = new JTextField();
		republicName.setFont(defaultFont);
		republicName.setSize(150, 24);
		republicName.setLocation((int)(relWidth*0.45) -
				(int)(republicName.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.4));

		nameDescription = new JLabel("Pick a name");
		nameDescription.setFont(defaultFont);
		nameDescription.setSize(140, 24);
		nameDescription.setLocation((int)(relWidth*0.45) -
				(int)(republicName.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.37));


		squareInfoDescription = new JLabel("Pick a square");
		squareInfoDescription.setFont(defaultFont);
		squareInfoDescription.setSize(140, 24);
		squareInfoDescription.setLocation((int)(relWidth*0.03), (int)(BananaRepublic.SCREEN_HEIGHT*0.37));


		repubErrorWarning = new JLabel();
		repubErrorWarning.setSize(300, 34);
		repubErrorWarning.setLocation((int)(relWidth*0.5) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.77));
		repubErrorWarning.setFont(defaultFont);
		repubErrorWarning.setVisible(false);

		this.add(colorDescription);
		this.add(colorPicker);
		this.add(nameDescription);
		this.add(squareInfoDescription);
		this.add(republicName);
		this.add(repubErrorWarning);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case "close":
				shutFrame();
				break;

			case "next":
				next();
		}
	}

	@Override
	protected void next() {

		//Validate the input

		//If no square is selected
		if(Point.getCurrentPoint() == null)
		{
			repubErrorWarning.setVisible(true);
			repubErrorWarning.setText("Select a home square");
			return;
		}
		//If the length is not right
		else if(republicName.getText().length() < 1 || republicName.getText().length() > 15)
		{
			repubErrorWarning.setVisible(true);
			repubErrorWarning.setText("Republic name is too " +
				(republicName.getText().length() < 1 ? "short" : "long"));
			return;
		}
		//If the name is already taken
		else if (Republic.isDuplicateRepublic(new Republic(republicName.getText(), "White"))
				|| republicName.getText().equals("No one"))
		{
			repubErrorWarning.setVisible(true);
			repubErrorWarning.setText("That name is already taken");
			return;
		}
		//If no color is selected
		else if (colorPicker.getSelectedIndex() == -1)
		{
			repubErrorWarning.setVisible(true);
			repubErrorWarning.setText("Select a team color");
			return;
		}
		//If the home square is owned by someone else
		else if (!getCurrentMap().getCurrentSquare().getOwner().equals(Republic.NONE))
		{
			repubErrorWarning.setVisible(true);
			repubErrorWarning.setText("Someone else owns that square");
			return;
		}

		//Create a new Republic
		Republic.addRepublic(new Republic(republicName.getText(),
			colorPicker.getSelectedValue()));

		currentRepub = Republic.getCurrentRepublic();
		
		//Give them the selected square as a home square
		getCurrentMap().acquireCurrentSquare(currentRepub);
		getCurrentMap().updateSquareInfo();

		//Check if it is time to move on
		Republic.incrementCurrentRepublic();
		if(Republic.getCurrentRepublicIndex() == Republic.getTeams())
		{
			//Draw the next screen
			Republic.setCurrentRepublic(0);

			BananaRepublic.addPanel(new ResourceCardPanel());
			shutPanel();
		}
		else
		{
			//Redraw this screen
			repubErrorWarning.setVisible(false);
			republicName.setText("");

			//Remove the slected color from the colorPicker
			ownershipColorListModifiable.remove(colorPicker.getSelectedIndex());

			colorPicker.setVisible(false);
			getCurrentPanel().remove(colorPicker);

			colorPicker = new JList<>(ownershipColorListModifiable.toArray(new String[1]));
			colorPicker.setFont(defaultFont);
			colorPicker.setSize(140, ownershipColorListModifiable.size()*25);
			colorPicker.setLocation((int)(getCurrentPanel().getWidth()*0.7) -
					(int)(colorPicker.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.4));
			colorPicker.setVisible(true);

			getCurrentPanel().add(colorPicker);
		}
	}

}
