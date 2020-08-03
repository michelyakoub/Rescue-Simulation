package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;



import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import controller.CommandCenter;

@SuppressWarnings("serial")
public class EastPanel extends JPanel{
	private JPanel north;
	private JPanel center;
	private JPanel south;
	private JLabel text1;
	public ArrayList<UnitButton> uButtons;
	private JPanel units;
	private JLabel text2;
	public JTextArea disasters;
	public JPanel infoButtons;
	public ArrayList<HelperButton> hButtons;
	private JLabel text3;
	private JScrollPane infoScrollPanel;
	private JScrollPane disasterScrollPanel;
	private JScrollPane buttonsScrollPanel;
	public JTextArea info;
	public ActionListener control;
	private JPanel centernorth;
	public EastPanel(ActionListener control){
		hButtons = new ArrayList<HelperButton>();
		uButtons = new ArrayList<UnitButton>();
		this.control= control;
		setLayout(new GridLayout(3,1));
		setVisible(true);
		setPreferredSize(new Dimension(400,1000));
		
		north = new JPanel();
		north.setLayout(new BorderLayout());
		north.setPreferredSize(new Dimension(400, 500));
		
		text1 = new JLabel("AvailableUnits");
		north.add(text1,BorderLayout.NORTH);
		
		units = new JPanel();
		units.setLayout(new FlowLayout());
		units.setBackground(Color.DARK_GRAY);

		ArrayList<Unit> emergencyUnits  = ((CommandCenter)control).engine.getEmergencyUnits();
		for(int i=0;i<emergencyUnits.size();i++){
			UnitButton ub =new UnitButton(emergencyUnits.get(i),control);
			if(emergencyUnits.get(i) instanceof Ambulance)
				ub.setIcon(new EasyImage("ambulance.jpg",new Dimension(80,50)));
			if(emergencyUnits.get(i) instanceof DiseaseControlUnit)
				ub.setIcon(new EasyImage("dcu.jpg",new Dimension(80,50)));
			if(emergencyUnits.get(i) instanceof Evacuator)
				ub.setIcon(new EasyImage("evacuator.jpg",new Dimension(80,50)));
			if(emergencyUnits.get(i) instanceof FireTruck)
				ub.setIcon(new EasyImage("firetruck.jpg",new Dimension(80,50)));
			if(emergencyUnits.get(i) instanceof GasControlUnit)
				ub.setIcon(new EasyImage("gas.jpg",new Dimension(80,50)));
			ub.setToolTipText(""+ub.unit.getState());
			uButtons.add(ub);
			units.add(ub);		
		}
		//unitScrollPanel = new JScrollPane(units,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		north.add(units,BorderLayout.CENTER);
		
		add(north);
		
		center = new JPanel();
		center.setLayout(new GridLayout(2,1));
		
		centernorth =new JPanel();
		centernorth.setLayout(new BorderLayout());
		
		text2 = new JLabel("Log");
		centernorth.add(text2,BorderLayout.NORTH);
		
		
		disasters = new JTextArea();
		disasters.setEditable(false);
		disasters.setBackground(Color.DARK_GRAY);
		disasters.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		disasters.setForeground(Color.RED);
		disasterScrollPanel = new JScrollPane(disasters,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		centernorth.add(disasterScrollPanel,BorderLayout.CENTER);
		
		center.add(centernorth);
		
		infoButtons = new JPanel();
		infoButtons.setLayout(new FlowLayout());
		infoButtons.setBackground(Color.darkGray);
		buttonsScrollPanel = new JScrollPane(infoButtons,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		center.add(buttonsScrollPanel);
		
		add(center);
		
		south = new JPanel();
		south.setLayout(new BorderLayout());
		
		text3 = new JLabel("Informations");
		south.add(text3,BorderLayout.NORTH);
		
		info = new JTextArea();
		info.setEditable(false);
		info.setBackground(Color.darkGray);
		info.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		info.setForeground(Color.YELLOW);
		infoScrollPanel = new JScrollPane(info,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		south.add(infoScrollPanel,BorderLayout.CENTER);
		
		add(south);
	}
	
}
