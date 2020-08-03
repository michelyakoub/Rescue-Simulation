package view;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class EasyImage extends ImageIcon {

	public EasyImage(String filename, Dimension dimension) {
		super("src/images/" + filename);
		setImage(getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH));
	}

}
