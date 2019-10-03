package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Address;
import controller.CommandCenter;
import controller.StinkyGameGUI;

public class Map2 extends JLabel{
	private StinkyGameGUI listener;
	private Main jf;
	private int width, height;
	private CommandCenter cc;
	private ArrayList<JButton> btnsPlaces, unitInPanel;
	private JLabel curCycle, casualties;
	private JLabel [] infoLabels, secondaryInfoLabels;
	private JButton nextcyclebutton, respondbutton;
	private JLabel componentsInBuildingsLabel, background;
	public ArrayList<JLabel> UnitsPages, ComponentsPages, secondaryComponentsPages;
	private ImageIcon b1, b2, b3, b4, citizen, dead, building, GasLeakBuilding, amb, BurningBuilding, gcu, dcu, evac, fireT, injuredB, infectedB, collapsing, collapsed;
	public JButton nextComponentsPage, prevComponentsPage, nextSecComponentsPage, prevSecComponentsPage, nextUnitsPage, prevUnitsPage, hint, effects;
	private int Page, curBackground;
	private ArrayList<SimulatableAndButton> sAndB, secondarySandB;
	private JComboBox<String> combo, combocas;
	public int componentsPage, secondaryComponentsPage;
	public ArrayList<Disaster> untreatedDisasters;
	
	public Map2(StinkyGameGUI listener, Main jf) {
		this.jf = jf;
		this.listener = listener;
		cc = listener.getCc();
		width = jf.getWidth();
		height = jf.getHeight();
		
		setHeight(height);
		createMap();
		createUnitButtons();
		sAndB = new ArrayList<SimulatableAndButton>();
		secondarySandB = new ArrayList<SimulatableAndButton>();
		collapsed= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Collapse.png").getScaledInstance((int)(jf.getHeight()*0.084), (int)(jf.getHeight()*0.084), java.awt.Image.SCALE_SMOOTH));
		building= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/building.png").getScaledInstance((int)(jf.getHeight()*0.084), (int)(jf.getHeight()*0.084), java.awt.Image.SCALE_SMOOTH));
		BurningBuilding= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/burningB.png").getScaledInstance((int)(jf.getHeight()*0.084), (int)(jf.getHeight()*0.084), java.awt.Image.SCALE_SMOOTH));
		GasLeakBuilding= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/gas.png").getScaledInstance((int)(jf.getHeight()*0.084), (int)(jf.getHeight()*0.084), java.awt.Image.SCALE_SMOOTH));
		collapsing = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/collapsing.png").getScaledInstance((int) (jf.getHeight() * 0.084),(int) (jf.getHeight() * 0.084),java.awt.Image.SCALE_SMOOTH));
		citizen = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Citizen.png").getScaledInstance((int) (jf.getHeight() * 0.084),(int) (jf.getHeight() * 0.084),java.awt.Image.SCALE_SMOOTH));
		injuredB = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/injuredC.png").getScaledInstance((int) (jf.getHeight() * 0.084),(int) (jf.getHeight() * 0.084),java.awt.Image.SCALE_SMOOTH));
		infectedB = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/infectedC.png").getScaledInstance((int) (jf.getHeight() * 0.084),(int) (jf.getHeight() * 0.084),java.awt.Image.SCALE_SMOOTH));
		dead = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/dead.png").getScaledInstance((int) (jf.getHeight() * 0.084),(int) (jf.getHeight() * 0.084),java.awt.Image.SCALE_SMOOTH));
		amb = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/ambulance.png").getScaledInstance((int) (jf.getHeight() * 0.084),(int) (jf.getHeight() * 0.084),java.awt.Image.SCALE_SMOOTH));
		dcu= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/dcu.png").getScaledInstance((int) (jf.getHeight() * 0.084),(int) (jf.getHeight() * 0.084), java.awt.Image.SCALE_SMOOTH));
		evac= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/evacuator.png").getScaledInstance((int) (jf.getHeight() * 0.084),(int) (jf.getHeight() * 0.084), java.awt.Image.SCALE_SMOOTH));
		fireT= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Firetruck.png").getScaledInstance((int) (jf.getHeight() * 0.084),(int) (jf.getHeight() * 0.084), java.awt.Image.SCALE_SMOOTH));
		gcu= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/gcu.png").getScaledInstance((int) (jf.getHeight() * 0.084),(int) (jf.getHeight() * 0.084), java.awt.Image.SCALE_SMOOTH));
		
		combocas = new JComboBox<String>();
		combocas.setVisible(false);
		combocas.setBounds((int) (jf.getWidth() * 0.31),(int) (jf.getWidth() * 0.485), (int) (jf.getWidth() * 0.19), (int) (jf.getHeight() * 0.039));
		this.add(combocas);
		combocas.setBackground(Color.black);
		combocas.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 17));
		combocas.setForeground(Color.white);
		combo = new JComboBox<String>();
		combo.setVisible(false);
		combo.setBounds((int) (jf.getWidth() * 0.007),(int) (jf.getWidth() * 0.485), (int) (jf.getWidth() * 0.27), (int) (jf.getHeight() * 0.039));
		combo.setBackground(Color.black);
		combo.setForeground(Color.white);
		combo.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 17));
		add(combo);
		
		respondbutton = new JButton("Respond");
		respondbutton.setBounds((int)(width*0.635),(int)(jf.getHeight()*0.09),(int)(jf.getHeight()*0.1) ,(int)(jf.getHeight()*0.05));
		add(respondbutton);
		respondbutton.setBackground(Color.darkGray);
		respondbutton.setForeground(Color.WHITE);
		respondbutton.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 17));
		respondbutton.setVisible(true);
		respondbutton.addActionListener(listener);
		
		nextcyclebutton= new JButton("Next Cycle");
		nextcyclebutton.setBounds((int)(width*0.55),(int)(jf.getHeight()*0.09),(int)(jf.getHeight()*0.14) ,(int)(jf.getHeight()*0.05));
		nextcyclebutton.setVisible(true);
		nextcyclebutton.setBackground(Color.darkGray);
		nextcyclebutton.setForeground(Color.white);
		nextcyclebutton.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 17));
		add(nextcyclebutton);
		nextcyclebutton.addActionListener(listener);

		casualties = new JLabel("Number of Casualties: " + cc.getEngine().calculateCasualties());
		casualties.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 30));
		casualties.setBounds((int)(width*0.55), (int)(jf.getHeight()*0.018),(int)(jf.getWidth()*0.5), (int)(jf.getHeight()*0.1));
		casualties.setVisible(true);
		add(casualties);
		
		infoLabels = new JLabel[9];
		int h=(int)(jf.getHeight()*0.125);
		for (int i = 0; i < infoLabels.length; i++) {
			infoLabels[i] = new JLabel();
			JLabel j = infoLabels[i];
			j.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 30));
			j.setBounds((int)(width*0.55), h,(int)(jf.getWidth()*0.33), (int)(jf.getHeight()*0.075));
			j.setVisible(true);
			add(j);
			h+=((int)(jf.getHeight()*0.029));
		}
		ImageIcon right = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/right.png").getScaledInstance((int) (jf.getHeight() * 0.02),(int) (jf.getHeight() * 0.06), java.awt.Image.SCALE_SMOOTH));
		ImageIcon left = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/left.png").getScaledInstance((int) (jf.getHeight() * 0.02),(int) (jf.getHeight() * 0.06), java.awt.Image.SCALE_SMOOTH));
		nextComponentsPage = new JButton(right);
		nextComponentsPage.setContentAreaFilled(false);
		nextComponentsPage.setBounds((int)(width*0.74), h + 72,(int)(jf.getWidth()*0.02), (int)(jf.getHeight()*0.06));
		nextComponentsPage.addActionListener(listener);
		nextComponentsPage.setVisible(false);
		add(nextComponentsPage);
		prevComponentsPage = new JButton(left);
		prevComponentsPage.setContentAreaFilled(false);
		prevComponentsPage.setBounds((int)(width*0.555), h + 72,(int)(jf.getWidth()*0.02), (int)(jf.getHeight()*0.06));
		prevComponentsPage.addActionListener(listener);
		prevComponentsPage.setVisible(false);
		add(prevComponentsPage);
		ComponentsPages = new ArrayList<JLabel>();
		
		secondaryInfoLabels = new JLabel [9];
		int secondaryH = h + ((int)(jf.getHeight()*0.143));
		for (int i = 0; i < 9; i++) {
			secondaryInfoLabels[i] = new JLabel();
			JLabel j = secondaryInfoLabels[i];
			j.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 26));
			j.setBounds((int)(width*0.55), secondaryH,(int)(jf.getWidth()*0.3), (int)(jf.getHeight()*0.025));
			j.setVisible(true);
			add(j);
			secondaryH += ((int)(jf.getHeight()*0.0255));
		}
		
		nextSecComponentsPage = new JButton(right);
		nextSecComponentsPage.setContentAreaFilled(false);
		nextSecComponentsPage.setBounds((int)(width*0.74), secondaryH + 30,(int)(jf.getWidth()*0.02), (int)(jf.getHeight()*0.06));
		nextSecComponentsPage.addActionListener(listener);
		nextSecComponentsPage.setVisible(false);
		add(nextSecComponentsPage);
		prevSecComponentsPage = new JButton(left);
		prevSecComponentsPage.setContentAreaFilled(false);
		prevSecComponentsPage.setBounds((int)(width*0.555), secondaryH + 30,(int)(jf.getWidth()*0.02), (int)(jf.getHeight()*0.06));
		prevSecComponentsPage.addActionListener(listener);
		prevSecComponentsPage.setVisible(false);
		add(prevSecComponentsPage);
		secondaryComponentsPages = new ArrayList<JLabel>();
		
		ImageIcon soundEffect = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/soundEffects.jpg").getScaledInstance((int) (getHeight() * 0.05),(int) (getHeight() * 0.05), java.awt.Image.SCALE_SMOOTH));
		effects = new JButton(soundEffect);
		effects.setBounds((int) (getWidth() *0.84), (int)(getHeight() * 0.02), (int)(getHeight() * 0.05), (int)(getHeight() * 0.05));
		effects.addActionListener(listener);
		effects.setVisible(true);
		add(effects);
		
		ImageIcon hints = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/hint.png").getScaledInstance((int) (getHeight() * 0.05),(int) (getHeight() * 0.05), java.awt.Image.SCALE_SMOOTH));
		hint = new JButton(hints);
		hint.setBounds((int) (getWidth() *0.805), (int)(getHeight() * 0.02), (int)(getHeight() * 0.05), (int)(getHeight() * 0.05));
		hint.setContentAreaFilled(false);
		hint.addActionListener(listener);
		hint.setVisible(true);
		hint.setBorderPainted(false);
		add(hint);
		
		untreatedDisasters = new ArrayList<Disaster>();
		
		curCycle = new JLabel("Current Cycle #" + cc.getEngine().getCurrentCycle());
		curCycle.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 30));
		curCycle.setBounds((int)(width*0.55), (int)(jf.getHeight()*0.0003),(int)(jf.getWidth()*0.5), (int)(jf.getHeight()*0.1));
		curCycle.setVisible(true);
		add(curCycle);
		add(jf.mute);
		jf.mute.setContentAreaFilled(false);
		
		curBackground = 0;
		
		b1= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/cartoonLandscape.jpg").getScaledInstance(jf.getWidth(),jf.getHeight(), java.awt.Image.SCALE_SMOOTH));
		b2= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Ev2.png").getScaledInstance(jf.getWidth(),jf.getHeight(), java.awt.Image.SCALE_SMOOTH));
		b3= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Ev3.jpg").getScaledInstance(jf.getWidth(),jf.getHeight(), java.awt.Image.SCALE_SMOOTH));
		b4= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Ev4.png").getScaledInstance(jf.getWidth(),jf.getHeight(), java.awt.Image.SCALE_SMOOTH));
		
		
		//back= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/cartoonLandscape.jpg").getScaledInstance(jf.getWidth(),jf.getHeight(), java.awt.Image.SCALE_SMOOTH));
		background= new JLabel(b1);
		add(background);
		background.setBounds(0,0,getWidth(),getHeight());
		validate();
		repaint();
		
	}

	public void createMap(){
		setSize(jf.getWidth(),jf.getHeight());
		int size= (int)(jf.getHeight()*0.078);
		int rwidth= (int)(jf.getHeight()*0.084);
		ImageIcon grass= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images\\PARKicon.png").getScaledInstance(rwidth, size, java.awt.Image.SCALE_SMOOTH));
		
		int last_height=(int)(jf.getHeight()*0.0018);
		int last_width= (int)(jf.getWidth()*0.01);
		int space= (int)(jf.getHeight()*0.009);
		int spacew= (int)(jf.getHeight()*0.011);
		
		btnsPlaces = new ArrayList<JButton>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				JButton b = new JButton(grass);
				b.setBorderPainted(false);
				if(i == 0 && j == 0){
					ImageIcon base= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images\\base.png").getScaledInstance(rwidth, size, java.awt.Image.SCALE_SMOOTH));
					b.setIcon(base);
					b.setContentAreaFilled(false);
					b.setBorderPainted(false);
				}				
				b.setBounds(last_width,last_height, rwidth,size);
				b.setContentAreaFilled(false);
				//b.setBorderPainted(false);
				btnsPlaces.add(b);
				b.addActionListener(listener);
				last_width+= rwidth+spacew;
				add(b);
			}
			last_height+=size+space;
			last_width= (int)(jf.getWidth()*0.01);
		}
		revalidate();
		repaint();
	}
	
	public void createUnitButtons(){
		UnitsPages = new ArrayList<JLabel>();
		JLabel page = new JLabel();
		page.setBounds((int)(getWidth()*0.92), (int) (jf.getHeight() * 0.03),(int)(jf.getWidth()*0.1), (int)(jf.getHeight()*0.9));
		page.setVisible(true);
		UnitsPages.add(page);
		add(page);
		Page = 0;
		
		ImageIcon up = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/up.png").getScaledInstance((int) (jf.getHeight() * 0.06),(int) (jf.getHeight() * 0.035), java.awt.Image.SCALE_SMOOTH));
		ImageIcon down = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/down.png").getScaledInstance((int) (jf.getHeight() * 0.06),(int) (jf.getHeight() * 0.035), java.awt.Image.SCALE_SMOOTH));
		
		
		int startWidth = (int)(page.getWidth()*0.01);
		int startH = (int)(page.getHeight()*0.04);
		int initialH = startH;
		int dim = (int)(page.getHeight()*0.1);
		int space= (int)(page.getHeight()*0.005);
		int count = 0;
		nextUnitsPage = new JButton(down);
		prevUnitsPage = new JButton(up);
		nextUnitsPage.addActionListener(listener);
		prevUnitsPage.addActionListener(listener);
		nextUnitsPage.setContentAreaFilled(false);
		prevUnitsPage.setContentAreaFilled(false);
		nextUnitsPage.setBorderPainted(false);
		prevUnitsPage.setBorderPainted(false);
		nextUnitsPage.setBounds((int) (jf.getWidth() * 0.93), (int) (jf.getHeight() * 0.82), (int)(jf.getHeight()*0.06), (int)(jf.getHeight()*0.035));
		prevUnitsPage.setBounds((int) (jf.getWidth() * 0.93), (int) (jf.getHeight() * 0.01), (int)(jf.getHeight()*0.06), (int)(jf.getHeight()*0.035));
		ImageIcon amb= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/ambulance.png").getScaledInstance(dim, dim, java.awt.Image.SCALE_SMOOTH));
		ImageIcon dcu= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/dcu.png").getScaledInstance(dim, dim, java.awt.Image.SCALE_SMOOTH));
		ImageIcon evac= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/evacuator.png").getScaledInstance(dim, dim, java.awt.Image.SCALE_SMOOTH));
		ImageIcon fireT= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Firetruck.png").getScaledInstance(dim, dim, java.awt.Image.SCALE_SMOOTH));
		ImageIcon gcu= new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/gcu.png").getScaledInstance(dim, dim, java.awt.Image.SCALE_SMOOTH));
		unitInPanel = new ArrayList<JButton>();
		if(cc.getEmergencyUnits().size()>8){
			nextUnitsPage.setVisible(true);
			prevUnitsPage.setVisible(true);
			add(nextUnitsPage);
			add(prevUnitsPage);
			
		}
		for (int i = 0; i < cc.getEmergencyUnits().size(); i++) {
			JButton b = new JButton();
			if(cc.getEmergencyUnits().get(i) instanceof Ambulance)
				b.setIcon(amb);
			else if(cc.getEmergencyUnits().get(i) instanceof Evacuator)
				b.setIcon(evac);
			else if(cc.getEmergencyUnits().get(i) instanceof FireTruck)
				b.setIcon(fireT);
			else if(cc.getEmergencyUnits().get(i) instanceof GasControlUnit)
				b.setIcon(gcu);
			else if(cc.getEmergencyUnits().get(i) instanceof DiseaseControlUnit)
				b.setIcon(dcu);
			b.setContentAreaFilled(false);
			b.setBorderPainted(false);
			b.setBounds(startWidth,startH, dim, dim);
			b.addActionListener(listener);
			page.add(b);
			unitInPanel.add(b);
			count++;
			if(count%8==0){
				page = new JLabel();
				page.setBounds((int)(getWidth()*0.92), (int) (jf.getHeight() * 0.05),(int)(jf.getWidth()*0.1), (int)(jf.getHeight()*0.9));
				UnitsPages.add(page);
				add(page);
				page.setVisible(false);
				startH = initialH;
			}
			else
				startH += (dim + space);
		}
		revalidate();
		repaint();
	}
	
	public void setPage(int page) {
		Page = page;
	}

	public void displayBuilding(ResidentialBuilding rb){
		displayBlank();
		remove(background);
		infoLabels[0].setText("Address: (" + rb.getLocation().getX() + "," + rb.getLocation().getY() + ")");
		if(rb.getDisaster() instanceof Collapse)
			infoLabels[1].setText("Disaster: Collapse");
		else if(rb.getDisaster() instanceof Fire)
			infoLabels[1].setText("Disaster: Fire");
		else
			infoLabels[1].setText("Disaster: Gas Leak");
		infoLabels[2].setText("Number of Occupants: " +  rb.getOccupants().size());
		
		JProgressBar jb = new JProgressBar(0,100);
		jb.setBounds((int)(infoLabels[3].getWidth() * 0.0),(int) (infoLabels[3].getHeight()* 0.39),(int)(infoLabels[3].getWidth()* 0.7), (int) (infoLabels[3].getHeight()* 0.36));         
		jb.setValue(rb.getStructuralIntegrity());
		jb.setString("Structural Integrity: " +  rb.getStructuralIntegrity());
		jb.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 23));
		jb.setStringPainted(true); 
		jb.setForeground(Color.BLACK);
		jb.setBackground(Color.white);
		jb.setVisible(true);
		infoLabels[3].add(jb);
		
		JProgressBar jb1 = new JProgressBar(0,100);
		jb1.setBounds((int)(infoLabels[4].getWidth() * 0.0),(int) (infoLabels[4].getHeight()* 0.39),(int)(infoLabels[4].getWidth()* 0.7), (int) (infoLabels[4].getHeight()* 0.36));         
		jb1.setValue(rb.getFoundationDamage());
		jb1.setString("Foundation Damage: " +  rb.getFoundationDamage());
		jb1.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 23));
		jb1.setStringPainted(true); 
		jb1.setForeground(Color.gray);
		jb1.setBackground(Color.darkGray);
		jb1.setVisible(true);
		infoLabels[4].add(jb1);

		JProgressBar jb2 = new JProgressBar(0,100);
		jb2.setBounds((int)(infoLabels[5].getWidth() * 0.0),(int) (infoLabels[5].getHeight()* 0.39),(int)(infoLabels[5].getWidth()* 0.7), (int) (infoLabels[5].getHeight()* 0.36));         
		jb2.setValue(rb.getFireDamage());
		jb2.setString("Fire Damage: " + rb.getFireDamage());
		jb2.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 23));
		jb2.setStringPainted(true); 
		jb2.setForeground(Color.ORANGE);
		jb2.setBackground(Color.red);
		jb2.setVisible(true);
		infoLabels[5].add(jb2);
		
		JProgressBar jb3 = new JProgressBar(0,100);
		jb3.setBounds((int)(infoLabels[6].getWidth() * 0.0),(int) (infoLabels[6].getHeight()* 0.39),(int)(infoLabels[6].getWidth()* 0.7), (int) (infoLabels[6].getHeight()* 0.36));         
		jb3.setValue(rb.getGasLevel());
		jb3.setString("Gas Level: " + rb.getGasLevel());
		jb3.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 23));
		jb3.setStringPainted(true); 
		jb3.setForeground(Color.yellow);
		jb3.setBackground(Color.green);
		jb3.setVisible(true);
		infoLabels[6].add(jb3);
		
		for (int i = 7; i < infoLabels.length; i++) {
			infoLabels[i].setText("");
		}
		
		JLabel page = new JLabel();
		page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.17),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
		page.setVisible(true);
		ComponentsPages.add(page);
		page.repaint();
		add(page);
		int startWidth = (int)(page.getWidth()*0.001);
		int startH = (int)(page.getHeight()*0.29);
		int initialW = startWidth;
		int dim = (int)(page.getHeight()*0.1);
		int space= (int)(page.getHeight()*0.005);
	
		int count = 0;
		sAndB = new ArrayList<SimulatableAndButton>();
		for (int i = 0; i < cc.getEmergencyUnits().size(); i++) {
			Unit unit = cc.getEmergencyUnits().get(i);
			if (cc.getEmergencyUnits().get(i).getLocation() == rb.getLocation()){
				JButton b = new JButton();
				if(unit instanceof Ambulance)
					b.setIcon(amb);
				else if(unit instanceof DiseaseControlUnit)
					b.setIcon(dcu);
				else if(unit instanceof Evacuator)
					b.setIcon(evac);
				else if(unit instanceof FireTruck)
					b.setIcon(fireT);
				else if(unit instanceof GasControlUnit)
					b.setIcon(gcu);
				b.setContentAreaFilled(false);
				b.setBorderPainted(false);
				b.setBounds(startWidth,startH, dim, dim);
				b.addActionListener(listener);
				b.setVisible(true);
				count++;
				SimulatableAndButton sb = new SimulatableAndButton(cc.getEmergencyUnits().get(i), b);
				sAndB.add(sb);
				page.add(b);
				startWidth +=(dim + space);
				
				if(count%3 == 0){
					page = new JLabel();
					page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.17),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
					add(page);
					ComponentsPages.add(page);
					page.setVisible(false);
					startWidth = initialW;
				}
			}
		}
		
		for (int i = 0; i < rb.getOccupants().size(); i++) {
			JButton b = new JButton();
			if(rb.getOccupants().get(i).getState() == CitizenState.DECEASED)
				b.setIcon(dead);
			else if(rb.getOccupants().get(i).getDisaster() instanceof Infection && rb.getOccupants().get(i).getDisaster().isActive()){
				b.setIcon(infectedB);
			}
			else if(rb.getOccupants().get(i).getDisaster() instanceof Injury && rb.getOccupants().get(i).getDisaster().isActive()){
				b.setIcon(injuredB);
			}
			else
				b.setIcon(citizen);
			b.setContentAreaFilled(false);
			b.setBorderPainted(false);
			b.setBounds(startWidth, startH, dim, dim);
			b.addActionListener(listener);
			b.setVisible(true);
			count++;
			SimulatableAndButton sb = new SimulatableAndButton(rb.getOccupants().get(i), b);
			sAndB.add(sb);
			page.add(b);
			
			startWidth +=(dim + space);
			if(count%3 == 0){
				page = new JLabel();
				page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.17),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
				add(page);
				ComponentsPages.add(page);
				page.setVisible(false);
				startWidth = initialW;
			}
		}
		if(count % 3 == 0 && count != 0 && ComponentsPages.size()>1) {
			int size = ComponentsPages.size();
			JLabel lastPage = ComponentsPages.get(size - 1);
			remove(lastPage);
			ComponentsPages.remove(size - 1);
		}
		if(count > 3){
			nextComponentsPage.setVisible(true);
			prevComponentsPage.setVisible(true);
		}
		add(background);
		revalidate();
		repaint();
	}
	

	public void displayCitizen(Citizen c){
		displayBlank();
		remove(background);
		infoLabels[0].setText("Location: (" + c.getLocation().getX() + "," + c.getLocation().getY() + ")");
		infoLabels[1].setText("Name: " + c.getName());
		if(c.getDisaster() instanceof Injury)
			infoLabels[4].setText("Disaster: Injury");
		else if(c.getDisaster() instanceof Infection)
			infoLabels[4].setText("Disaster: Infection");
		else
			infoLabels[4].setText("No Disaster on this citizen.");
		infoLabels[2].setText("National ID: " + c.getNationalID());
		infoLabels[3].setText("Age: " + c.getAge());
		infoLabels[5].setText("Citizen State: " + c.getState());
		JProgressBar jb = new JProgressBar(0,100);
		jb.setBounds((int)(infoLabels[6].getWidth() * 0.0),(int) (infoLabels[6].getHeight()* 0.39),(int)(infoLabels[6].getWidth()* 0.7), (int) (infoLabels[6].getHeight()* 0.36));         
		jb.setValue(c.getHp());
		jb.setString("Health Points: " +  c.getHp());
		jb.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 23));
		jb.setStringPainted(true); 
		jb.setForeground(Color.BLUE);
		jb.setBackground(Color.cyan);
		jb.setVisible(true);
		infoLabels[6].add(jb);
		
		JProgressBar jb1 = new JProgressBar(0,100);
		jb1.setBounds((int)(infoLabels[7].getWidth() * 0.0),(int) (infoLabels[7].getHeight()* 0.39),(int)(infoLabels[7].getWidth()* 0.7), (int) (infoLabels[7].getHeight()* 0.36));         
		jb1.setValue(c.getBloodLoss());
		jb1.setString("Blood Loss: " +  c.getBloodLoss());
		jb1.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 23));
		jb1.setStringPainted(true); 
		jb1.setForeground(Color.RED);
		jb1.setBackground(Color.pink);
		jb1.setVisible(true);
		infoLabels[7].add(jb1);

		JProgressBar jb2 = new JProgressBar(0,100);
		jb2.setBounds((int)(infoLabels[8].getWidth() * 0.0),(int) (infoLabels[8].getHeight()* 0.39),(int)(infoLabels[8].getWidth()* 0.7), (int) (infoLabels[8].getHeight()* 0.36));         
		jb2.setValue(c.getToxicity());
		jb2.setString("Toxicity: " +  c.getToxicity());
		jb2.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 23));
		jb2.setStringPainted(true); 
		jb2.setForeground(Color.green);
		jb2.setBackground(Color.lightGray);
		jb2.setVisible(true);
		infoLabels[8].add(jb2);
		
		
		JLabel page = new JLabel();
		page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.14),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
		page.setVisible(true);
		ComponentsPages.add(page);
		page.repaint();
		add(page);
		int startWidth = (int)(page.getWidth()*0.001);
		int startH = (int)(page.getHeight()*0.295);
		int initialW = startWidth;
		int dim = (int)(page.getHeight()*0.1);
		int space= (int)(page.getHeight()*0.005);
	
		int count = 0;
		sAndB = new ArrayList<SimulatableAndButton>();
		for (int i = 0; i < cc.getEmergencyUnits().size(); i++) {
			Unit unit = cc.getEmergencyUnits().get(i);
			if (cc.getEmergencyUnits().get(i).getLocation() == c.getLocation()){
				JButton b = new JButton();
				if(unit instanceof Ambulance)
					b.setIcon(amb);
				else if(unit instanceof DiseaseControlUnit)
					b.setIcon(dcu);
				else if(unit instanceof Evacuator)
					b.setIcon(evac);
				else if(unit instanceof FireTruck)
					b.setIcon(fireT);
				else if(unit instanceof GasControlUnit)
					b.setIcon(gcu);
				b.setContentAreaFilled(false);
				b.setBorderPainted(false);
				b.setBounds(startWidth,startH, dim, dim);
				b.addActionListener(listener);
				b.setVisible(true);
				count++;
				SimulatableAndButton sb = new SimulatableAndButton(cc.getEmergencyUnits().get(i), b);
				sAndB.add(sb);
				page.add(b);
				startWidth +=(dim + space);
				
				if(count%3 == 0){
					page = new JLabel();
					page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.14),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
					add(page);
					ComponentsPages.add(page);
					page.setVisible(false);
					startWidth = initialW;
				}
			}
		}
		
		add(background);
		revalidate();
		repaint();
	}
	public void displayBlank(){
		remove(background);
		for(JLabel cur: ComponentsPages) {
			cur.setVisible(false);
			remove(cur);
		}
		for(JLabel cur: secondaryComponentsPages) {
			cur.setVisible(false);
			remove(cur);
		}
		for (int i = 0; i < infoLabels.length; i++) {
			infoLabels[i].setText("");
			infoLabels[i].removeAll();
		}
		for (int i = 0; i < secondaryInfoLabels.length; i++) {
			secondaryInfoLabels[i].setText("");
			secondaryInfoLabels[i].removeAll();
		}
		componentsPage = 0;
		secondaryComponentsPage = 0;
		for (int i = 0; i < ComponentsPages.size(); i++) {
			ComponentsPages.get(i).removeAll();
		}
		for (int i = 0; i < secondaryComponentsPages.size(); i++) {
			secondaryComponentsPages.get(i).removeAll();
		}
		ComponentsPages.clear();
		nextComponentsPage.setVisible(false);
		prevComponentsPage.setVisible(false);
		secondaryComponentsPages.clear();
		nextSecComponentsPage.setVisible(false);
		prevSecComponentsPage.setVisible(false);
		
		add(background);
		revalidate();
		repaint();
	}
	
	public void displayBase(){
		displayBlank();
		remove(background);
		for (int i = 0; i < infoLabels.length; i++)
			infoLabels[i].setText("");
		infoLabels[8].setText("Base");
		componentsPage = 0;
		for (int i = 0; i < ComponentsPages.size(); i++) {
			ComponentsPages.get(i).removeAll();
		}
		ComponentsPages.clear();
		nextComponentsPage.setVisible(false);
		prevComponentsPage.setVisible(false);
		JLabel page = new JLabel();
		page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.17),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
		page.setVisible(true);
		ComponentsPages.add(page);
		page.repaint();
		add(page);
		int startWidth = (int)(page.getWidth()*0.001);
		int startH = (int)(page.getHeight()*0.29);
		int initialW = startWidth;
		int dim = (int)(page.getHeight()*0.1);
		int space= (int)(page.getHeight()*0.005);
		int count = 0;
		sAndB = new ArrayList<SimulatableAndButton>();
		for (int i = 0; i < cc.getEmergencyUnits().size(); i++) {
			Unit unit = cc.getEmergencyUnits().get(i);
			if (cc.getEmergencyUnits().get(i).getLocation().getX() == 0 && cc.getEmergencyUnits().get(i).getLocation().getY() == 0){
				JButton b = new JButton();
				if(unit instanceof Ambulance)
					b.setIcon(amb);
				else if(unit instanceof DiseaseControlUnit)
					b.setIcon(dcu);
				else if(unit instanceof Evacuator)
					b.setIcon(evac);
				else if(unit instanceof FireTruck)
					b.setIcon(fireT);
				else if(unit instanceof GasControlUnit)
					b.setIcon(gcu);
				b.setContentAreaFilled(false);
				b.setBorderPainted(false);
				b.setBounds(startWidth,startH, dim, dim);
				b.addActionListener(listener);
				b.setVisible(true);
				count++;
				SimulatableAndButton sb = new SimulatableAndButton(cc.getEmergencyUnits().get(i), b);
				sAndB.add(sb);
				page.add(b);
				startWidth +=(dim + space);
				if(count%3 == 0){
					page = new JLabel();
					page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.17),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
					add(page);
					ComponentsPages.add(page);
					page.setVisible(false);
					startWidth = initialW;
				}
			}
		}
		for (int i = 0; i < cc.getEngine().getCitizens().size(); i++) {
			if(cc.getEngine().getCitizens().get(i).getLocation().getX() == 0 && cc.getEngine().getCitizens().get(i).getLocation().getY() == 0){
				JButton b = new JButton();
				b.addActionListener(listener);
				b.setVisible(true);
				if(cc.getEngine().getCitizens().get(i).getState() == CitizenState.DECEASED)
					b.setIcon(dead);
				else if(cc.getEngine().getCitizens().get(i).getDisaster() instanceof Injury && cc.getEngine().getCitizens().get(i).getDisaster().isActive())
					b.setIcon(injuredB);
				else if(cc.getEngine().getCitizens().get(i).getDisaster() instanceof Infection && cc.getEngine().getCitizens().get(i).getDisaster().isActive())
					b.setIcon(infectedB);
				else
					b.setIcon(citizen);
				b.setBounds(startWidth, startH, dim, dim);
				b.setContentAreaFilled(false);
				b.setBorderPainted(false);
				count++;
				SimulatableAndButton sb = new SimulatableAndButton(cc.getEngine().getCitizens().get(i), b);
				sAndB.add(sb);
				page.add(b);
				startWidth +=(dim + space);
				if(count%3 == 0){
					page = new JLabel();
					add(page);
					page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.17),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
					ComponentsPages.add(page);
					page.setVisible(false);
					startWidth = initialW;
				}
			}
		}
		if(count % 3 == 0 && count != 0 && ComponentsPages.size()>1) {
			int size = ComponentsPages.size();
			JLabel lastPage = ComponentsPages.get(size - 1);
			remove(lastPage);
			ComponentsPages.remove(size - 1);
		}
		if(count > 3){
			nextComponentsPage.setVisible(true);
			prevComponentsPage.setVisible(true);
		}
		
		add(background);
		revalidate();
		repaint();
		setVisible(true);
	}
	
	
	public void displayUnit(Unit u){
		displayBlank();
		remove(background);
		infoLabels[0].setText("Unit ID: " + u.getUnitID());
		if(u instanceof Ambulance)
			infoLabels[1].setText("Type: Ambulance");
		else if(u instanceof DiseaseControlUnit)
			infoLabels[1].setText("Type: Disease Control Unit");
		else if(u instanceof FireTruck)
			infoLabels[1].setText("Type: Fire Truck");
		else if(u instanceof GasControlUnit)
			infoLabels[1].setText("Type: Gas Control Unit");
		else if(u instanceof Evacuator)
			infoLabels[1].setText("Type: Evacuator");
		infoLabels[2].setText("Location: (" + u.getLocation().getX() + "," + u.getLocation().getY() + ")");
		infoLabels[3].setText("Steps/Cycle: " + u.getStepsPerCycle());
		int next;
		if(u.getTarget()!=null && u.getTarget() instanceof Citizen){
			infoLabels[4].setText("Target is a Citizen.");
			infoLabels[5].setText("Target's Location: (" + u.getTarget().getLocation().getX() + "," + u.getTarget().getLocation().getY() + ")" );
			next = 6;
		}else if(u.getTarget()!=null && u.getTarget() instanceof ResidentialBuilding){
			infoLabels[4].setText("Target is a Building.");
			infoLabels[5].setText("Target's Address: (" + u.getTarget().getLocation().getX() + "," + u.getTarget().getLocation().getY() + ")" );
			next = 6;
		}else{
			infoLabels[4].setText("This unit has no target.");
			next = 5;
		}
		infoLabels[next++].setText("Unit's state: " + u.getState());
		if(u instanceof Evacuator){
			infoLabels[next++].setText("Number of Passengers: " + ((Evacuator)u).getPassengers().size());
		}
		for (int i = next; i < infoLabels.length; i++)
			infoLabels[i].setText("");

		if(u instanceof Evacuator && ((Evacuator)u).getPassengers().size() > 0){
			for (int i = 0; i < ComponentsPages.size(); i++) {
				ComponentsPages.get(i).removeAll();
			}
			ComponentsPages.clear();
			nextComponentsPage.setVisible(false);
			prevComponentsPage.setVisible(false);
			
			JLabel page = new JLabel();
			page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.17),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
			page.setVisible(true);
			ComponentsPages.add(page);
			page.repaint();
			add(page);
			int startWidth = (int)(page.getWidth()*0.001);
			int startH = (int)(page.getHeight()*0.29);
			int initialW = startWidth;
			int dim = (int)(page.getHeight()*0.1);
			int space= (int)(page.getHeight()*0.005);

			
			int count = 0;
			sAndB = new ArrayList<SimulatableAndButton>();
			for (int i = 0; i < ((Evacuator)u).getPassengers().size(); i++) {
				JButton b = new JButton();
				b.addActionListener(listener);
				b.setVisible(true);
				if(((Evacuator)u).getPassengers().get(i).getState() == CitizenState.DECEASED)
					b.setIcon(dead);
				else if(((Evacuator)u).getPassengers().get(i).getDisaster() instanceof Injury && ((Evacuator)u).getPassengers().get(i).getDisaster().isActive())
					b.setIcon(injuredB);
				else if(((Evacuator)u).getPassengers().get(i).getDisaster() instanceof Infection && ((Evacuator)u).getPassengers().get(i).getDisaster().isActive())
					b.setIcon(infectedB);
				else
					b.setIcon(citizen);
				b.setBounds(startWidth, startH, dim, dim);
				b.setContentAreaFilled(false);
				b.setBorderPainted(false);
				count++;
				SimulatableAndButton sb = new SimulatableAndButton(((Evacuator)u).getPassengers().get(i), b);
				sAndB.add(sb);
				page.add(b);
				startWidth +=(dim + space);
				if(count%3 == 0){
					page = new JLabel();
					add(page);
					page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.17),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
					ComponentsPages.add(page);
					page.setVisible(false);
					startWidth = initialW;
				}
			}
			if(count % 3 == 0 && count != 0 && ComponentsPages.size()>1) {
				int size = ComponentsPages.size();
				JLabel lastPage = ComponentsPages.get(size - 1);
				remove(lastPage);
				ComponentsPages.remove(size - 1);
			}
			if(count > 3){
				nextComponentsPage.setVisible(true);
				prevComponentsPage.setVisible(true);
			}
		}
		add(background);
		revalidate();
		repaint();
	}
	
	public void displaySecondaryUnit(Unit u){
		remove(background);
		secondaryInfoLabels[0].setText("Unit ID: " + u.getUnitID());
		if(u instanceof Ambulance)
			secondaryInfoLabels[1].setText("Type: Ambulance");
		else if(u instanceof DiseaseControlUnit)
			secondaryInfoLabels[1].setText("Type: Disease Control Unit");
		else if(u instanceof FireTruck)
			secondaryInfoLabels[1].setText("Type: Fire Truck");
		else if(u instanceof GasControlUnit)
			secondaryInfoLabels[1].setText("Type: Gas Control Unit");
		else if(u instanceof Evacuator)
			secondaryInfoLabels[1].setText("Type: Evacuator");
		secondaryInfoLabels[2].setText("Location: (" + u.getLocation().getX() + "," + u.getLocation().getY() + ")");
		secondaryInfoLabels[3].setText("Steps/Cycle: " + u.getStepsPerCycle());
		int next;
		if(u.getTarget()!=null && u.getTarget() instanceof Citizen){
			secondaryInfoLabels[4].setText("Target is a Citizen.");
			secondaryInfoLabels[5].setText("Target's Location: (" + u.getTarget().getLocation().getX() + "," + u.getTarget().getLocation().getY() + ")" );
			next = 6;
		}else if(u.getTarget()!=null && u.getTarget() instanceof ResidentialBuilding){
			secondaryInfoLabels[4].setText("Target is a Building.");
			secondaryInfoLabels[5].setText("Target's Address: (" + u.getTarget().getLocation().getX() + "," + u.getTarget().getLocation().getY() + ")" );
			next = 6;
		}else{
			secondaryInfoLabels[4].setText("This unit has no target.");
			next = 5;
		}
		secondaryInfoLabels[next++].setText("Unit's state: " + u.getState());
		if(u instanceof Evacuator){
			secondaryInfoLabels[next++].setText("Number of Passengers: " + ((Evacuator)u).getPassengers().size());
		}
		for (int i = next; i < secondaryInfoLabels.length; i++)
			secondaryInfoLabels[i].setText("");
		if(u instanceof Evacuator && ((Evacuator)u).getPassengers().size() > 0){
			for (int i = 0; i < secondaryComponentsPages.size(); i++) {
				secondaryComponentsPages.get(i).removeAll();
			}
			secondaryComponentsPages.clear();
			nextSecComponentsPage.setVisible(false);
			prevSecComponentsPage.setVisible(false);
			JLabel page = new JLabel();
			page.setBounds((int)(getWidth()*0.58), (int) (jf.getHeight() * 0.13),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.85));
			page.setVisible(true);
			secondaryComponentsPages.add(page);
			page.repaint();
			add(page);
			int startWidth = (int)(page.getWidth()*0.001);
			int startH = (int)(page.getHeight()*0.27);
			int initialW = startWidth;
			int dim = (int)(page.getHeight()*0.1);
			int space= (int)(page.getHeight()*0.005);
			int count = 0;
			//sAndB = new ArrayList<SimulatableAndButton>();
			for (int i = 0; i < ((Evacuator)u).getPassengers().size(); i++) {
				JButton b = new JButton();
				b.addActionListener(listener);
				if(((Evacuator)u).getPassengers().get(i).getState() == CitizenState.DECEASED)
					b.setIcon(dead);
				else if(((Evacuator)u).getPassengers().get(i).getDisaster() instanceof Injury && ((Evacuator)u).getPassengers().get(i).getDisaster().isActive())
					b.setIcon(injuredB);
				else if(((Evacuator)u).getPassengers().get(i).getDisaster() instanceof Infection && ((Evacuator)u).getPassengers().get(i).getDisaster().isActive())
					b.setIcon(infectedB);
				else
					b.setIcon(citizen);
				b.setBounds(startWidth, (int)(jf.getHeight()*0.63), dim, dim);
				b.setContentAreaFilled(false);
				b.setBorderPainted(false);
				count++;
				SimulatableAndButton sb = new SimulatableAndButton(((Evacuator)u).getPassengers().get(i), b);
				sAndB.add(sb);
				b.setVisible(true);
				page.add(b);
				startWidth +=(dim + space);
				if(count%3 == 0){
					page = new JLabel();
					add(page);
					page.setBounds((int)(getWidth()*0.8), (int) (jf.getHeight() * 0.5),(int)(jf.getWidth()*0.175), (int)(jf.getHeight()*0.9));
					secondaryComponentsPages.add(page);
					page.setVisible(false);
					startWidth = initialW;
				}

			}
			if(count % 3 == 0 && count != 0 && secondaryComponentsPages.size()>1) {
				int size = secondaryComponentsPages.size();
				JLabel lastPage = secondaryComponentsPages.get(size - 1);
				remove(lastPage);
				secondaryComponentsPages.remove(size - 1);
			}
			if(count > 3){
				nextSecComponentsPage.setVisible(true);
				prevSecComponentsPage.setVisible(true);
			}
		}
		add(background);
		repaint();
		revalidate();
		setVisible(true);
	}
	
	public void displayHint(){
		displayBlank();
		remove(background);
		if(jf.mode != level.EASY)
			hint.setVisible(false);
		comeUpWithHintPls();
		add(background);
	}
	
	public void comeUpWithHintPls(){
		String s = "Click Next Cycle";
		String s2 ="";
		for (int i = 0; i < cc.getVisibleBuildings().size(); i++) {
			if(cc.getVisibleBuildings().get(i).getDisaster()!= null && cc.getVisibleBuildings().get(i).getDisaster().isActive()){
				Boolean beingTreated = false;
				for (int j = 0; j < cc.getEmergencyUnits().size(); j++)
					if(cc.getEmergencyUnits().get(j).getState() != UnitState.IDLE && cc.getEmergencyUnits().get(j).getTarget() == cc.getVisibleBuildings().get(i))
						beingTreated = true;
				if(!beingTreated){
					Disaster d = cc.getVisibleBuildings().get(i).getDisaster();
					Unit curUnit;
					for (int j = 0; j < cc.getEmergencyUnits().size(); j++) {
						curUnit = cc.getEmergencyUnits().get(j);
						if(d instanceof Collapse && cc.getVisibleBuildings().get(i).getOccupants().size()!=0 && curUnit instanceof Evacuator && curUnit.getState() == UnitState.IDLE){
							infoLabels[0].setText("Send Evacuator with Unit-ID " + curUnit.getUnitID());
							infoLabels[1].setText("to the building in " + cc.getVisibleBuildings().get(i).getLocation());
							return;	
						}
						if(d instanceof Collapse && cc.getVisibleBuildings().get(i).getOccupants().size()!=0 && curUnit instanceof Evacuator && ((ResidentialBuilding)curUnit.getTarget()).getStructuralIntegrity()< cc.getVisibleBuildings().get(i).getStructuralIntegrity()){
							s = "Send Evacuator with Unit-ID " + curUnit.getUnitID();
							s2 = "to the building in " + cc.getVisibleBuildings().get(i).getLocation();
						}
						if(d instanceof Fire  && curUnit instanceof FireTruck && curUnit.getState() == UnitState.IDLE){
							infoLabels[0].setText("Send FireTruck with Unit-ID " + curUnit.getUnitID());
							infoLabels[1].setText("to the building in " + cc.getVisibleBuildings().get(i).getLocation());
							return;
						}
						if(d instanceof GasLeak && curUnit instanceof GasControlUnit && curUnit.getState() == UnitState.IDLE){
							infoLabels[0].setText("Send Gas-Conrol unit with Unit-ID " + curUnit.getUnitID());
							infoLabels[1].setText("to the building in " + cc.getVisibleBuildings().get(i).getLocation());
							return;
						}
						if(d instanceof Fire  && curUnit instanceof FireTruck && curUnit.getDistanceToTarget()> distance(cc.getVisibleBuildings().get(i).getLocation(), curUnit.getLocation())){
							s = "Send FireTruck with Unit-ID " + curUnit.getUnitID();
							s2 = "to the building in " + cc.getVisibleBuildings().get(i).getLocation();
						}
						if(d instanceof GasLeak && curUnit instanceof GasControlUnit && curUnit.getDistanceToTarget()> distance(cc.getVisibleBuildings().get(i).getLocation(), curUnit.getLocation())){
							s = "Send Gas-Conrol unit with Unit-ID " + curUnit.getUnitID();
							s2 = "to the building in " + cc.getVisibleBuildings().get(i).getLocation();
						}
						
					}
				}
			}
			
		}
		for (int i = 0; i < cc.getVisibleCitizens().size(); i++) {
			if(cc.getVisibleCitizens().get(i).getDisaster()!= null && cc.getVisibleCitizens().get(i).getDisaster().isActive()){
				Boolean beingTreated = false;
				for (int j = 0; j < cc.getEmergencyUnits().size(); j++)
					if(cc.getEmergencyUnits().get(j).getState() != UnitState.IDLE && cc.getEmergencyUnits().get(j).getTarget() == cc.getVisibleCitizens().get(i))
						beingTreated = true;
				if(!beingTreated){
					Disaster d = cc.getVisibleCitizens().get(i).getDisaster();
					Unit curUnit;
					for (int j = 0; j < cc.getEmergencyUnits().size(); j++) {
						curUnit = cc.getEmergencyUnits().get(j);
						if(d instanceof Injury  && curUnit instanceof Ambulance && curUnit.getState() == UnitState.IDLE){
							s = "Send Ambulance with Unit-ID " + curUnit.getUnitID();
							s2 = "to the citizen in " + cc.getVisibleCitizens().get(i).getLocation();
							return;
						}
						if(d instanceof Infection && curUnit instanceof DiseaseControlUnit && curUnit.getState() == UnitState.IDLE){
							s = "Send Disease-Conrol unit with Unit-ID " + curUnit.getUnitID();
							s2 = "to the citizen in " + cc.getVisibleCitizens().get(i).getLocation();
							return;
						}
						if(d instanceof Injury  && curUnit instanceof Ambulance && curUnit.getDistanceToTarget()> distance(cc.getVisibleCitizens().get(i).getLocation(), curUnit.getLocation())){
							s = "Send Ambulance with Unit-ID " + curUnit.getUnitID();
							s2 = "to the citizen in " + cc.getVisibleCitizens().get(i).getLocation();
						}
						if(d instanceof Infection && curUnit instanceof DiseaseControlUnit && curUnit.getDistanceToTarget()> distance(cc.getVisibleCitizens().get(i).getLocation(), curUnit.getLocation())){
							s = "Send Disease-Conrol unit with Unit-ID " + curUnit.getUnitID();
							s2 = "to the citizen in " + cc.getVisibleCitizens().get(i).getLocation();
						}
					}
				}
			}
			
		}
		infoLabels[0].setText(s);
		infoLabels[1].setText(s2);
		return;
	}
	
	public int distance(Address l1, Address l2){
		int x = 0;
		x += Math.abs(l1.getX() - l2.getX());
		x += Math.abs(l1.getY() - l2.getY());
		return x;
	}
	
	public void displaySecondaryCitizen(Citizen c){
		remove(background);
		secondaryInfoLabels[0].setText("Location: (" + c.getLocation().getX() + "," + c.getLocation().getY() + ")");
		secondaryInfoLabels[1].setText("Name: " + c.getName());
		if(c.getDisaster() instanceof Injury)
			secondaryInfoLabels[4].setText("Disaster: Injury");
		else if(c.getDisaster() instanceof Infection)
			secondaryInfoLabels[4].setText("Disaster: Infection");
		else
			secondaryInfoLabels[4].setText("No Disaster on this citizen.");
		secondaryInfoLabels[2].setText("National ID: " + c.getNationalID());
		secondaryInfoLabels[3].setText("Age: " + c.getAge());
		secondaryInfoLabels[5].setText("Citizen State: " + c.getState());
		secondaryInfoLabels[6].setText("Health Points: " +  c.getHp());
		secondaryInfoLabels[7].setText("Blood Loss: " +  c.getBloodLoss());
		secondaryInfoLabels[8].setText("Toxicity: " +  c.getToxicity());
		
//		JProgressBar jb = new JProgressBar(0,100);
//		jb.setBounds(0,(int) (secondaryInfoLabels[6].getHeight()* 0.3),(int)(secondaryInfoLabels[6].getWidth()* 0.7), (int) (secondaryInfoLabels[6].getHeight() * 0.7));         
//		jb.setValue(c.getHp());
//		jb.setString("Health Points: " +  c.getHp());
//		jb.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 15));
//		jb.setStringPainted(true); 
//		jb.setForeground(Color.BLUE);
//		jb.setBackground(Color.cyan);
//		jb.setVisible(true);
//		secondaryInfoLabels[6].removeAll();
//		secondaryInfoLabels[6].add(jb);
//		
//		JProgressBar jb1 = new JProgressBar(0,100);
//		jb1.setBounds(0,(int) (secondaryInfoLabels[7].getHeight()* 0.3),(int)(secondaryInfoLabels[7].getWidth()* 0.7), (int) (secondaryInfoLabels[7].getHeight() * 0.7));         
//		jb1.setValue(c.getBloodLoss());
//		jb1.setString("Blood Loss: " +  c.getBloodLoss());
//		jb1.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 15));
//		jb1.setStringPainted(true); 
//		jb1.setForeground(Color.RED);
//		jb1.setBackground(Color.pink);
//		jb1.setVisible(true);
//		secondaryInfoLabels[7].add(jb1);
////		secondaryInfoLabels[7].setText("ghfcfvsvvdv");
//
//		JProgressBar jb2 = new JProgressBar(0,100);
//		jb2.setBounds(0,(int) (secondaryInfoLabels[8].getHeight()* 0.3),(int)(secondaryInfoLabels[8].getWidth()* 0.7), (int) (secondaryInfoLabels[8].getHeight() * 0.7));         
//		jb2.setValue(c.getToxicity());
//		jb2.setString("Toxicity: " +  c.getToxicity());
//		jb2.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 15));
//		jb2.setStringPainted(true); 
//		jb2.setForeground(Color.green);
//		jb2.setBackground(Color.lightGray);
//		jb2.setVisible(true);
//		secondaryInfoLabels[8].add(jb2);
		
		add(background);
	}
	
	public void displayResponding(){
		displayBlank();
		remove(background);
		infoLabels[0].setText("The unit is responding");
		add(background);
	}
	
	//Mariem you're the bestest!!!!
	
	public void updateBackground(){
		background.removeAll();
		curBackground = (curBackground + 1)%4;
		if(curBackground == 0){
			background.setIcon(b1);
			for (int i = 0; i < infoLabels.length; i++) {
				infoLabels[i].setForeground(Color.BLACK);
			}
			for (int i = 0; i < secondaryInfoLabels.length; i++) {
				secondaryInfoLabels[i].setForeground(Color.BLACK);
			}
			curCycle.setForeground(Color.BLACK);
			casualties.setForeground(Color.BLACK);
			jf.mute.setContentAreaFilled(false);
			hint.setContentAreaFilled(false);
			prevUnitsPage.setContentAreaFilled(false);
			nextUnitsPage.setContentAreaFilled(false);
			nextComponentsPage.setContentAreaFilled(false);
			prevComponentsPage.setContentAreaFilled(false);
			nextSecComponentsPage.setContentAreaFilled(false);
			prevSecComponentsPage.setContentAreaFilled(false);
			btnsPlaces.get(0).setContentAreaFilled(false);
		}
		else if(curBackground == 1)
			background.setIcon(b2);
		else if(curBackground == 2){
			background.setIcon(b3);
			for (int i = 0; i < secondaryInfoLabels.length; i++) {
				secondaryInfoLabels[i].setForeground(Color.WHITE);
			}
		}
		else if(curBackground == 3){
			background.setIcon(b4);
			for (int i = 0; i < infoLabels.length; i++) {
				infoLabels[i].setForeground(Color.WHITE);
			}
			
			curCycle.setForeground(Color.white);
			casualties.setForeground(Color.white);
			jf.mute.setContentAreaFilled(true);
			hint.setContentAreaFilled(true);
			prevUnitsPage.setContentAreaFilled(true);
			nextUnitsPage.setContentAreaFilled(true);
			nextComponentsPage.setContentAreaFilled(true);
			prevComponentsPage.setContentAreaFilled(true);
			nextSecComponentsPage.setContentAreaFilled(true);
			prevSecComponentsPage.setContentAreaFilled(true);
			btnsPlaces.get(0).setContentAreaFilled(true);
		}
	}

		public void nxtcycle() {
			displayBlank();
			remove(background);
			for (int i = 0; i < infoLabels.length; i++)
				infoLabels[i].setText("");
			cc.getEngine().nextCycle();
			if(cc.getEngine().getCurrentCycle()%3 == 0)
				updateBackground();
			updatecurr();
			int x = cc.getEngine().calculateCasualties();
			casualties.setText("Number of Casualties: " + x);
			if( cc.getEngine().checkGameOver())
				jf.GameOver();
			if(x > 0)
				combocas.setVisible(true);
			if(cc.getEngine().getExecutedDisasters().size() > 0)
				combo.setVisible(true);
			updateMap();
			updateUnitLocation();
			//ZEINA
			String text = "";
			ArrayList<Disaster> d = cc.getEngine().getExecutedDisasters();
			for (int i = 0; i < d.size(); i++) {
				if(d.get(i).getStartCycle() == cc.getEngine().getCurrentCycle()){
					if(d.get(i) instanceof Fire)
						text+= ("Fire Disaster on the Building with location (" + d.get(i).getTarget().getLocation().getX() + "," +  d.get(i).getTarget().getLocation().getY() + ")" + "\n");
					else if(d.get(i) instanceof GasLeak)
						text+= ("Gas leakage in the Building with location (" + d.get(i).getTarget().getLocation().getX() + "," +  d.get(i).getTarget().getLocation().getY() + ")" + "\n");
					else if(d.get(i) instanceof Collapse)
						text+= ("Building with location (" + d.get(i).getTarget().getLocation().getX() + "," +  d.get(i).getTarget().getLocation().getY() + ") collapsed." + "\n");
					else if(d.get(i) instanceof Injury)
						text+= ("Citizen named " + ((Citizen)d.get(i).getTarget()).getName()+ " with location (" + d.get(i).getTarget().getLocation().getX() + "," +  d.get(i).getTarget().getLocation().getY() + ") is injured." + "\n");
					else if(d.get(i) instanceof Infection)
						text+= ("Citizen named " + ((Citizen)d.get(i).getTarget()).getName()+ " with location (" + d.get(i).getTarget().getLocation().getX() + "," +  d.get(i).getTarget().getLocation().getY() + ") has an Infection." + "\n");
					combo.addItem(text);
					text ="";
				}
			}
			String cas= "";
			if (cc.getEngine().calculateCasualties()>0) {
				combocas.removeAllItems();
				for (int i=0; i<cc.getEngine().getCitizens().size(); i++) {
					if (cc.getEngine().getCitizens().get(i).getState()==CitizenState.DECEASED) {	
					    	cas= "Name of dead citizen: " +cc.getEngine().getCitizens().get(i).getName()+ " in (" + cc.getEngine().getCitizens().get(i).getLocation().getX() + "," + cc.getEngine().getCitizens().get(i).getLocation().getY() +").";
					    	combocas.addItem(cas);
					   }
					}
				}
			componentsPage = 0;
			
			if(jf.mode == level.MEDIUM)
				hint.setVisible(true);
			nextComponentsPage.setVisible(false);
			prevComponentsPage.setVisible(false);
			//ZEINA
			add(background);
			revalidate();
			repaint();
		}

		public void updatecurr() {
			curCycle.setText("Current Cycle #" + cc.getEngine().getCurrentCycle());
			revalidate();
			repaint();
		}

		public void updateMap() {
			for (int i=0; i<10; i++) {
				for (int j=0; j<10; j++) {
					if(i == 0 && j==0){
						j++;
					}
					for (int k=0; k<cc.getVisibleCitizens().size(); k++) {
						if (cc.getVisibleCitizens().get(k).getLocation().getX()==i && cc.getVisibleCitizens().get(k).getLocation().getY()==j) {
							if (cc.getVisibleCitizens().get(k).getState()==CitizenState.DECEASED) 
								btnsPlaces.get(i*10+j).setIcon(dead);
							else if(cc.getVisibleCitizens().get(k).getDisaster() instanceof Injury && cc.getVisibleCitizens().get(k).getDisaster().isActive())
								btnsPlaces.get(i*10+j).setIcon(injuredB);
							else if(cc.getVisibleCitizens().get(k).getDisaster() instanceof Infection && cc.getVisibleCitizens().get(k).getDisaster().isActive())
								btnsPlaces.get(i*10+j).setIcon(infectedB);
							else
								btnsPlaces.get(i*10+j).setIcon(citizen);
							btnsPlaces.get(i*10+j).setContentAreaFilled(false);
						}
					}

					for (int k=0; k<cc.getVisibleBuildings().size(); k++) {
						if (cc.getVisibleBuildings().get(k).getLocation().getX()==i && cc.getVisibleBuildings().get(k).getLocation().getY()==j) {
							if (cc.getVisibleBuildings().get(k).getStructuralIntegrity()==0) {
								btnsPlaces.get(i*10+j).setIcon(collapsed);
							}
							else if(cc.getVisibleBuildings().get(k).getDisaster() instanceof GasLeak && cc.getVisibleBuildings().get(k).getDisaster().isActive())
								btnsPlaces.get(i*10+j).setIcon(GasLeakBuilding);
							else if(cc.getVisibleBuildings().get(k).getDisaster() instanceof Fire && cc.getVisibleBuildings().get(k).getDisaster().isActive())
								btnsPlaces.get(i*10+j).setIcon(BurningBuilding);
							else if(cc.getVisibleBuildings().get(k).getDisaster() instanceof Collapse && cc.getVisibleBuildings().get(k).getDisaster().isActive())
								btnsPlaces.get(i*10+j).setIcon(collapsing);
							else
								btnsPlaces.get(i*10+j).setIcon(building);
							btnsPlaces.get(i*10+j).setContentAreaFilled(false);
						}
					}
				}
			}
			revalidate();
			repaint();
		}
		//Farah bardo
	
	public StinkyGameGUI getListener() {
		return listener;
	}
	
	public void updateUnitLocation(){
		
	}

	public void setListener(StinkyGameGUI listener) {
		this.listener = listener;
	}

	public Main getJf() {
		return jf;
	}

	public JLabel[] getInfoLabels() {
		return infoLabels;
	}

	public JLabel getcomponentsInBuildingsLabel() {
		return componentsInBuildingsLabel;
	}

	public ArrayList<JButton> getUnitInPanel() {
		return unitInPanel;
	}

	public void setJf(Main jf) {
		this.jf = jf;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public CommandCenter getCc() {
		return cc;
	}

	public void setCc(CommandCenter cc) {
		this.cc = cc;
	}

	public ArrayList<JButton> getBtnsPlaces() {
		return btnsPlaces;
	}

	public JLabel getCurCycle() {
		return curCycle;
	}

	public void setCurCycle(JLabel curCycle) {
		this.curCycle = curCycle;
	}

	public JButton getNextcyclebutton() {
		return nextcyclebutton;
	}

	public void setNextcyclebutton(JButton nextcyclebutton) {
		this.nextcyclebutton = nextcyclebutton;
	}

	public JLabel getCasualties() {
		return casualties;
	}

	public void setCasualties(JLabel casualties) {
		this.casualties = casualties;
	}


	public ArrayList<JLabel> getUnitsPages() {
		return UnitsPages;
	}

	public JButton getNextUnitsPage() {
		return nextUnitsPage;
	}

	public JButton getPrevUnitsPage() {
		return prevUnitsPage;
	}

	public int getPage() {
		return Page;
	}

	public ArrayList<JLabel> getComponentsPages() {
		return ComponentsPages;
	}

	public ArrayList<JLabel> getSecondaryComponentsPages() {
		return secondaryComponentsPages;
	}

	public JButton getNextComponentsPage() {
		return nextComponentsPage;
	}

	public JButton getPrevComponentsPage() {
		return prevComponentsPage;
	}

	public JButton getNextSecComponentsPage() {
		return nextSecComponentsPage;
	}

	public JButton getPrevSecComponentsPage() {
		return prevSecComponentsPage;
	}

	public ArrayList<SimulatableAndButton> getsAndB() {
		return sAndB;
	}

	public ArrayList<SimulatableAndButton> getSecondarySandB() {
		return secondarySandB;
	}

	public int getComponentsPage() {
		return componentsPage;
	}

	public int getSecondaryComponentsPage() {
		return secondaryComponentsPage;
	}

	public JButton getRespondbutton() {
		return respondbutton;
	}
}
