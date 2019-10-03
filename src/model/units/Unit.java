package model.units;

import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;
import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;

public abstract class Unit implements Simulatable, SOSResponder {
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public void setWorldListener(WorldListener listener) {
		this.worldListener = listener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	@Override
	public void respond(Rescuable r) throws CannotTreatException,
			IncompatibleTargetException {
		if (!canTreat(r)) {
			throw new CannotTreatException(this, r,
					"Use your head! This unit cannot treat this disaster. Choose another unit.");
		}
		if (incompatible(r))
			throw new IncompatibleTargetException(this, r,
					"This Unit is incompatible with this target.");
		if (target != null && state == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);

	}

	public void reactivateDisaster() {
		Disaster curr = target.getDisaster();
		curr.setActive(true);
	}

	public void finishRespond(Rescuable r) {
		target = r;
		state = UnitState.RESPONDING;
		Address t = r.getLocation();
		distanceToTarget = Math.abs(t.getX() - location.getX())
				+ Math.abs(t.getY() - location.getY());

	}

	public abstract void treat();

	public void cycleStep() {
		if (state == UnitState.IDLE)
			return;
		if (distanceToTarget > 0) {
			distanceToTarget = distanceToTarget - stepsPerCycle;
			if (distanceToTarget <= 0) {
				distanceToTarget = 0;
				Address t = target.getLocation();
				worldListener.assignAddress(this, t.getX(), t.getY());
			}
		} else {
			state = UnitState.TREATING;
			treat();
		}
	}

	public void jobsDone() {
		target = null;
		state = UnitState.IDLE;

	}

	public boolean canTreat(Rescuable r) {
		if (r instanceof Citizen) {
			Citizen c = (Citizen) r;
			if (this instanceof Ambulance
					&& !(c.getDisaster() instanceof Injury))
				return false;
			if (this instanceof DiseaseControlUnit
					&& !(c.getDisaster() instanceof Infection))
				return false;
			/*
			 * if(c.getState() == CitizenState.SAFE) return false;
			 */
			if (c.getBloodLoss() > 0 || c.getToxicity() > 0)
				return true;
		}
		if (r instanceof ResidentialBuilding) {
			ResidentialBuilding rb = (ResidentialBuilding) r;
			if (this instanceof GasControlUnit
					&& !(r.getDisaster() instanceof GasLeak))
				return false;
			if (this instanceof Evacuator
					&& !(r.getDisaster() instanceof Collapse))
				return false;
			if (this instanceof FireTruck && !(r.getDisaster() instanceof Fire))
				return false;
			if (rb.getFireDamage() > 0
					|| rb.getGasLevel() > 0
					|| (rb.getFoundationDamage() > 0 && rb.getOccupants()
							.size() > 0))
				return true;
		}
		return false;
	}

	public boolean incompatible(Rescuable r) {
		if (r instanceof ResidentialBuilding) {
			ResidentialBuilding rb = (ResidentialBuilding) r;
			if (this instanceof FireTruck && rb.getDisaster() instanceof Fire)
				return false;
			if (this instanceof Evacuator
					&& rb.getDisaster() instanceof Collapse)
				return false;
			if (this instanceof GasControlUnit
					&& rb.getDisaster() instanceof GasLeak)
				return false;
		}
		if (r instanceof Citizen) {
			Citizen c = (Citizen) r;
			if (c.getDisaster() instanceof Infection
					&& this instanceof DiseaseControlUnit)
				return false;
			if (c.getDisaster() instanceof Injury && this instanceof Ambulance)
				return false;
		}
		return true;
	}

	public int getDistanceToTarget() {
		return distanceToTarget;
	}
}
