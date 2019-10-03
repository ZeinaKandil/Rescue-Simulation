package controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.people.Citizen;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulatable;
import sun.tools.jar.resources.jar;
import view.Main;
import view.Map2;
import view.Mode;
import view.NewGame;
import view.Score;
import view.hoToPlay;
import view.level;
import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;

public class StinkyGameGUI implements ActionListener {
	private CommandCenter cc;
	Main mainView;
	private Unit lastUnit;
	private Rescuable lastRescuable;
	private int count;
	private int countunit;
	private int countres;
	private ImageIcon sound, nosound, effects, noEffects, advanced, advancedS, beg, begS, med, medS;
	private Clip  curClip;
	public boolean effectsOn;

	public StinkyGameGUI() throws Exception {
		cc = new CommandCenter();
		mainView = new Main(this);
		mainView.newGame();
		
		lastUnit= null;
		lastRescuable = null;
		count=0;
		countres=0;
		countunit=0;
		sound = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/unmuted.png").getScaledInstance((int) (mainView.getHeight() * 0.05),(int) (mainView.getHeight() * 0.05), java.awt.Image.SCALE_SMOOTH));
		nosound = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/muted.png").getScaledInstance((int) (mainView.getHeight() * 0.05),(int) (mainView.getHeight() * 0.05), java.awt.Image.SCALE_SMOOTH));
		effectsOn = true;
		effects = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/soundEffects.jpg").getScaledInstance((int) (mainView.getHeight() * 0.05),(int) (mainView.getHeight() * 0.05), java.awt.Image.SCALE_SMOOTH));
		noEffects = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/nosoundEffects.jpg").getScaledInstance((int) (mainView.getHeight() * 0.05),(int) (mainView.getHeight() * 0.05), java.awt.Image.SCALE_SMOOTH));
		
		beg = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/beginner.jpg").getScaledInstance((int) (mainView.getHeight() * 0.2),(int) (mainView.getHeight() * 0.07), java.awt.Image.SCALE_SMOOTH));
		begS = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/beginnerSjpg.jpg").getScaledInstance((int) (mainView.getHeight() * 0.2),(int) (mainView.getHeight() * 0.07), java.awt.Image.SCALE_SMOOTH));
		med = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/intermediate.jpg").getScaledInstance((int) (mainView.getHeight() * 0.2),(int) (mainView.getHeight() * 0.07), java.awt.Image.SCALE_SMOOTH));
		medS = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/intermediateS.jpg").getScaledInstance((int) (mainView.getHeight() * 0.2),(int) (mainView.getHeight() * 0.07), java.awt.Image.SCALE_SMOOTH));
		advanced = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/advanced.jpg").getScaledInstance((int) (mainView.getHeight() * 0.2),(int) (mainView.getHeight() * 0.07), java.awt.Image.SCALE_SMOOTH));
		advancedS = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/advancedS.jpg").getScaledInstance((int) (mainView.getHeight() * 0.2),(int) (mainView.getHeight() * 0.07), java.awt.Image.SCALE_SMOOTH));

	}
	
	public void actionPerformed(ActionEvent e) {
		if(mainView.getJl() instanceof hoToPlay){
			hoToPlay hToP = ((hoToPlay)mainView.getJl());
			if((JButton) e.getSource() == hToP.back){
				mainView.newGame();
			}
		}
		
		if((JButton) e.getSource() == mainView.mute && !mainView.muted){
			mainView.muteSound();
			mainView.muted = true;
			mainView.mute.setIcon(nosound);
			mainView.mute.setContentAreaFilled(false);
			return;
		}
		else if((JButton) e.getSource() == mainView.mute && mainView.muted){
			mainView.unmuteSound();
			mainView.muted = false;
			mainView.mute.setIcon(sound);
			mainView.mute.setContentAreaFilled(false);
			return;
		}
		if (mainView.getJl() instanceof Score) {
			if ((JButton) e.getSource()== ((Score)mainView.getJl()).playagain) {
				try {
					cc=new CommandCenter();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				mainView.map();
			}
		}
		if (mainView.getJl() instanceof NewGame) {
			NewGame ng = ((NewGame) mainView.getJl());
			if((JButton)e.getSource() == ng.htp){
				mainView.htp();
			}
			if((JButton)e.getSource() == ng.startBtn){
				if(mainView.mode == null)
					JOptionPane.showMessageDialog(null,
							"Please Choose a Difficulty Level", "",
							JOptionPane.ERROR_MESSAGE);
				else
					mainView.map();
			}
			else if((JButton)e.getSource() == ng.beginner){
				ng.beginner.setIcon(begS);
				ng.intermediate.setIcon(med);
				ng.advanced.setIcon(advanced);
				mainView.mode = level.EASY;
				ng.info.setText("Unlimited Hints!");
			}
			else if((JButton)e.getSource() == ng.intermediate){
				ng.beginner.setIcon(beg);
				ng.intermediate.setIcon(medS);
				ng.advanced.setIcon(advanced);
				mainView.mode = level.MEDIUM;
				ng.info.setText("Just 1 Hint per cycle.");
			}
			else if((JButton)e.getSource() == ng.advanced){
				ng.beginner.setIcon(beg);
				ng.intermediate.setIcon(med);
				ng.advanced.setIcon(advancedS);
				mainView.mode = level.HARD;
				ng.info.setText("Only 1 hint");
			}
		}
		else if(mainView.getJl() instanceof Map2){
			count++;
			Map2 map = ((Map2) mainView.getJl());
			if((JButton)e.getSource() == map.hint){
				map.displayHint();
				return;
			}
			if((JButton) e.getSource() == map.effects && effectsOn){
				effectsOn = false;
				map.effects.setIcon(noEffects);
				if(curClip != null && curClip.isActive()){
					curClip.stop();
					curClip.close();
				}
				return;
			}
			else if((JButton) e.getSource() == map.effects && !effectsOn){
				effectsOn = true;
				map.effects.setIcon(effects);
				return;
			}
			for (int i = 0; i < map.getUnitInPanel().size(); i++) {
					if((JButton)e.getSource() == map.getUnitInPanel().get(i)){
						map.displayUnit(getCc().getEmergencyUnits().get(i));
						lastUnit=getCc().getEmergencyUnits().get(i);
						countunit=count;
					}
			}
				if((JButton)e.getSource() == map.nextComponentsPage){
					int curPage = map.componentsPage;
					int nxtPage = curPage + 1;
					if(nxtPage < map.ComponentsPages.size()) {
						JLabel curLabel = map.ComponentsPages.get(curPage);
						JLabel nxtLabel = map.ComponentsPages.get(nxtPage);
						curLabel.setVisible(false);
						nxtLabel.setVisible(true);
						map.componentsPage += 1;
						mainView.getContentPane().revalidate();
						mainView.getContentPane().repaint();
						mainView.setVisible(true);
						return;
					}
					return;
				}
				if((JButton)e.getSource() == map.prevComponentsPage){
					int curPage = map.componentsPage;
					int prevPage = curPage - 1;
					if(prevPage >= 0) {
						JLabel curLabel = map.ComponentsPages.get(curPage);
						JLabel prevLabel = map.ComponentsPages.get(prevPage);
						curLabel.setVisible(false);
						prevLabel.setVisible(true);
						map.componentsPage -= 1;
						mainView.getContentPane().revalidate();
						mainView.getContentPane().repaint();
						mainView.setVisible(true);
					}
					return;
				}
				if((JButton)e.getSource() == map.nextSecComponentsPage){
					int curSecPage = map.getSecondaryComponentsPage();
					int nxtSecPage = curSecPage + 1;
					if(nxtSecPage < map.getSecondaryComponentsPages().size()) {
						JLabel curLabel = map.getSecondaryComponentsPages().get(curSecPage);
						JLabel nxtLabel = map.getSecondaryComponentsPages().get(nxtSecPage);
						curLabel.setVisible(false);
						nxtLabel.setVisible(true);
						map.secondaryComponentsPage += 1;
						mainView.getContentPane().revalidate();
						mainView.getContentPane().repaint();
						mainView.setVisible(true);
					}
					return;
				}
				if((JButton)e.getSource() == map.prevSecComponentsPage){
					int curPage = map.getSecondaryComponentsPage();
					int prevPage = curPage - 1;
					if(prevPage >= 0) {
						JLabel curLabel = map.getSecondaryComponentsPages().get(curPage);
						JLabel prevLabel = map.getSecondaryComponentsPages().get(prevPage);
						curLabel.setVisible(false);
						prevLabel.setVisible(true);
						map.secondaryComponentsPage -= 1;
						mainView.getContentPane().revalidate();
						mainView.getContentPane().repaint();
						mainView.setVisible(true);
					}
					return;
				}
				if ((JButton)e.getSource()==map.getNextcyclebutton()) {
					map.nxtcycle();
					return;
				}
				if((JButton)e.getSource()== map.getNextUnitsPage()){
					if(map.getPage()+1 < map.getUnitsPages().size()){
						int curPageIdx = map.getPage();
						JLabel curPage = map.getUnitsPages().get(curPageIdx);  
						curPage.setVisible(false);
//						map.remove(curPage);
						map.setPage(map.getPage() + 1);
						JLabel newPage = map.getUnitsPages().get(curPageIdx + 1);
						newPage.setVisible(true);
//						map.add(newPage);
						mainView.getContentPane().revalidate();
						mainView.getContentPane().repaint();
						mainView.setVisible(true);
					}
					return;
				}
				if((JButton)e.getSource()== map.getPrevUnitsPage()){
					if(map.getPage()-1 >= 0){
						int curPageIdx = map.getPage();
						JLabel curPage = map.getUnitsPages().get(curPageIdx); 
						curPage.setVisible(false);
						map.setPage(map.getPage() - 1);
						JLabel newPage = map.getUnitsPages().get(curPageIdx - 1);
						newPage.setVisible(true);
						mainView.getContentPane().revalidate();
						mainView.getContentPane().repaint();
						mainView.setVisible(true);
					}
					return;
				}
				for (int i = 0; i < map.getsAndB().size(); i++) {
					if(map.getsAndB().get(i).getB() == (JButton)e.getSource()){
						Simulatable s = map.getsAndB().get(i).getS();
						if( s instanceof Unit){
							map.displaySecondaryUnit((Unit)(s));
							lastUnit = (Unit)(s);
							countunit=count;
						}
						else{
							map.displaySecondaryCitizen((Citizen)(s));
							lastRescuable = (Citizen)(s);
							countres=count;
						}
						return;
					}
				}
				for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					Boolean res = false;
					if (map.getBtnsPlaces().get(10*i + j) == (JButton) e.getSource()){
						if(i==0 && j==0){
							map.displayBase();
							map.revalidate();
							map.repaint();
							mainView.getContentPane().revalidate();
							mainView.getContentPane().repaint();
							mainView.setVisible(true);
							res = true;
						}
						for (int j2 = 0;!res && j2 < cc.getVisibleBuildings().size(); j2++)
							if (cc.getVisibleBuildings().get(j2).getLocation().getX() == i && cc.getVisibleBuildings().get(j2).getLocation().getY() == j) {
								res = true;
								map.displayBuilding(cc.getVisibleBuildings().get(j2));
								lastRescuable=cc.getVisibleBuildings().get(j2);
								countres=count;
								break;
							}
						for (int j2 = 0; !res && j2 < cc.getVisibleCitizens().size(); j2++)
							if (cc.getVisibleCitizens().get(j2).getLocation().getX() == i && cc.getVisibleCitizens().get(j2).getLocation().getY() == j) {
								res = true;
								map.displayCitizen(cc.getVisibleCitizens().get(j2));
								lastRescuable=cc.getVisibleCitizens().get(j2);
								countres=count;
								break;
							}
						if(!res) {
							map.displayBlank();
							mainView.getContentPane().revalidate();
							mainView.getContentPane().repaint();
							mainView.setVisible(true);
						}
							}
					

							}
						}
				if (e.getSource()==map.getRespondbutton() ) {
					if (countunit<countres && countunit>0) {
					try{
						lastUnit.respond(lastRescuable);
						map.displayResponding();
						if(effectsOn){
							if(lastUnit instanceof FireTruck){
								curClip = loadClip("Fire.wav");
							}
							else if(lastUnit instanceof GasControlUnit){
								curClip = loadClip("gcu.wav");
							}
							else if(lastUnit instanceof Evacuator){
								curClip = loadClip("evac.wav");
							}
							else if(lastUnit instanceof DiseaseControlUnit){
								curClip = loadClip("dcu.wav");
							}
							else if(lastUnit instanceof Ambulance){
								curClip = loadClip("amb.wav");
							}
						}
						
					}
					catch (CannotTreatException e1) {
						JOptionPane.showMessageDialog(null,
								e1.getMessage(), "",
								JOptionPane.ERROR_MESSAGE);
					} catch (IncompatibleTargetException e1) {
						JOptionPane.showMessageDialog(null,
								e1.getMessage(), "",
								JOptionPane.ERROR_MESSAGE);

					}}
					else {
						JOptionPane.showMessageDialog(null,
								"Click on the unit then rescuable then respond", "",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	public Clip loadClip( String filename )
	{
	    Clip in = null;
	    try
	    {
	    	AudioInputStream  audioIn=  
	                AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile()); 
	    	in = AudioSystem.getClip();
	        in.open( audioIn );
	    }
	    catch( Exception e )
	    {
	        e.printStackTrace();
	    }
	    in.start();

	    return in;
	}


	public static void main(String[] args) throws Exception {
		StinkyGameGUI gui = new StinkyGameGUI();
	}

	public CommandCenter getCc() {
		return cc;
	}

}
