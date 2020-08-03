package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class NorthPanel extends JPanel{
	public JPanel center;
	public JPanel west;
	public JButton nextCycle;
	public JLabel text1;
	public JLabel text2;
	public JTextArea cycleNum;
	public JTextArea casualitiesNum;
	public ActionListener control;

	
	public NorthPanel(ActionListener control){
		this.control = control;
		setPreferredSize(new Dimension(100,50));
		setLayout(new GridLayout(1,3));
		setVisible(true);
		
		west = new JPanel();
		west.setLayout(new BorderLayout());
		
		text1 = new JLabel("NUM. of casualities : ");
		west.add(text1,BorderLayout.WEST);
		
		casualitiesNum = new JTextArea();
		casualitiesNum.setEditable(false);
		casualitiesNum.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
		casualitiesNum.setText("0");
		west.add(casualitiesNum,BorderLayout.CENTER);
		
		add(west);
		
		center = new JPanel();
		center.setLayout(new BorderLayout());
		
		text2 = new JLabel("cycle num : ");
		center.add(text2,BorderLayout.WEST);
		
		cycleNum = new JTextArea();
		cycleNum.setEditable(false);
		cycleNum.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
		cycleNum.setText("0");
		center.add(cycleNum,BorderLayout.CENTER);
		
		add(center);
		
		nextCycle = new JButton("NEXT CYCLE");
		nextCycle.addActionListener(control);
		add(nextCycle);

	}
}
