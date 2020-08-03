package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;


import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;
import view.CellButton;
import view.EasyImage;
import view.GameOver;
import view.HelperButton;
import view.TheBoss;
import view.UnitButton;

public class CommandCenter implements SOSListener, ActionListener {

	public Simulator engine;
	public  ArrayList<ResidentialBuilding> visibleBuildings;
	public ArrayList<Citizen> visibleCitizens;
	public Unit unit = null;

	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;
	private TheBoss theboss;
	public GameOver gameover;

	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		theboss = new TheBoss(this);
		gameover = new GameOver(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
	}

	@Override
	public void receiveSOSCall(Rescuable r) {
		
		if (r instanceof ResidentialBuilding) {
			
			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);
			
		} else {
			
			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof CellButton){
			CellButton cb = (CellButton)e.getSource();
				try {
					if(this.unit != null && cb.res != null){
						this.unit.respond(cb.res);
						this.unit = null;
						theboss.eastPanel.infoButtons.removeAll();
						theboss.eastPanel.hButtons.clear();
						return;
					}
				} catch (IncompatibleTargetException e1) {
					JOptionPane.showMessageDialog(theboss,e1.getMessage());
					this.unit = null;
					return;
				} catch (CannotTreatException e1) {
					JOptionPane.showMessageDialog(theboss,e1.getMessage());
					this.unit = null;
					return;
				}
				if(cb.address.getX()==0&&cb.address.getY()==0){
					theboss.eastPanel.infoButtons.removeAll();
					theboss.eastPanel.hButtons.clear();
					this.unit = null;
					for(int i =0;i<engine.emergencyUnits.size();i++){
						if(engine.emergencyUnits.get(i).getLocation().getX()==0 && engine.emergencyUnits.get(i).getLocation().getY()==0){
							HelperButton x = new HelperButton(engine.emergencyUnits.get(i),this);
							x.addImage();
							x.setBackground(Color.WHITE);
							theboss.eastPanel.infoButtons.add(x);
							theboss.eastPanel.hButtons.add(x);
						}
					}
					for(int i =0;i<engine.citizens.size();i++){
						if(engine.citizens.get(i).getLocation().getX()==0 && engine.citizens.get(i).getLocation().getY()==0){
							HelperButton x = new HelperButton(engine.citizens.get(i),this);
							x.addImage();
							x.setBackground(Color.WHITE);
							theboss.eastPanel.infoButtons.add(x);
							theboss.eastPanel.hButtons.add(x);
						}
					}
					theboss.revalidate();
					theboss.repaint();
					return;
				}
				if(cb.res instanceof Citizen){
					theboss.eastPanel.infoButtons.removeAll();
					theboss.eastPanel.hButtons.clear();
					theboss.revalidate();
					theboss.repaint();
					this.unit = null;
					Citizen c = (Citizen)cb.res;
					theboss.eastPanel.info.setText(citizenString(c));
					return;
				}
				if(cb.res instanceof ResidentialBuilding){
					theboss.eastPanel.infoButtons.removeAll();
					theboss.eastPanel.hButtons.clear();
					this.unit = null;
					ResidentialBuilding b = (ResidentialBuilding)cb.res;
					theboss.eastPanel.info.setText(buildingString(b));
					for(int i=0;i<b.getOccupants().size();i++){
						HelperButton x = new HelperButton(b.getOccupants().get(i),this);
						x.addImage();
						x.setBackground(Color.WHITE);
						theboss.eastPanel.infoButtons.add(x);
						theboss.eastPanel.hButtons.add(x);
					}
					theboss.revalidate();
					theboss.repaint();
					return;
				}
				theboss.eastPanel.infoButtons.removeAll();
				theboss.eastPanel.hButtons.clear();
				theboss.revalidate();
				theboss.repaint();
				this.unit = null;
				return;
			}
		if(e.getSource() instanceof UnitButton){
			UnitButton ub = (UnitButton)e.getSource();
			this.unit = ub.unit;
			if(ub.unit instanceof Ambulance){
				String s = "AMBULANCE\n";
				s += unitString(ub.unit);
				theboss.eastPanel.info.setText(s);
				return;
			}
			if(ub.unit instanceof DiseaseControlUnit){
				String s = "DISEASE CONTROL UNIT\n";
				s += unitString(ub.unit);
				theboss.eastPanel.info.setText(s);
				return;
			}
			if(ub.unit instanceof Evacuator){
				theboss.eastPanel.infoButtons.removeAll();
				theboss.eastPanel.hButtons.clear();
				theboss.revalidate();
				theboss.repaint();
				String s = "EVACUATOR\n";
				s += unitString(ub.unit);
				s += "Num. of Passengers = "+ ((Evacuator)ub.unit).getPassengers().size() +"\n";
				for(int i=0;i<((Evacuator)ub.unit).getPassengers().size();i++){
					HelperButton x = new HelperButton(((Evacuator)ub.unit).getPassengers().get(i),this);
					x.addImage();
					x.setBackground(Color.WHITE);
					theboss.eastPanel.infoButtons.add(x);
					theboss.eastPanel.hButtons.add(x);
					
					s+= "----------------------- \n"; 
					s+= "Passenger " + (i+1) + " : \n";
					s+=citizenString(((Evacuator)ub.unit).getPassengers().get(i));
					}
				theboss.eastPanel.info.setText(s);
				theboss.revalidate();
				theboss.repaint();
				return;
			}
			if(ub.unit instanceof FireTruck){
				theboss.eastPanel.infoButtons.removeAll();
				theboss.eastPanel.hButtons.clear();
				theboss.revalidate();
				theboss.repaint();
				String s = "FIRE TRUCK\n";
				s += unitString(ub.unit);
				theboss.eastPanel.info.setText(s);
				return;
			}
			if(ub.unit instanceof GasControlUnit){
				theboss.eastPanel.infoButtons.removeAll();
				theboss.eastPanel.hButtons.clear();
				theboss.revalidate();
				theboss.repaint();
				String s = "GAS CONTROL UNIT\n";
				s += unitString(ub.unit);
				theboss.eastPanel.info.setText(s);
				return;
			}
		}
		if(e.getSource() instanceof HelperButton){
			HelperButton hb = (HelperButton)e.getSource();
			
			if(this.unit != null && hb.sim instanceof Citizen){
				try {
					this.unit.respond((Rescuable)hb.sim);
				} catch (IncompatibleTargetException e1) {
					JOptionPane.showMessageDialog(theboss,e1.getMessage());
					this.unit = null;
					return;
				} catch (CannotTreatException e1) {
					JOptionPane.showMessageDialog(theboss,e1.getMessage());
					this.unit = null;
					return;
				}
				this.unit = null;
				return;
			}
			if(hb.sim instanceof Citizen){
				Citizen c = (Citizen)hb.sim;
				theboss.eastPanel.info.setText(citizenString(c));
				this.unit = null;
				return;
				}
			
		}
		if(e.getSource() == theboss.northPanel.nextCycle){
			//theboss.eastPanel.infoButtons.removeAll();
			this.unit = null;
			try {
				engine.nextCycle();
			} catch (CitizenAlreadyDeadException e1) {
				JOptionPane.showMessageDialog(theboss,e1.getMessage());
			} catch (BuildingAlreadyCollapsedException e1) {
				JOptionPane.showMessageDialog(theboss,e1.getMessage());
			}
			
			//TO RESET INFO PANEL EVERY CYCLE	
				theboss.eastPanel.info.setText("");
				
			//TO SET CURRENT CYCLE NUM	
				theboss.northPanel.cycleNum.setText("" + engine.currentCycle);
			
			//TO SET DISASTERS PANEL TEXT	
				theboss.eastPanel.disasters.setText(theboss.eastPanel.disasters.getText()+disastersPanelString());
			
			//TO CALCULATE CASUALTIES EVERY CYCLE
				theboss.northPanel.casualitiesNum.setText(""+ engine.calculateCasualties());
			
			//TO CHANGE THE IMAGE WHEN DIE
				for(int i=0;i<engine.citizens.size();i++){
					if(engine.citizens.get(i).getState() == CitizenState.DECEASED && ((CellButton)theboss.map.buttons[engine.citizens.get(i).getLocation().getX()][engine.citizens.get(i).getLocation().getY()]).res instanceof Citizen)
						theboss.map.buttons[engine.citizens.get(i).getLocation().getX()][engine.citizens.get(i).getLocation().getY()].setIcon(new EasyImage("RIP.jpg",new Dimension(100,100)));
				}
				for(int j = 0;j<theboss.eastPanel.hButtons.size();j++){
					if(theboss.eastPanel.hButtons.get(j).sim instanceof Citizen && ((Citizen)theboss.eastPanel.hButtons.get(j).sim).getState() == CitizenState.DECEASED){
						theboss.eastPanel.hButtons.get(j).setIcon(new EasyImage("RIP.jpg",new Dimension(100,100)));
					}		
				}

				
			//TO CHANGE TOOLTIPTEXT EVERY CYCLE IN UNITS
				for(int i =0;i<theboss.eastPanel.uButtons.size();i++)
					theboss.eastPanel.uButtons.get(i).setToolTipText(""+theboss.eastPanel.uButtons.get(i).unit.getState());
			
			//TO CHANGE TOOLTIPTEXT EVERY CYCLE IN MAP
				for(int i=0;i<theboss.map.buttons.length;i++){
					for(int j=0;j<theboss.map.buttons[i].length;j++){
						CellButton cb =(CellButton)theboss.map.buttons[i][j];
						cb.addImage();
						if(cb.res instanceof ResidentialBuilding)
							cb.setToolTipText("Structural integrity = "+((ResidentialBuilding)cb.res).getStructuralIntegrity());
						if(cb.res instanceof Citizen)
							cb.setToolTipText("HP = "+((Citizen)cb.res).getHp());
					}
				}
			if(engine.checkGameOver() == true){
				theboss.setVisible(false);
				gameover.l.setText("Number of Casualties : "+ engine.calculateCasualties());
				gameover.setVisible(true);
			}
		}
	}
	public String disasterString(Disaster d){
		String s = "";
		if(d instanceof Infection)
			s = "Infection";
		else if(d instanceof Injury)
			s = "Injury"; 
		else if(d instanceof Fire)
			s = "Fire";
		else if(d instanceof GasLeak)
			s = "Gas Leak";
		else if(d instanceof Collapse)
			s = "Collapse";
		else 
			s = "Null";
		return s;
	}
	public String citizenString(Citizen c){
		String s = "Name : "+ c.getName() + "\n";
		s+= "Age : "+c.getAge()+"\n";
		s+= "NationalID : "+c.getNationalID()+"\n";
		s+= "Location : (" + c.getLocation().getX()+","+c.getLocation().getY()+")\n";
		s+= "HP = " + c.getHp()+"\n";
		s+= "BloodLoss = " + c.getBloodLoss() +"\n";
		s+= "Toxicity = " + c.getToxicity() + "\n";
		s+= "State : "+ c.getState()+"\n";
		s+= "Disaster : "+disasterString(c.getDisaster())+"\n";
		return s;
	}
	public String buildingString(ResidentialBuilding b){
		String s = "Location : (" + b.getLocation().getX()+","+b.getLocation().getY()+")\n";
		s+= "Structural Integerity = " + b.getStructuralIntegrity() + "\n";
		s+= "Fire Damage = " + b.getFireDamage() + "\n";
		s+= "Gas Level = "+ b.getGasLevel() + "\n";
		s+= "Foundation Damage = " + b.getFoundationDamage() + "\n";
		s+= "Disaster : "+disasterString(b.getDisaster())+"\n";
		s+= "Num. of Occupants = " + b.getOccupants().size() + "\n";
		for(int i=0;i<b.getOccupants().size();i++){
			s+= "----------------------- \n"; 
			s+= "Occupant " + (i+1) + " : \n";
			s+=citizenString(b.getOccupants().get(i));
			}
		return s;	
	}
	public String unitString(Unit u){
		String s = "ID : " + u.getUnitID() + "\n";
		s+= "Location : (" + u.getLocation().getX()+","+u.getLocation().getY()+")\n";
		s+= "Steps per Cycle = " + u.getStepsPerCycle()  +"\n";
		s+= "State : "+u.getState()+"\n";
		if(u.getTarget() instanceof Citizen)
			s+= "Tareget : Citizen " + ((Citizen)u.getTarget()).getName()+"\n";
		else if(u.getTarget() instanceof ResidentialBuilding)
			s+= "Tareget : ResidentialBuilding (" + u.getTarget().getLocation().getX()+","+u.getTarget().getLocation().getY()+")\n";
		else
			s+= "Target : Null\n";
		return s;
	}
	public String disastersPanelString(){
		String s="";
		for(int i =0;i<engine.executedDisasters.size();i++){
			Disaster d = (Disaster)engine.executedDisasters.get(i);
			if(d instanceof Infection&& d.getStartCycle()==engine.currentCycle)
				s += "The citizen "+ ((Citizen)d.getTarget()).getName() +" in ("+ ((Citizen)d.getTarget()).getLocation().getX()+","+((Citizen)d.getTarget()).getLocation().getY() + ") has been  infected in cycle " + d.getStartCycle() +"\n"; 
			if(d instanceof Injury && d.getStartCycle()==engine.currentCycle)
				s += "The citizen "+ ((Citizen)d.getTarget()).getName() +" in ("+ ((Citizen)d.getTarget()).getLocation().getX()+","+((Citizen)d.getTarget()).getLocation().getY() + ") has been injured in cycle " + d.getStartCycle() +"\n"; 
			if(d instanceof Fire && d.getStartCycle()==engine.currentCycle)
				s += "The building in ("+ ((ResidentialBuilding)d.getTarget()).getLocation().getX()+","+((ResidentialBuilding)d.getTarget()).getLocation().getY() + ") got struck by Fire in cycle " + d.getStartCycle() +"\n"; 
			if(d instanceof GasLeak && d.getStartCycle()==engine.currentCycle)
				s += "The building in ("+ ((ResidentialBuilding)d.getTarget()).getLocation().getX()+","+((ResidentialBuilding)d.getTarget()).getLocation().getY() + ") got struck by GasLeak in cycle " + d.getStartCycle() +"\n"; 
			if(d instanceof Collapse && d.getStartCycle()==engine.currentCycle)
				s += "The building in ("+ ((ResidentialBuilding)d.getTarget()).getLocation().getX()+","+((ResidentialBuilding)d.getTarget()).getLocation().getY() + ") got struck by Collapse in cycle " + d.getStartCycle() +"\n"; 	
		}
		for (int i = 0;i<engine.citizens.size();i++){
			if(engine.citizens.get(i).getState() == CitizenState.DECEASED && !engine.citizens.get(i).dead){
				s+= "The citizen "+ engine.citizens.get(i).getName() + " has been died in cycle " + engine.currentCycle + "\n";
				engine.citizens.get(i).dead = true;
			}
		}
		return s;
	}
	
}
