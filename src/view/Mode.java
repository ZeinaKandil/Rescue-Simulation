package view;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import controller.StinkyGameGUI;

public class Mode extends JLabel{
	public JButton beginner, intermediate, advanced,start;
	private StinkyGameGUI listener;
	private Main jf;
	public JLabel info;

	public Mode(StinkyGameGUI listener, Main jf) {
		this.jf = jf;
		this.listener = listener;
		setSize(jf.getWidth(),jf.getHeight());
		
		ImageIcon beg = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/beginner.jpg").getScaledInstance((int) (getHeight() * 0.36),(int) (getHeight() * 0.16), java.awt.Image.SCALE_SMOOTH));
		beginner = new JButton(beg);
		beginner.addActionListener(listener);
		beginner.setBorderPainted(false);
		beginner.setBounds((int)(getWidth() * 0.22), (int)(getHeight() * 0.2) , (int) (getHeight() * 0.36),(int) (getHeight() * 0.16));
		add(beginner);
		beginner.setVisible(true);
		
		ImageIcon med = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/intermediate.jpg").getScaledInstance((int) (getHeight() * 0.36),(int) (getHeight() * 0.16), java.awt.Image.SCALE_SMOOTH));
		intermediate = new JButton(med);
		intermediate.addActionListener(listener);
		intermediate.setBorderPainted(false);
		intermediate.setBounds((int)(getWidth() * 0.22), (int)(getHeight() * 0.38) , (int) (getHeight() * 0.36),(int) (getHeight() * 0.16));
		add(intermediate);
		intermediate.setVisible(true);
		
		ImageIcon ad = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/advanced.jpg").getScaledInstance((int) (getHeight() * 0.36),(int) (getHeight() * 0.16), java.awt.Image.SCALE_SMOOTH));
		advanced = new JButton(ad);
		advanced.addActionListener(listener);
		advanced.setBorderPainted(false);
		advanced.setBounds((int)(getWidth() * 0.22), (int)(getHeight() * 0.56) , (int) (getHeight() * 0.36),(int) (getHeight() * 0.16));
		add(advanced);
		advanced.setVisible(true);
		
		start = new JButton("start");
		start.addActionListener(listener);
		start.setBounds((int)(getWidth() * 0.7), (int)(getHeight() * 0.7) , (int) (getHeight() * 0.1),(int) (getHeight() * 0.16));
		start.setVisible(true);
		add(start);
		
		info = new JLabel("Select a difficulty");
		info.setBounds((int)(getWidth() * 0.65), (int)(getHeight() * 0.7) , (int) (getHeight() * 0.3),(int) (getHeight() * 0.16));
		info.setVisible(true);
		add(info);
		
		jf.mute.setContentAreaFilled(true);
		add(jf.mute);
		
	}

}
