package view;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.StinkyGameGUI;

public class NewGame extends JLabel{
	private Main mainView;
	public JButton downloading;
	public JButton beginner, intermediate, advanced,startBtn, htp;
	private StinkyGameGUI listener;
	public JLabel info;
	
	public NewGame(StinkyGameGUI lis, JFrame jf) {
		setMainView((Main)jf);
		listener = lis;
		setSize(jf.getWidth(),jf.getHeight());
		
		Image backgroundImage= Toolkit.getDefaultToolkit().getImage("images/StinkyGame.png").getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH);
		ImageIcon backgroundImageIcon= new ImageIcon(backgroundImage); 
		
		//getScaledInstance --> resizes the image
		ImageIcon startGame= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/play.png").getScaledInstance((int)(jf.getWidth()*0.10), (int)(jf.getHeight()*0.20), java.awt.Image.SCALE_SMOOTH));
		startBtn = new JButton(startGame);
		//adding button to the jlabel
		add(startBtn);
		//Hides the white background of the Jbutton
		startBtn.setContentAreaFilled(false);
		//Hides the silver clickable frame
		startBtn.setBorderPainted(false);
		
		startBtn.setVisible(true);
		//X_position, Y_position, Width, Height
		startBtn.setBounds((int)(jf.getWidth()*0.46), (int)(jf.getHeight()*0.21), (int)(jf.getWidth()*0.1), (int)(jf.getHeight()*0.20));
		startBtn.addActionListener(listener);
		JLabel background = new JLabel(backgroundImageIcon);
		
		ImageIcon beg = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/beginner.jpg").getScaledInstance((int) (getHeight() * 0.2),(int) (getHeight() * 0.07), java.awt.Image.SCALE_SMOOTH));
		beginner = new JButton(beg);
		beginner.addActionListener(listener);
		beginner.setBorderPainted(false);
		beginner.setBounds((int)(getWidth() * 0.01), (int)(getHeight() * 0.34) , (int) (getHeight() * 0.2),(int) (getHeight() * 0.07));
		add(beginner);
		beginner.setVisible(true);
		
		ImageIcon med = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/intermediate.jpg").getScaledInstance((int) (getHeight() * 0.2),(int) (getHeight() * 0.07), java.awt.Image.SCALE_SMOOTH));
		intermediate = new JButton(med);
		intermediate.addActionListener(listener);
		intermediate.setBorderPainted(false);
		intermediate.setBounds((int)(getWidth() * 0.01), (int)(getHeight() * 0.43) , (int) (getHeight() * 0.2),(int) (getHeight() * 0.07));
		add(intermediate);
		intermediate.setVisible(true);
		
		ImageIcon ad = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/advanced.jpg").getScaledInstance((int) (getHeight() * 0.2),(int) (getHeight() * 0.07), java.awt.Image.SCALE_SMOOTH));
		advanced = new JButton(ad);
		advanced.addActionListener(listener);
		advanced.setBorderPainted(false);
		advanced.setBounds((int)(getWidth() * 0.01), (int)(getHeight() * 0.52) , (int) (getHeight() * 0.2),(int) (getHeight() * 0.07));
		add(advanced);
		advanced.setVisible(true);
		
		ImageIcon how = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/htp.png").getScaledInstance((int) (getHeight() * 0.2),(int) (getHeight() * 0.05), java.awt.Image.SCALE_SMOOTH));
		htp = new JButton(how);
		htp.addActionListener(listener);
		htp.setBorderPainted(false);
		htp.setBounds((int)(getWidth() * 0.0061), (int)(getHeight() * 0.66) , (int) (getHeight() * 0.2),(int) (getHeight() * 0.05));
		htp.setContentAreaFilled(false);
		add(htp);
		htp.setVisible(true);
		
		info = new JLabel("Choose a difficulty");
		info.setVisible(true);
		info.setBounds((int)(getWidth() * 0.01), (int)(getHeight() * 0.27) , (int) (getHeight() * 0.37),(int) (getHeight() * 0.05));
		info.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 30));
		add(info);
		
//		ImageIcon download = new ImageIcon("images/loading.gif");
//		downloading = new JButton(download);
//		downloading.setContentAreaFilled(false);
//		downloading.setBorderPainted(false);
//		downloading.setBounds((int)(jf.getWidth()*0.46), (int)(jf.getHeight() * 0.48), (int)(jf.getHeight()* 0.145), (int)(jf.getHeight()* 0.145));
//		add(downloading);
		
		add(mainView.mute);
		
		add(background);
		background.setBounds(0,0,getWidth(),getHeight());
		
	}
	
	public Main getMainView() {
		return mainView;
	}
	public void setMainView(Main mainView) {
		this.mainView = mainView;
	}

}
