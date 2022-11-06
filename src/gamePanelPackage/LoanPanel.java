package gamePanelPackage;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import bananaPackage.BananaRepublic;

public class LoanPanel extends ChoicePanel {

	private static final long serialVersionUID = -6101971719676882111L;

	private JLabel takeLabel;
	private JTextField takeAmount;

	private JLabel payLabel;
	private JTextField payAmount;

	private JLabel warning;

	public LoanPanel()
	{
		super();

		panelTitle.setText("Manage Loans");

		nextButton.setText("Accept");
		nextButton.setSize(400, 100);
		nextButton.setLocation((int)(relWidth*0.6) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.8));

		//Place the labels halfway down at 1/3 and 2/3, each 20%, place amounts right below
		takeLabel = new JLabel();
		takeLabel.setSize((int)(relWidth*0.3), 30);
		takeLabel.setLocation((int)(relWidth*0.17), (int)(BananaRepublic.SCREEN_HEIGHT * 0.5));
		takeLabel.setFont(infoFont);
		takeLabel.setText("Millions to take");

		takeAmount = new JTextField();
		takeAmount.setSize((int)(relWidth*0.3), 30);
		takeAmount.setLocation((int)(relWidth*0.17), (int)(BananaRepublic.SCREEN_HEIGHT * 0.5) + 30);
		takeAmount.setFont(infoFont);

		payLabel = new JLabel();
		payLabel.setSize((int)(relWidth*0.3), 30);
		payLabel.setLocation((int)(relWidth*0.53), (int)(BananaRepublic.SCREEN_HEIGHT * 0.5));
		payLabel.setFont(infoFont);
		payLabel.setText("Millions to pay");

		payAmount = new JTextField();
		payAmount.setSize((int)(relWidth*0.3), 30);
		payAmount.setLocation((int)(relWidth*0.53), (int)(BananaRepublic.SCREEN_HEIGHT * 0.5) + 30);
		payAmount.setFont(infoFont);

		warning = new JLabel("That is not a valid amount");
		warning.setSize((int)(relWidth*0.3), 30);
		warning.setFont(defaultFont);
		warning.setLocation((int)(relWidth*0.6) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.77));
		warning.setVisible(false);

		repubInfoPanel = new RepublicInfoPanel(RepublicInfoDisplayMode.LOAN, this);

		this.add(warning);
		this.add(payAmount);
		this.add(payLabel);
		this.add(takeAmount);
		this.add(takeLabel);

		this.add(repubInfoPanel);
	}

	@Override
	protected void next() {
		//Check if the input is a valid number
		//Add a zero to the start of the string to account for null input
		if(isIntegral("0" + takeAmount.getText()) && isIntegral("0" + payAmount.getText()))
		{
			//If the amount to take is 0, then ALWAYS let the user leave
			if(!(Integer.parseInt("0" + takeAmount.getText()) == 0) &&
					Integer.parseInt(takeAmount.getText()) + currentRepub.getLoanThisRound() > currentRepub.getMaxLoan())
			{
				warning.setVisible(true);
				warning.setText("You are taking too much");
			}
			else
			{
			currentRepub.takeLoan(Integer.parseInt("0" + takeAmount.getText()));
			currentRepub.payLoan(Integer.parseInt("0" + payAmount.getText()));

			BananaRepublic.addPanel(new MakeChoicePanel());
			shutPanel();
			}
		}
		else
		{
			warning.setVisible(true);
			warning.setText("Not a valid number");
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
