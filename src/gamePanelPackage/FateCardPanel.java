package gamePanelPackage;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import bananaPackage.BananaRepublic;
import bananaPackage.Republic;

public class FateCardPanel extends GamePanel {

	private static final long serialVersionUID = -1445675853864279386L;

	private JButton fateCard;
	private JLabel fateAmount;

	public FateCardPanel()
	{
		super();

		panelTitle.setText("Fate Cards");

		nextButton.setText("Click Card");
		nextButton.setSize(400, 100);
		nextButton.setLocation((int)(relWidth*0.6) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.8));
		nextButton.setEnabled(false);

		//Create the card button
		fateCard = new JButton();
		fateCard.setSize((int)(relWidth*0.4), (int)(relWidth*0.3));
		fateCard.setLocation((int)(relWidth*0.35), (int)(BananaRepublic.SCREEN_HEIGHT * 0.35));
		fateCard.setBorder(defaultBorder);
		fateCard.addActionListener(this);
		fateCard.setActionCommand("fateCard");

		fateAmount = new JLabel();
		fateAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
		fateAmount.setSize(100, 18);
		fateAmount.setLocation(fateCard.getX() + (int)(fateCard.getWidth() * 0.5) - (int)(fateAmount.getWidth() * 0.5),
				fateCard.getY() + fateCard.getHeight() + (int)(relWidth*0.02));

		repubInfoPanel = new RepublicInfoPanel(RepublicInfoDisplayMode.CARDDRAW, this);

		this.add(fateAmount);
		this.add(fateCard);

		this.add(repubInfoPanel);

		setCardText();
	}

	/**
	 * Sets the fate card text and how much money has been made from it
	 */
	private void setCardText() {
		fateCard.setText("<html>" + currentRepub.drawFateCard());
		fateAmount.setText("$" + numForm.format(currentRepub.getFateProfit()));
	}

	@Override
	protected void next() {
		Republic.incrementCurrentRepublic();
		if(Republic.getCurrentRepublicIndex() == Republic.getTeams())
		{
			//Draw the next screen (wrap up)
			Republic.setCurrentRepublic(0);

			BananaRepublic.addPanel(new WrapUpPanel());
			shutPanel();
		}
		else
		{
			//Remove this panel, and add a new instance of one
			BananaRepublic.addPanel(new FateCardPanel());
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

			case "fateCard":
				currentRepub.addFateProfit();
				redrawRepubInfo();
				fateCard.setBorder(ResourceCardPanel.clickBorder);
				fateCard.setEnabled(false);
				break;
		}

		nextButton.setEnabled(!fateCard.isEnabled());

		if(nextButton.isEnabled())
		{
			nextButton.setText("Continue");
		}
	}

}
