package gamePanelPackage;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import bananaPackage.BananaRepublic;

public abstract class ChoicePanel extends GamePanel {

	private static final long serialVersionUID = 7518137275079456963L;

	protected JButton backButton;
	
	public ChoicePanel()
	{
		super();
		
		nextButton.setSize(400, 100);
		nextButton.setLocation((int)(relWidth*0.6) -
				(int)(nextButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.8));
		
		backButton = new JButton("Back");
		backButton.setSize(nextButton.getWidth(), nextButton.getHeight() / 2);
		backButton.setLocation((int)(relWidth*0.6) -
				(int)(backButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.66));
		backButton.setFont(defaultFont);
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		backButton.setSize(150, 60);
		backButton.setLocation((int)(relWidth*0.89) -
				(int)(backButton.getWidth()*0.5), (int)(BananaRepublic.SCREEN_HEIGHT*0.13));
		backButton.addActionListener(this);
		backButton.setActionCommand("back");
		
		this.add(backButton);
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);
	
	@Override
	protected abstract void next();

	public void back()
	{
		BananaRepublic.addPanel(new MakeChoicePanel());
		shutPanel();
	}
}
