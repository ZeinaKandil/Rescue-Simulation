package view;

import javax.swing.JButton;

import simulation.Rescuable;

public class RescuableButton extends JButton{

	private Rescuable rescuable;
	private int x;
	private int y;
	
	public RescuableButton(Rescuable r, int x, int y) {
		super();
		rescuable = r;
		this.x = x;
		this.y = y;
	}
	public RescuableButton(){
		super();
	}

	public Rescuable getRescuable() {
		return rescuable;
	}

	public void setRescuable(Rescuable rescuable) {
		this.rescuable = rescuable;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	

}
