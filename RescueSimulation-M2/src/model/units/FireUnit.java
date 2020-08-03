package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;


public abstract class FireUnit extends Unit {

	public FireUnit(String unitID, Address location, int stepsPerCycle,WorldListener worldListener) {
		super(unitID, location, stepsPerCycle,worldListener);
	}
	
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		if(r instanceof Citizen)
			throw new IncompatibleTargetException(this,r,"the target must be a ResidentialBuilding");
		else if(!canTreat(r))
			throw new CannotTreatException(this, r, "cannot treat");
		else
			super.respond(r);
	}
}
