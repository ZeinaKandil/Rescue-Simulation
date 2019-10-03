package view;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import controller.StinkyGameGUI;

public class hoToPlay extends JLabel{
public StinkyGameGUI listener;
public Main jf;
public JButton back;

	public hoToPlay(StinkyGameGUI lis, Main jf) {
		listener = lis;
		this.jf = jf;
		setSize(jf.getWidth(), jf.getHeight());
		ImageIcon b = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/goBack.png").getScaledInstance((int) (getHeight() * 0.3),(int) (getHeight() * 0.12), java.awt.Image.SCALE_SMOOTH));
		back = new JButton(b);
		back.addActionListener(listener);
		back.setContentAreaFilled(false);
		back.setBorderPainted(false);
		add(back);
		back.setBounds(0, 0, (int) (getHeight() * 0.3),(int) (getHeight() * 0.12));
		
		add(jf.mute);
		
		Image backgroundImage= Toolkit.getDefaultToolkit().getImage("images/htpScreen.jpg").getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH);
		ImageIcon backgroundImageIcon= new ImageIcon(backgroundImage); 
		JLabel background = new JLabel(backgroundImageIcon);
		add(background);
		background.setBounds(0,0,getWidth(),getHeight());
		
	}

}
