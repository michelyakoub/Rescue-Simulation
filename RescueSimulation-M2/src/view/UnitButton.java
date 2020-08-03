package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.units.Unit;

@SuppressWarnings("serial")
public class UnitButton extends JButton{
	public Unit unit;
	public UnitButton(Unit unit,ActionListener control){
		this.unit = unit;
		addActionListener(control);
	}
}
