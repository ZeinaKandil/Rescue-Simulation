package view;

import javax.swing.JButton;

import simulation.Simulatable;

public class SimulatableAndButton {
	Simulatable s;
	JButton b;
	public SimulatableAndButton(Simulatable s, JButton b){
		this.b = b;
		this.s = s;
		
	}
	public Simulatable getS() {
		return s;
	}
	public JButton getB() {
		return b;
	}
	

}
