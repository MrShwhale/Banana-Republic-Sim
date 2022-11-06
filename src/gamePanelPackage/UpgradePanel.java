package gamePanelPackage;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import bananaPackage.BananaRepublic;
import bananaPackage.Development;
import bananaPackage.Republic;

public class UpgradePanel extends ChoicePanel {

	private static final long serialVersionUID = -4886238407819964008L;

	public static final Border upgradedBorder = BorderFactory.createLineBorder(Color.GREEN, 2);
	public static final Border tooHighBorder = BorderFactory.createLineBorder(Color.RED, 2);
	public static final Border selectedBorder = BorderFactory.createLineBorder(Color.BLUE, 2);

	private JPanel holderPanel;

	private JButton[][] upgradeButtons;

	private int selectedUpgrade;

	public UpgradePanel()
	{
		super();
		
		upgradeButtons = new JButton[4][4];

		panelTitle.setText("Upgrade");

		//holderPanel design based off of table from "Banana republic simulation rules" doc
		holderPanel = new JPanel();
		holderPanel.setLocation((int)(relWidth*0.15), (int)(BananaRepublic.SCREEN_HEIGHT*0.25));
		holderPanel.setSize((int)(relWidth*0.70), (int)(BananaRepublic.SCREEN_HEIGHT*0.50));
		holderPanel.setLayout(new GridLayout(5,5));
		holderPanel.setBorder(defaultBorder);

		//Add headers
		holderPanel.add(new JLabel());
		holderPanel.add(new JLabel("UNIMPROVED"));
		holderPanel.add(new JLabel("BASIC"));
		holderPanel.add(new JLabel("ADVANCED"));
		holderPanel.add(new JLabel("HI-TECH"));

		//Use this to keep track of which upgrade is selected.
		//0 = none, 1 = mil, 2 = edu, 3 = leg, 4 = inf
		selectedUpgrade = 0;
		
		//Main button section
		//If a an upgrade is owned, it appears with a green border and is disabled
		//If an upgrade is more than 1 level above the republics development, it is red bordered and disabled
		//Everything else is clickable and black border

		//Add military stats
		holderPanel.add(new JLabel("Military"));

		for(int i = 0; i < upgradeButtons[0].length; i++)
		{
			upgradeButtons[0][i] = new JButton();
			upgradeButtons[0][i].setAlignmentX(CENTER_ALIGNMENT);
			upgradeButtons[0][i].setText("<html>Level " + (i+1) + "<br>" + "$" + numForm.format(Development.getDevelopmentFromLevel(i+1).getUpgradeCost()*Republic.DEFAULT_GAIN));
			upgradeButtons[0][i].addActionListener(this);
			upgradeButtons[0][i].setActionCommand("mil" + i);

			//An upgrade is owned if the level of it is less than or equal to the level of the republic stat
			if((i+1) <= currentRepub.getMilitaryLevel().getIntLevel())
			{
				upgradeButtons[0][i].setEnabled(false);
				upgradeButtons[0][i].setBorder(upgradedBorder);
			}
			//An upgrade is too high if the level of it is >1 more than the republic development
			else if((i+1) > currentRepub.getDevelopment().getIntLevel() + 1)
			{
				upgradeButtons[0][i].setEnabled(false);
				upgradeButtons[0][i].setBorder(tooHighBorder);
			}
			else
			{
				upgradeButtons[0][i].setEnabled(true);
				upgradeButtons[0][i].setBorder(defaultBorder);
			}


			holderPanel.add(upgradeButtons[0][i]);
		}

		//Add education stats
		holderPanel.add(new JLabel("Education"));

		for(int i = 0; i < upgradeButtons[1].length; i++)
		{
			upgradeButtons[1][i] = new JButton();
			upgradeButtons[1][i].setAlignmentX(CENTER_ALIGNMENT);
			upgradeButtons[1][i].setText("<html>Productivity<br>" + Development.getDevelopmentFromLevel(i+1).getProductivity()
					+ "<br>" + "$" + numForm.format(Development.getDevelopmentFromLevel(i+1).getUpgradeCost()*Republic.DEFAULT_GAIN));
			upgradeButtons[1][i].addActionListener(this);
			upgradeButtons[1][i].setActionCommand("edu" + i);

			//An upgrade is owned if the level of it is less than or equal to the level of the republic stat
			if((i+1) <= currentRepub.getEducationLevel().getIntLevel())
			{
				upgradeButtons[1][i].setEnabled(false);
				upgradeButtons[1][i].setBorder(upgradedBorder);
			}
			//An upgrade is too high if the level of it is >1 more than the republic development
			else if((i+1) > currentRepub.getDevelopment().getIntLevel() + 1)
			{
				upgradeButtons[1][i].setEnabled(false);
				upgradeButtons[1][i].setBorder(tooHighBorder);
			}
			else
			{
				upgradeButtons[1][i].setBorder(defaultBorder);
			}

			holderPanel.add(upgradeButtons[1][i]);
		}

		//Add infrastructure stats
		holderPanel.add(new JLabel("<html>Physical<br>Infrastructure"));

		for(int i = 0; i < upgradeButtons[3].length; i++)
		{
			upgradeButtons[3][i] = new JButton();
			upgradeButtons[3][i].setAlignmentX(CENTER_ALIGNMENT);
			upgradeButtons[3][i].setText("<html>Productivity<br>" + Development.getDevelopmentFromLevel(i+1).getProductivity()
					+ "<br>" + "$" + numForm.format(Development.getDevelopmentFromLevel(i+1).getUpgradeCost()*Republic.DEFAULT_GAIN));
			upgradeButtons[3][i].addActionListener(this);
			upgradeButtons[3][i].setActionCommand("inf" + i);

			//An upgrade is owned if the level of it is less than or equal to the level of the republic stat
			if((i+1) <= currentRepub.getInfrastructureLevel().getIntLevel())
			{
				upgradeButtons[3][i].setEnabled(false);
				upgradeButtons[3][i].setBorder(upgradedBorder);
			}
			//An upgrade is too high if the level of it is >1 more than the republic development
			else if((i+1) > currentRepub.getDevelopment().getIntLevel() + 1)
			{
				upgradeButtons[3][i].setEnabled(false);
				upgradeButtons[3][i].setBorder(tooHighBorder);
			}
			else
			{
				upgradeButtons[3][i].setBorder(defaultBorder);
			}

			holderPanel.add(upgradeButtons[3][i]);
		}

		//Add legal stats
		holderPanel.add(new JLabel("Legal"));

		for(int i = 0; i < upgradeButtons[2].length; i++)
		{
			upgradeButtons[2][i] = new JButton();
			upgradeButtons[2][i].setAlignmentX(CENTER_ALIGNMENT);
			upgradeButtons[2][i].setText("<html>Productivity<br>" + Development.getDevelopmentFromLevel(i+1).getProductivity()
					+ "<br>" + "$" + numForm.format(Development.getDevelopmentFromLevel(i+1).getUpgradeCost()*Republic.DEFAULT_GAIN));
			upgradeButtons[2][i].addActionListener(this);
			upgradeButtons[2][i].setActionCommand("leg" + i);

			//An upgrade is owned if the level of it is less than or equal to the level of the republic stat
			if((i+1) <= currentRepub.getLegalLevel().getIntLevel())
			{
				upgradeButtons[2][i].setEnabled(false);
				upgradeButtons[2][i].setBorder(upgradedBorder);
			}
			//An upgrade is too high if the level of it is >1 more than the republic development
			else if((i+1) > currentRepub.getDevelopment().getIntLevel() + 1)
			{
				upgradeButtons[2][i].setEnabled(false);
				upgradeButtons[2][i].setBorder(tooHighBorder);
			}
			else
			{
				upgradeButtons[2][i].setBorder(defaultBorder);
			}

			holderPanel.add(upgradeButtons[2][i]);
		}

		nextButton.setText("Purchase");
		nextButton.setSize(400, 100);
		nextButton.setLocation((int)(relWidth*0.6) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.8));
		nextButton.setEnabled(true);

		repubInfoPanel = new RepublicInfoPanel(RepublicInfoDisplayMode.UPGRADE, this);

		this.add(holderPanel);
		this.add(repubInfoPanel);
	}
	
	/**
	 * Resets the border of the currently selected space to the default border
	 * Does not change selectedUpgrade
	 * 
	 * @param buttonLevel the level to redo borders at
	 */
	private void unselectCurrent(int buttonLevel)
	{
		upgradeButtons[selectedUpgrade-1][buttonLevel].setBorder(defaultBorder);
	}
	
	
	@Override
	protected void next() {
		
		//Whichever upgrade was selected, increase that level 
		currentRepub.setUpgradeBought(true);
		
		switch (selectedUpgrade)
		{
			case 0:
				currentRepub.setUpgradeBought(false);
				break;
			case 1:
				currentRepub.setMilitary(currentRepub.getMilitaryLevel().nextLevel());
				break;
			case 2:
				currentRepub.setEducation(currentRepub.getEducationLevel().nextLevel());
				break;
			case 3:
				currentRepub.setLegal(currentRepub.getLegalLevel().nextLevel());
				break;
			case 4:
				currentRepub.setInfrastructure(currentRepub.getInfrastructureLevel().nextLevel());
		}
		
		currentRepub.addMoney(-repubInfoPanel.getUpgradeCost());	
		
		back();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		switch(actionCommand)
		{
			case "close":
				shutFrame();
				return;
			case "next":
				next();
				return;
			case "back":
				back();
				return;
		}
		
		int buttonLevel = actionCommand.charAt(3)-48;
		
		try {unselectCurrent(buttonLevel);} catch (Exception exec) {}
		
		selectedUpgrade = 0;
		
		switch(actionCommand.substring(0,3))
		{
			case "mil":
				//If this was not previously selected
				if(selectedUpgrade != 1)
				{
					selectedUpgrade = 1;
					upgradeButtons[0][buttonLevel].setBorder(selectedBorder);
				}
				break;
			case "edu":
				if(selectedUpgrade != 2)
				{
					selectedUpgrade = 2;
					upgradeButtons[1][buttonLevel].setBorder(selectedBorder);
				}
				break;
			case "leg":
				if(selectedUpgrade != 3)
				{
					selectedUpgrade = 3;
					upgradeButtons[2][buttonLevel].setBorder(selectedBorder);
				}
				break;
			case "inf":
				if(selectedUpgrade != 4)
				{
					selectedUpgrade = 4;
					upgradeButtons[3][buttonLevel].setBorder(selectedBorder);
				}
				break;
		}
		//After that, always redraw the repubInfoPanel after updating upgradeCost and upgradeSystemCosts

		long upgradeCost = 0;
		
		//Always pay for upgrades as if no squares have been bought that round
		long upgradeBase = currentRepub.getNumSquares() - (currentRepub.isSquareBought() ? 1 : 0) * Republic.DEFAULT_GAIN;
		
		Development selectedMilitary = currentRepub.getMilitaryLevel();
		Development selectedEducation = currentRepub.getEducationLevel();
		Development selectedLegal = currentRepub.getLegalLevel();
		Development selectedInfrastructure = currentRepub.getInfrastructureLevel();
		
		switch(selectedUpgrade)
		{
			case 1:
				selectedMilitary = selectedMilitary.nextLevel();
				upgradeCost = selectedMilitary.getUpgradeCost() * upgradeBase;
				break;
			case 2:
				selectedEducation = selectedEducation.nextLevel();
				upgradeCost = selectedEducation.getUpgradeCost() * upgradeBase;
				break;
			case 3:
				selectedLegal = selectedLegal.nextLevel();
				upgradeCost = selectedLegal.getUpgradeCost() * upgradeBase;
				break;
			case 4:
				selectedInfrastructure = selectedInfrastructure.nextLevel();
				upgradeCost = selectedInfrastructure.getUpgradeCost() * upgradeBase;
				break;
		}

		repubInfoPanel.setUpgradeCost(upgradeCost);

		repubInfoPanel.setUpgradeSystemCosts(currentRepub.getSystemCosts(selectedMilitary, selectedLegal, selectedEducation, selectedInfrastructure));

		redrawRepubInfo();

		//Then, disable the exit button if it costs too much
		if(upgradeCost > currentRepub.getMoney() && upgradeCost > 0)
		{
			nextButton.setText("Too much");
			nextButton.setEnabled(false);
		}
		else
		{
			nextButton.setText("Purchase");
			nextButton.setEnabled(true);
		}
	}

}