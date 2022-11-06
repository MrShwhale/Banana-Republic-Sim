package bananaPackage;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gamePanelPackage.GamePanel;
import gamePanelPackage.RepubMakerPanel;
/**
 * Displays the start panel
 * Allows people to select the number of rounds and teams
 */
public class StartPanel extends JPanel implements ActionListener {

	//#ADD THE SELECT MAP FILE TEXTFIELD

	private static final long serialVersionUID = 1L;
	private JLabel panelTitle;
	private JLabel requestRounds;
	private JLabel requestTeams;
	private JLabel warnRounds;
	private JLabel warnTeams;
	private JTextField roundsField;
	private JTextField teamsField;
	private JButton closeButton;
	private JButton nextButton;

	public StartPanel()
	{
		super();

		//Set up the first panel
		this.setBounds(0, 0, BananaRepublic.SCREEN_WIDTH, BananaRepublic.SCREEN_HEIGHT);
		this.setLayout(null);
		this.setBackground(GamePanel.backgroundColor);

		//Button to close the game
		closeButton = new JButton("Close Game");
		closeButton.setFont(GamePanel.defaultFont);
		closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		closeButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		closeButton.setSize(150, 60);
		closeButton.setLocation((int)(BananaRepublic.SCREEN_WIDTH*0.93) -
				(int)(closeButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.03));
		closeButton.addActionListener(this);
		closeButton.setActionCommand("close");

		//#BUG - this button... only the top part works. Find out why. Or fix it idc
		//Button to start the game
		nextButton = new JButton();
		nextButton.setFont(GamePanel.titleFont);
		nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		nextButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		nextButton.setSize(600, 120);
		nextButton.setLocation((int)(BananaRepublic.SCREEN_WIDTH*0.5) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.8));
		nextButton.addActionListener(this);
		nextButton.setActionCommand("next");
		nextButton.setText("Start Game");

		//Title of the panel
		panelTitle = new JLabel("Banana Republic");
		panelTitle.setFont(GamePanel.titleFont);
		panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.setVerticalAlignment(SwingConstants.CENTER);
		panelTitle.setSize(700, 100);
		panelTitle.setLocation((int)(BananaRepublic.SCREEN_WIDTH*0.5) -
				(int)(panelTitle.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.1));

		//Label for rounds text field
		requestRounds = new JLabel("Number of rounds");
		requestRounds.setFont(GamePanel.defaultFont);
		requestRounds.setHorizontalAlignment(SwingConstants.CENTER);
		requestRounds.setVerticalAlignment(SwingConstants.CENTER);
		requestRounds.setSize(200, 24);
		requestRounds.setLocation((int)(BananaRepublic.SCREEN_WIDTH*0.42) -
				(int)(requestRounds.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.65));
		requestRounds.setBorder(GamePanel.defaultBorder);

		//Label for teams text field
		requestTeams = new JLabel("Number of teams");
		requestTeams.setFont(GamePanel.defaultFont);
		requestTeams.setHorizontalAlignment(SwingConstants.CENTER);
		requestTeams.setVerticalAlignment(SwingConstants.CENTER);
		requestTeams.setSize(200, 24);
		requestTeams.setLocation((int)(BananaRepublic.SCREEN_WIDTH*0.42) -
				(int)(requestTeams.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.7));
		requestTeams.setBorder(GamePanel.defaultBorder);

		//Warning for the rounds
		warnRounds = new JLabel("Enter a valid number");
		warnRounds.setFont(GamePanel.defaultFont);
		warnRounds.setHorizontalAlignment(SwingConstants.CENTER);
		warnRounds.setVerticalAlignment(SwingConstants.CENTER);
		warnRounds.setSize(200, 24);
		warnRounds.setLocation((int)(BananaRepublic.SCREEN_WIDTH*0.67) -
				(int)(warnRounds.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.65));
		warnRounds.setVisible(false);

		//Warning for the teams
		warnTeams = new JLabel("Enter a valid number (1-10)");
		warnTeams.setFont(GamePanel.defaultFont);
		warnTeams.setHorizontalAlignment(SwingConstants.CENTER);
		warnTeams.setVerticalAlignment(SwingConstants.CENTER);
		warnTeams.setSize(250, 24);
		warnTeams.setLocation((int)(BananaRepublic.SCREEN_WIDTH*0.67) -
				(int)(warnRounds.getWidth()*0.5) + 6, (int)(BananaRepublic.SCREEN_HEIGHT*0.7));
		warnTeams.setVisible(false);

		//Text field for rounds
		roundsField = new JTextField();
		roundsField.setFont(GamePanel.defaultFont);
		roundsField.setSize(150, 24);
		roundsField.setLocation((int)(BananaRepublic.SCREEN_WIDTH*0.55) -
				(int)(roundsField.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.65));

		//Text field for teams
		teamsField = new JTextField();
		teamsField.setFont(GamePanel.defaultFont);
		teamsField.setSize(150, 24);
		teamsField.setLocation((int)(BananaRepublic.SCREEN_WIDTH*0.55) -
				(int)(teamsField.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.7));

		//Add elements to the panel
		this.add(nextButton);
		this.add(closeButton);
		this.add(panelTitle);
		this.add(requestRounds);
		this.add(requestTeams);
		this.add(warnRounds);
		this.add(warnTeams);
		this.add(roundsField);
		this.add(teamsField);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand())
		{
			case "close":
				BananaRepublic.shutFrame();
				break;
			case "next":
				next();
		}
	}

	public void next() {
		if(GamePanel.isIntegral(teamsField.getText()))
		{
			int roundText = Integer.parseInt(teamsField.getText());
			warnTeams.setVisible(!(roundText<11 && roundText>0));
		}
		else
		{
			warnTeams.setVisible(true);
		}
		warnRounds.setVisible(!GamePanel.isIntegral(roundsField.getText()));

		if(!warnTeams.isVisible() && !warnRounds.isVisible())
		{
			Republic.setRounds(Integer.parseInt(roundsField.getText()));
			Republic.setTeams(Integer.parseInt(teamsField.getText()));
			BananaRepublic.addPanel(new RepubMakerPanel());
			BananaRepublic.addPanel(new MapPanel());

			this.setVisible(false);
			BananaRepublic.removePanel(this);
		}
	}

}
