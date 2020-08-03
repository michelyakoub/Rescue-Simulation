package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import controller.CommandCenter;

@SuppressWarnings("serial")
public class TheBoss extends JFrame{
	public Map map;
	public EastPanel eastPanel;
	public  NorthPanel northPanel;
	public ActionListener control;
	public TheBoss(ActionListener control) throws Exception{
		this.control = control;
		map = new Map(control);
		eastPanel = new EastPanel(control);
		northPanel = new NorthPanel(control);
		
		setSize(1600, 1000);
		setLocation(120, 25);
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setVisible(true);
		setResizable(false);
		setTitle("Rescue Simulation");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(map,BorderLayout.CENTER);
		add(northPanel,BorderLayout.NORTH);
		add(eastPanel,BorderLayout.EAST);
	}
	public static void main(String[]args) throws Exception{
		new CommandCenter();
	}
}
