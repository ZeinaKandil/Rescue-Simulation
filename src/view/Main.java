package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import controller.StinkyGameGUI;


public class Main extends JFrame {
	private StinkyGameGUI listener;
	private JLabel jl;
	private JTextArea executedDisasters;
	public Clip clip;
	public JButton mute;
	public boolean muted;
	public level mode;
	public Main(StinkyGameGUI listener) throws UnsupportedAudioFileException, LineUnavailableException {
		this.listener = listener;
		/*
		 * The pack method sizes the frame so that all its contents are at or above their preferred sizes. 
		 * An alternative to pack is to establish a frame size explicitly by calling setSize or setBounds 
		 * (which also sets the frame location)
		 */
		pack();
		Dimension dim = getMaximumSize();
		setSize(dim);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we)
		    { 
		        String choices[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Stinky Game",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,choices,choices[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
		        }
		    }
		});
		
		muted = false;
		ImageIcon sound = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/unmuted.png").getScaledInstance((int) (getHeight() * 0.05),(int) (getHeight() * 0.05), java.awt.Image.SCALE_SMOOTH));
		mute = new JButton(sound);
		mute.setBounds((int) (getWidth() *0.875), (int)(getHeight() * 0.02), (int)(getHeight() * 0.05), (int)(getHeight() * 0.05));
		mute.addActionListener(listener);
		mute.setContentAreaFilled(false);
		mute.setBorderPainted(false);
		
		//Title of the game
		setTitle("Stinky Game");
		//Icon of the game
		Image icon = Toolkit.getDefaultToolkit().getImage("images\\spiderman.png");
		setIconImage(icon);
		SimpleAudioPlayer();
		validate();
		//repaint();
	}
	public void SimpleAudioPlayer() throws UnsupportedAudioFileException, LineUnavailableException  {
			try{
	        // create AudioInputStream object 
			String filePath = "music1.wav"; 
			AudioInputStream  audioInputStream =  
	                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
	          
	        // create clip reference 
	        clip = AudioSystem.getClip(); 
	          
	        // open audioInputStream to the clip 
	        clip.open(audioInputStream); 
	          
	        clip.loop(Clip.LOOP_CONTINUOUSLY); 
	        clip.start();
			}catch(IOException e){
				System.out.println(e.getMessage()+"mariem");
			}
	    }
	
	public void muteSound(){
		clip.stop();
		//clip.close();
	}
	
	public void unmuteSound(){
		clip.start();
	}
		
	public JLabel getJl(){
		return jl;
	}
	
	public void newGame(){
		NewGame newGame = new NewGame(listener, this);
		//newGame.startBtn.addActionListener(listener);
		getContentPane().removeAll();//or remove(JComponent)
		getContentPane().revalidate();
		getContentPane().repaint();
		jl = newGame;
		add(jl);
		setVisible(true);
	}
	
	public void map(){
		getContentPane().removeAll();//or remove(JComponent)
		getContentPane().revalidate();
		getContentPane().repaint();
		jl= new Map2(listener, this);
		add(jl);
		setVisible(true);
	}

	public void htp(){
		getContentPane().removeAll();//or remove(JComponent)
		getContentPane().revalidate();
		getContentPane().repaint();
		jl= new hoToPlay(listener, this);
		add(jl);
		setVisible(true);
	}
	
	public void GameOver(){
		getContentPane().removeAll();//or remove(JComponent)
		getContentPane().revalidate();
		getContentPane().repaint();
		jl= new Score(listener, this);
		add(jl);
		setVisible(true);
	}
	
	public JTextArea getExecutedDisasters() {
		return executedDisasters;
	}
}
