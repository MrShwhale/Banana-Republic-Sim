package gamePanelPackage;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import bananaPackage.BananaRepublic;
import bananaPackage.Republic;

public class BuySquarePanel extends ChoicePanel {

	private static final long serialVersionUID = -3309920046767997015L;

	private JLabel warning;
	private JLabel description;

	public BuySquarePanel()
	{
		super();

		panelTitle.setText("Buy squares");

		nextButton.setText("Purchase");
		nextButton.setSize(400, 100);
		nextButton.setLocation((int)(relWidth*0.6) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.8));

		//Place the labels halfway down at 1/3 and 2/3, each 20%, place amounts right below
		description = new JLabel();
		description.setSize((int)(relWidth*0.3), 60);
		description.setLocation((int)(relWidth*0.17), (int)(BananaRepublic.SCREEN_HEIGHT * 0.5));
		description.setFont(defaultFont);
		description.setText("<html>Select an unowned square adjecent to one of your own");

		//Set up the warning for invalid squares
		warning = new JLabel();
		warning.setSize((int)(relWidth*0.6), 30);
		warning.setFont(defaultFont);
		warning.setLocation((int)(relWidth*0.6) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.77));
		warning.setVisible(false);

		repubInfoPanel = new RepublicInfoPanel(RepublicInfoDisplayMode.SQUARE, this);

		this.add(warning);
		this.add(description);
		this.add(repubInfoPanel);
	}

	@Override
	protected void next() {
		//If the square is placed in a valid way (unowned and adjacent)

		//If you own the square, go back without buying
		if(getCurrentMap().getCurrentSquare().getOwner().equals(currentRepub))
		{
			BananaRepublic.addPanel(new MakeChoicePanel());
			shutPanel();
		}
		//Check if owned by someone else
		else if(!getCurrentMap().getCurrentSquare().getOwner().equals(Republic.NONE))
		{
			warning.setText("That space is already owned");
			warning.setVisible(true);
		}
		//Check if adjacent owned
		else if(!getCurrentMap().getCurrentAdjacent())
		{
			warning.setText("You must own a space nearby");
			warning.setVisible(true);
		}
		//Else buy the square, then go back to the other space
		else
		{
			currentRepub.setSquareBought(true);

			getCurrentMap().acquireCurrentSquare(currentRepub);
			getCurrentMap().updateSquareInfo();

			BananaRepublic.addPanel(new MakeChoicePanel());
			shutPanel();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand())
		{
			case "close":
				shutFrame();
				break;

			case "next":
				next();
				break;
			case "back":
				back();
				break;
		}
	}

}
