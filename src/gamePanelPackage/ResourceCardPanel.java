package gamePanelPackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;

import bananaPackage.BananaRepublic;
import bananaPackage.Republic;

/**
 * Displays the resource cards
 * The user must click on each one to
 *
 * @author williampatmore
 *
 */
public class ResourceCardPanel extends GamePanel {

	private static final long serialVersionUID = 2322213977853781435L;

	public static final Border clickBorder = BorderFactory.createLineBorder(Color.RED);

	private JButton mineCard;
	private JButton agrCard;
	private JButton fishCard;

	private JLabel mineAmount;
	private JLabel fishAmount;
	private JLabel agrAmount;

	public ResourceCardPanel()
	{
		super();

		currentRepub.setUpgradeBought(false);
		currentRepub.setSquareBought(false);

		panelTitle.setText("Resource Cards");

		nextButton.setText("Click all");
		nextButton.setSize(400, 100);
		nextButton.setLocation((int)(relWidth*0.6) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.8));
		nextButton.setEnabled(false);

		repubInfoPanel = new RepublicInfoPanel(RepublicInfoDisplayMode.CARDDRAW, this);

		//Create the buttons
		mineCard = new JButton();
		mineCard.setSize((int)(relWidth*0.29), (int)(relWidth*0.3));
		mineCard.setLocation((int)(relWidth*0.03), (int)(BananaRepublic.SCREEN_HEIGHT * 0.35));
		mineCard.setBorder(defaultBorder);
		mineCard.addActionListener(this);
		mineCard.setActionCommand("mineCard");

		agrCard = new JButton();
		agrCard.setSize((int)(relWidth*0.29), (int)(relWidth*0.3));
		agrCard.setLocation((int)(relWidth*0.06) + mineCard.getWidth(), (int)(BananaRepublic.SCREEN_HEIGHT * 0.35));
		agrCard.setBorder(defaultBorder);
		agrCard.addActionListener(this);
		agrCard.setActionCommand("agrCard");

		fishCard = new JButton();
		fishCard.setSize((int)(relWidth*0.29), (int)(relWidth*0.3));
		fishCard.setLocation((int)(relWidth*0.09) + mineCard.getWidth() + agrCard.getWidth(), (int)(BananaRepublic.SCREEN_HEIGHT * 0.35));
		fishCard.setBorder(defaultBorder);
		fishCard.addActionListener(this);
		fishCard.setActionCommand("fishCard");

		mineAmount = new JLabel();
		mineAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
		mineAmount.setSize(100, 18);
		mineAmount.setLocation(mineCard.getX() + (int)(mineCard.getWidth() * 0.5) - (int)(mineAmount.getWidth() * 0.5),
				mineCard.getY() + mineCard.getHeight() + (int)(relWidth*0.02));


		agrAmount = new JLabel();
		agrAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
		agrAmount.setSize(100, 18);
		agrAmount.setLocation(agrCard.getX() + (int)(agrCard.getWidth() * 0.5) - (int)(agrAmount.getWidth() * 0.5),
				agrCard.getY() + agrCard.getHeight() + (int)(relWidth*0.02));

		fishAmount = new JLabel();
		fishAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
		fishAmount.setSize(100, 18);
		fishAmount.setLocation(fishCard.getX() + (int)(fishCard.getWidth() * 0.5) - (int)(fishAmount.getWidth() * 0.5),
				fishCard.getY() + fishCard.getHeight() + (int)(relWidth*0.02));

		this.add(agrCard);
		this.add(mineCard);
		this.add(fishCard);

		this.add(mineAmount);
		this.add(fishAmount);
		this.add(agrAmount);

		this.add(repubInfoPanel);

		setCardText();
	}

	private void setCardText()
	{
		mineCard.setText("<html>" + currentRepub.drawMineCard());
		mineAmount.setText("$" + numForm.format(currentRepub.getMineProfit()));

		fishCard.setText("<html>" + currentRepub.drawFishCard());
		fishAmount.setText("$" + numForm.format(currentRepub.getFishProfit()));

		agrCard.setText("<html>" + currentRepub.drawAgrCard());
		agrAmount.setText("$" + numForm.format(currentRepub.getAgrProfit()));
	}

	@Override
	protected void next() {
		//Check if it is time to move on
		Republic.incrementCurrentRepublic();
		if(Republic.getCurrentRepublicIndex() == Republic.getTeams())
		{
			//Draw the next screen (fate cards)
			Republic.setCurrentRepublic(0);

			BananaRepublic.addPanel(new FateCardPanel());
			shutPanel();
		}
		else
		{
			//Remove this panel, and add a new instance of one
			BananaRepublic.addPanel(new ResourceCardPanel());
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

			case "mineCard":
				currentRepub.addMineProfit();
				redrawRepubInfo();
				mineCard.setBorder(clickBorder);
				mineCard.setEnabled(false);
				break;

			case "fishCard":
				currentRepub.addFishProfit();
				redrawRepubInfo();
				fishCard.setBorder(clickBorder);
				fishCard.setEnabled(false);
				break;

			case "agrCard":
				currentRepub.addAgrProfit();
				redrawRepubInfo();
				agrCard.setBorder(clickBorder);
				agrCard.setEnabled(false);
				break;
		}

		nextButton.setEnabled(!mineCard.isEnabled() && !fishCard.isEnabled() && !agrCard.isEnabled());

		if(nextButton.isEnabled())
		{
			nextButton.setText("Continue");
		}
	}

}
