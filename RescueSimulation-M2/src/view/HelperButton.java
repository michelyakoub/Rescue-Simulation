package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.disasters.Infection;
import model.disasters.Injury;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import simulation.Simulatable;

@SuppressWarnings("serial")
public class HelperButton extends JButton{
	public Simulatable sim;
	public ActionListener control;
public HelperButton (Simulatable sim,ActionListener control){
	this.sim=sim;
	this.control=control; 
	addActionListener(control);
}
public void addImage(){
		if(sim instanceof Citizen){
			if(((Citizen) sim).getState() == CitizenState.DECEASED)
				setIcon(new EasyImage("RIP.jpg",new Dimension(100,100)));
			else if(((Citizen) sim).getDisaster() instanceof Injury)
				setIcon(new EasyImage("c"+(Integer.parseInt(((Citizen)sim).getNationalID())%8)+"i.png",new Dimension(120,80)));
			else if(((Citizen) sim).getDisaster() instanceof Infection)
				setIcon(new EasyImage("c"+(Integer.parseInt(((Citizen)sim).getNationalID())%8)+"t.png",new Dimension(120,80)));
			else setIcon(new EasyImage("c"+(Integer.parseInt(((Citizen)sim).getNationalID())%8)+".jpg",new Dimension(120,85)));
			setToolTipText("HP = "+((Citizen)sim).getHp());
		}
	else if(sim instanceof Unit){
		if(sim instanceof Ambulance)
			setIcon(new EasyImage("ambulance.jpg",new Dimension(80,50)));
		if(sim instanceof DiseaseControlUnit)
			setIcon(new EasyImage("dcu.jpg",new Dimension(80,50)));
		if(sim  instanceof Evacuator)
			setIcon(new EasyImage("evacuator.jpg",new Dimension(80,50)));
		if(sim  instanceof FireTruck)
			setIcon(new EasyImage("firetruck.jpg",new Dimension(80,50)));
		if(sim instanceof GasControlUnit)
			setIcon(new EasyImage("gas.jpg",new Dimension(80,50)));
		setToolTipText(""+((Unit)sim).getState());
		
	}
}
	}

