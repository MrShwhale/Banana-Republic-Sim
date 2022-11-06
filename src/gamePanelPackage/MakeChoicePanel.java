package gamePanelPackage;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import bananaPackage.BananaRepublic;
import bananaPackage.Republic;

public class MakeChoicePanel extends GamePanel {

	private static final long serialVersionUID = 6900771480493448234L;

	private JButton loanButton;
	private JButton squareBuyButton;
	private JButton upgradeButton;

	public MakeChoicePanel()
	{
		super();

		panelTitle.setText("Manage");

		nextButton.setText("Next");
		nextButton.setSize(400, 100);
		nextButton.setLocation((int)(relWidth*0.6) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.8));

		//Create the buttons
		loanButton = new JButton();
		loanButton.setSize((int)(relWidth*0.29), (int)(relWidth*0.3));
		loanButton.setLocation((int)(relWidth*0.03), (int)(BananaRepublic.SCREEN_HEIGHT * 0.35));
		loanButton.setBorder(defaultBorder);
		loanButton.setFont(infoFont);
		loanButton.addActionListener(this);
		loanButton.setActionCommand("loan");
		loanButton.setEnabled(Republic.getCurrentRound() > 5);
		loanButton.setText(loanButton.isEnabled() ? "<html>Manage Loans<br>Take out or pay off loans for your country" : "<html>Manage Loans<br>Loans not available until round 5");

		squareBuyButton = new JButton();
		squareBuyButton.setSize((int)(relWidth*0.29), (int)(relWidth*0.3));
		squareBuyButton.setLocation((int)(relWidth*0.06) + loanButton.getWidth(), (int)(BananaRepublic.SCREEN_HEIGHT * 0.35));
		squareBuyButton.setBorder(defaultBorder);
		squareBuyButton.setFont(infoFont);
		squareBuyButton.addActionListener(this);
		squareBuyButton.setActionCommand("square");
		squareBuyButton.setEnabled(!currentRepub.isSquareBought());
		squareBuyButton.setText(squareBuyButton.isEnabled() ? "<html>Buy new squares<br>Purchase new squares for your republic.<br>Make sure you can keep up with the system costs" : "<html>You can only buy one square per turn");

		upgradeButton = new JButton();
		upgradeButton.setSize((int)(relWidth*0.29), (int)(relWidth*0.3));
		upgradeButton.setLocation((int)(relWidth*0.09) + loanButton.getWidth() + squareBuyButton.getWidth(), (int)(BananaRepublic.SCREEN_HEIGHT * 0.35));
		upgradeButton.setBorder(defaultBorder);
		upgradeButton.setFont(infoFont);
		upgradeButton.setAlignmentX(CENTER_ALIGNMENT);
		upgradeButton.addActionListener(this);
		upgradeButton.setActionCommand("upgrade");
		upgradeButton.setEnabled(!currentRepub.isUpgradeBought());
		upgradeButton.setText(upgradeButton.isEnabled() ? "<html>Upgrade your country<br>Make your country more productive<br>Make sure you can keep up with the system costs" : "<html>You can only buy one upgrade per turn");

		repubInfoPanel = new RepublicInfoPanel(RepublicInfoDisplayMode.CHOICES, this);

		this.add(loanButton);
		this.add(squareBuyButton);
		this.add(upgradeButton);

		this.add(repubInfoPanel);
	}

	@Override
	protected void next() {
		Republic.incrementCurrentRepublic();
		if(Republic.getCurrentRepublicIndex() == Republic.getTeams())
		{
			//Draw the next screen (Resource Cards)
			Republic.setCurrentRepublic(0);

			BananaRepublic.addPanel(new ResourceCardPanel());
			shutPanel();
		}
		else
		{
			//Remove this panel, and add a new instance of one
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
			case "loan":
				BananaRepublic.addPanel(new LoanPanel());
				shutPanel();
				break;
			case "square":
				BananaRepublic.addPanel(new BuySquarePanel());
				shutPanel();
				break;
			case "upgrade":
				BananaRepublic.addPanel(new UpgradePanel());
				shutPanel();
				break;
		}
	}

}
