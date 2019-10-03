package view;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import controller.StinkyGameGUI;

public class Score extends JLabel{
	private JLabel scoreLabel;
	private StinkyGameGUI listener;
	private Main jf;
	public JButton playagain;

	public Score(StinkyGameGUI lis, Main m) {
		jf = m;
		listener = lis;
		setSize(jf.getWidth(),jf.getHeight());
		
		Image backgroundImage= Toolkit.getDefaultToolkit().getImage("images/GameOver.png").getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH);
		ImageIcon backgroundImageIcon= new ImageIcon(backgroundImage); 
		
		ImageIcon playAgain = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/replay.png").getScaledInstance((int) (getWidth() * 0.13),(int) (getHeight() * 0.07), java.awt.Image.SCALE_SMOOTH));
		
		playagain= new JButton(playAgain);
		playagain.setBounds((int)(jf.getWidth()*0.433), (int)(jf.getHeight()*0.07), (int)(jf.getWidth()*0.13), (int)(jf.getHeight()*0.07));
		playagain.setVisible(true);
		playagain.setContentAreaFilled(false);
		playagain.setBorderPainted(false);
		playagain.addActionListener(listener);
		this.add(playagain);
		
		scoreLabel = new JLabel("Score:" + listener.getCc().getEngine().calculateCasualties());
		scoreLabel.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 110));
		scoreLabel.setBounds((int)(jf.getWidth()*0.38), (int)(jf.getHeight()*0.12),(int)(jf.getWidth()*0.4), (int)(jf.getHeight()*0.4));
		scoreLabel.setVisible(true);
		add(scoreLabel);
		
		jf.mute.setContentAreaFilled(true);
		add(jf.mute);
		
		JLabel background = new JLabel(backgroundImageIcon);
		add(background);
		background.setBounds(0,0,getWidth(),getHeight());
		
	}

}
