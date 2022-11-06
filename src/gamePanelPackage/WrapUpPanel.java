package gamePanelPackage;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import bananaPackage.BananaRepublic;
import bananaPackage.Republic;

public class WrapUpPanel extends GamePanel {

	private static final long serialVersionUID = -1057938946434779749L;

	private JLabel wrapUpText;

	public WrapUpPanel()
	{
		super();

		panelTitle.setText("Round " + Republic.getCurrentRound());

		nextButton.setText("Next");
		nextButton.setSize(400, 100);
		nextButton.setLocation((int)(relWidth*0.6) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.8));

		wrapUpText = new JLabel();
		wrapUpText.setSize((int)(relWidth*0.5), (int)(relWidth*0.45));
		wrapUpText.setLocation((int)(relWidth*0.25), (int)(BananaRepublic.SCREEN_HEIGHT * 0.3));
		wrapUpText.setBorder(defaultBorder);
		wrapUpText.setFont(infoFont);

		setText();

		this.add(wrapUpText);
	}

	/**
	 * Set the text of the label to the current republic's amount
	 */
	private void setText()
	{
		currentRepub.addLoanInterest();

		int moneyGained = (int)((currentRepub.getProductivity()*
				currentRepub.getTotalResourceProfit()) - (currentRepub.getTotalResourceProfit()));

		long finalMoney = currentRepub.getMoney() + moneyGained - currentRepub.getSystemCosts();

		wrapUpText.setText("<html>" +
							"Name: " + currentRepub.getName() + "<br>" +
							"Color: " + currentRepub.getColorText() + "<br>" +
							"Loan amount: $" + numForm.format(currentRepub.getLoanAmount()) + "<br>" +
							"Previous balance: $" + numForm.format(currentRepub.getMoney()) + "<br>" +
							"Productivity: " + currentRepub.getProductivity() + "<br>" +
							"Balance " + (currentRepub.getProductivity() > 1 ? "gained" : "lost") + " from productivity: $" +
								numForm.format(moneyGained) + "<br>" +
							"System costs: $" + numForm.format(currentRepub.getSystemCosts()) + "<br>" +
							"Final balance: $" + numForm.format(finalMoney) + "<br>");
		currentRepub.setMoney(finalMoney);
	}

	@Override
	protected void next() {
		//Check if it is time to move on
		Republic.incrementCurrentRepublic();
		if(Republic.getCurrentRepublicIndex() == Republic.getTeams())
		{
			//Check if the game is over forever
			if(Republic.getCurrentRound() == Republic.getRounds())
			{
				BananaRepublic.addPanel(new WinPanel());
				shutPanel();
				return;
			}

			Republic.incrementRound();

			//Draw the next screen (upgrades)
			Republic.setCurrentRepublic(0);

			BananaRepublic.addPanel(new MakeChoicePanel());
			shutPanel();
		}
		else
		{
			//Remove this panel, and add a new instance of one
			BananaRepublic.addPanel(new WrapUpPanel());
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
		}
	}

}
