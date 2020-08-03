package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import simulation.Address;

@SuppressWarnings("serial")
public class Map extends JPanel{
	public JButton[][] buttons;
	public ActionListener control;
	public Map(ActionListener control){
		this.control = control;
		buttons = new JButton[10][10];
		setPreferredSize(new Dimension(1000,800));
		setLayout(new GridLayout(10,10));
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				CellButton c = new CellButton(new Address(i,j),control);
				buttons[i][j] = c;
				c.setBackground(Color.WHITE);
				c.addImage();
			}
		}
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				add(buttons[i][j]);
			}
		}
		setVisible(true);	
	}
}
