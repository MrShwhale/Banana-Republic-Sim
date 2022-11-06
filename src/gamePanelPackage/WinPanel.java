package gamePanelPackage;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import bananaPackage.BananaRepublic;
import bananaPackage.Republic;

public class WinPanel extends GamePanel {

	private JLabel winnerName;
	private JLabel resources;
	private JLabel stats;

	private static final long serialVersionUID = -1164718159108257435L;

	public WinPanel()
	{
		Republic winner = Republic.getRichestRepublic();

		panelTitle.setText("End of game");

		winnerName = new JLabel();
		winnerName.setText(winner.getName());
		winnerName.setFont(GamePanel.titleFont);
		winnerName.setHorizontalAlignment(SwingConstants.CENTER);
		winnerName.setVerticalAlignment(SwingConstants.CENTER);
		winnerName.setSize(700, 100);
		winnerName.setLocation((int)(relWidth*0.4) -
				(int)(winnerName.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.12));

		resources = new JLabel();
		resources.setFont(infoFont);
		resources.setSize((int)(relWidth*0.35),(int)(BananaRepublic.SCREEN_HEIGHT*0.30));
		resources.setLocation((int)(relWidth*0.12), (int)(BananaRepublic.SCREEN_HEIGHT*0.17));
		resources.setText("<html>" + "Fish: " + numForm.format(winner.getFish()) + "<br>" +
							"Mine: " + numForm.format(winner.getMine()) + "<br>" +
							"Agriculture: " + numForm.format(winner.getAgr()));

		stats = new JLabel();
		stats.setFont(infoFont);
		stats.setSize((int)(relWidth*0.35),(int)(BananaRepublic.SCREEN_HEIGHT*0.30));
		stats.setLocation((int)(relWidth*0.49), (int)(BananaRepublic.SCREEN_HEIGHT*0.17));
		stats.setText("<html>" + "Color: " + winner.getColorText() + "<br>" +
							"Balance: $" + numForm.format(winner.getMoney()) + "<br>" +
							"Squares: " + numForm.format(winner.getNumSquares()));

		this.add(winnerName);
		this.add(resources);
		this.add(stats);
	}

	@Override
	protected void next() {}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand())
		{
			case "next":
			case "close":
				shutFrame();
				break;
		}
	}
}
