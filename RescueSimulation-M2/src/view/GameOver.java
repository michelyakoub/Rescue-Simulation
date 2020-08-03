package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameOver extends JFrame{
	public JPanel p;
	public JLabel l;
	public GameOver(ActionListener control){
		p =new JPanel() {
			
		public void paintComponent(Graphics g) {

			ImageIcon i = new ImageIcon("src/images/gameover.png");
			Image p =i.getImage().getScaledInstance(1200, 1000, Image.SCALE_SMOOTH);
			i.setImage(p);
			g.drawImage(p, 0, 0, getWidth(), getHeight(), this);
		}
	};
		l =new JLabel();
		setSize(1600, 1000);
		setLocation(120, 25);
		setVisible(false);
		setResizable(false);
		l.setFont(new Font(Font.MONOSPACED, Font.BOLD, 60));
		l.setForeground(Color.WHITE);
		p.add(l);
		add(p);
	    repaint();
	    revalidate();
	}

}
