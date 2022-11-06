package gamePanelPackage;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import bananaPackage.Republic;
/**
 * Displays the information of a republic
 * Can be set to display different information with different RepublicInfoDisplayMode
 */

public class RepublicInfoPanel extends JPanel {

	private static final long serialVersionUID = -4325203586061534621L;
	private RepublicInfoDisplayMode displayMode;
	private Republic republic;

	//Default variables
	private JLabel name;
	private JLabel color;
	private JLabel balance;

	//Resources
	private JLabel mine;
	private JLabel agr;
	private JLabel fish;

	//Loan amount
	private JLabel loan;
	private JLabel maxLoan;

	//System costs
	private JLabel oldCosts;
	private JLabel newCosts;

	//Upgrade cost
	private JLabel cost;
	private long upgradeCost;
	private long upgradeSystemCosts;

	public RepublicInfoPanel(RepublicInfoDisplayMode displayMode, Component parent)
	{
		int relWidth = parent.getWidth();
		int relHeight = parent.getHeight();

		this.setBorder(GamePanel.defaultBorder);
		this.setLocation((int)(relWidth*0.05), (int)(relHeight * 0.14));

		this.displayMode = displayMode;
		republic = Republic.getCurrentRepublic();

		this.setSize((int)(relWidth * 0.55), (int)(relHeight * 0.08));
		this.setLayout(new GridLayout(3,2));

		upgradeCost = 0;
		upgradeSystemCosts = republic.getSystemCosts();

		redraw();
	}

	public void redraw()
	{
		removeAll();

		switch(displayMode)
		{
			case CARDDRAW:
				name = new JLabel();
				name.setText("Name: " + republic.getName());

				agr = new JLabel();
				agr.setText("Agriculture: " + GamePanel.numForm.format(republic.getAgr()));

				color = new JLabel();
				color.setText("Color: " + republic.getColorText());

				mine = new JLabel();
				mine.setText("Mine: " + GamePanel.numForm.format(republic.getMine()));

				balance = new JLabel();
				balance.setText("Balance: $" + GamePanel.numForm.format(republic.getMoney()));

				fish = new JLabel();
				fish.setText("Fish: " + GamePanel.numForm.format(republic.getFish()));

				this.add(name);
				this.add(agr);
				this.add(color);
				this.add(mine);
				this.add(balance);
				this.add(fish);
				break;
			case LOAN:

				name = new JLabel();
				name.setText("Name: " + republic.getName());

				loan = new JLabel();
				loan.setText("Loan: $" + GamePanel.numForm.format(republic.getLoanAmount()));
				
				maxLoan = new JLabel();
				maxLoan.setText("Max loan: $" + GamePanel.numForm.format(republic.getMaxLoan() * Republic.DEFAULT_GAIN));

				color = new JLabel();
				color.setText("Color: " + republic.getColorText());

				balance = new JLabel();
				balance.setText("Balance: $" + GamePanel.numForm.format(republic.getMoney()));

				this.add(name);
				this.add(loan);
				this.add(color);
				this.add(maxLoan);
				this.add(balance);
				this.add(new JLabel());
				break;
			case CHOICES:

				name = new JLabel();
				name.setText("Name: " + republic.getName());

				loan = new JLabel();
				loan.setText("Loan: $" + GamePanel.numForm.format(republic.getLoanAmount()));

				color = new JLabel();
				color.setText("Color: " + republic.getColorText());

				oldCosts = new JLabel();
				oldCosts.setText("System costs: $" + GamePanel.numForm.format(republic.getSystemCosts()));

				balance = new JLabel();
				balance.setText("Balance: $" + GamePanel.numForm.format(republic.getMoney()));

				this.add(name);
				this.add(loan);
				this.add(color);
				this.add(oldCosts);
				this.add(balance);
				this.add(new JLabel());
				break;
			case UPGRADE:
				name = new JLabel();
				name.setText("Name: " + republic.getName());

				cost = new JLabel();
				cost.setText("Cost: $" + GamePanel.numForm.format(upgradeCost));

				color = new JLabel();
				color.setText("Color: " + republic.getColorText());

				oldCosts = new JLabel();
				oldCosts.setText("System costs: $" + GamePanel.numForm.format(republic.getSystemCosts()));

				balance = new JLabel();
				balance.setText("Balance: $" + GamePanel.numForm.format(republic.getMoney()));

				newCosts = new JLabel();
				newCosts.setText("New costs: $" + GamePanel.numForm.format(upgradeSystemCosts));

				this.add(name);
				this.add(cost);
				this.add(color);
				this.add(oldCosts);
				this.add(balance);
				this.add(newCosts);
				break;
			case SQUARE:
				name = new JLabel();
				name.setText("Name: " + republic.getName());

				color = new JLabel();
				color.setText("Color: " + republic.getColorText());

				balance = new JLabel();
				balance.setText("Balance: $" + GamePanel.numForm.format(republic.getMoney()));

				oldCosts = new JLabel();
				oldCosts.setText("System costs: $" + GamePanel.numForm.format(republic.getSystemCosts()));

				newCosts = new JLabel();
				newCosts.setText("New costs: $" + GamePanel.numForm.format(republic.getSystemCosts(1)));

				this.add(name);
				this.add(color);
				this.add(oldCosts);
				this.add(balance);
				this.add(newCosts);
				break;
			default:
				break;
		}
	}

	public RepublicInfoDisplayMode getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(RepublicInfoDisplayMode displayMode) {
		this.displayMode = displayMode;
	}

	public Republic getRepublic() {
		return republic;
	}

	public void setRepublic(Republic republic) {
		this.republic = republic;
	}

	public void setUpgradeCost(long upgradeCost2) {
		this.upgradeCost = upgradeCost2;
	}
	
	public long getUpgradeCost()
	{
		return upgradeCost;
	}

	public void setUpgradeSystemCosts(int upgradeSystemCosts) {
		this.upgradeSystemCosts = upgradeSystemCosts;
	}
}
