package view;


import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import controller.CommandCenter;
import model.disasters.Collapse;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Unit;
import simulation.Address;
import simulation.Rescuable;

@SuppressWarnings("serial")
public class CellButton extends JButton{
	public Address address;
	public Rescuable res;
	public ArrayList<Unit> units;
	public ActionListener control;
	public CellButton(Address address,ActionListener control){
		this.control=control;
		this.address = address;
		this.res = ((CommandCenter)control).engine.getRescuableByAddress(address.getX(),address.getY());
		addActionListener(control);
	}
	public void addImage(){
		if(address.getX()==0&&address.getY()==0)
			setText("BASE");
		else if(res instanceof ResidentialBuilding){
			if(((ResidentialBuilding) res).getStructuralIntegrity() == 0)
				setIcon(new EasyImage("collapse.jpg",new Dimension(120,90)));
			else if(res.getDisaster() instanceof Fire && res.getDisaster().isActive())
				setIcon(new EasyImage("b"+((res.getLocation().getX()+res.getLocation().getY())%5)+"f.JPG",new Dimension(120,80)));
			else if(res.getDisaster() instanceof GasLeak && res.getDisaster().isActive())
				setIcon(new EasyImage("b"+((res.getLocation().getX()+res.getLocation().getY())%5)+"g.JPG",new Dimension(120,80)));
			else if(res.getDisaster() instanceof Collapse && res.getDisaster().isActive())
				setIcon(new EasyImage("b"+((res.getLocation().getX()+res.getLocation().getY())%5)+"c.PNG",new Dimension(120,80)));
			else setIcon(new EasyImage("b"+((res.getLocation().getX()+res.getLocation().getY())%5)+".jpg",new Dimension(120,80)));
			setToolTipText("Structural integrity = "+((ResidentialBuilding)res).getStructuralIntegrity());
		}
		else if(res instanceof Citizen){
			if(((Citizen) res).getState() == CitizenState.DECEASED)
				setIcon(new EasyImage("RIP.jpg",new Dimension(100,100)));
			else if(res.getDisaster() instanceof Injury && res.getDisaster().isActive())
				setIcon(new EasyImage("c"+(Integer.parseInt(((Citizen)res).getNationalID())%8)+"i.png",new Dimension(120,80)));
			else if(res.getDisaster() instanceof Infection && res.getDisaster().isActive())
				setIcon(new EasyImage("c"+(Integer.parseInt(((Citizen)res).getNationalID())%8)+"t.png",new Dimension(120,80)));
			else setIcon(new EasyImage("c"+(Integer.parseInt(((Citizen)res).getNationalID())%8)+".jpg",new Dimension(120,85)));
			setToolTipText("HP = "+((Citizen)res).getHp());
		}
	}	

}
