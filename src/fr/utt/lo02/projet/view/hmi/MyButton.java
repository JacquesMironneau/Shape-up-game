package fr.utt.lo02.projet.view.hmi;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.Objects;

/**
 * Custom button, used to handle hover and pressed image in an easier way
 */
public class MyButton extends JButton {
		 
	    @Serial
		private static final long serialVersionUID = 1L;
	 
	    public MyButton(String txt, String icon, String iconHover, String iconPressed) {
	        super(txt);
	        setForeground(Color.WHITE);
	         
	        setOpaque(false);
	        setContentAreaFilled(false); // Prevent the component to paint the interior of the button
	        setBorderPainted(false); // prevent the component to paint the border.
	        setFocusPainted(false); // don't display the focus effect
	         
	        setHorizontalAlignment(SwingConstants.CENTER);
	        setHorizontalTextPosition(SwingConstants.CENTER);
	        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

	        ImageIcon imgIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(icon)));
	        ImageIcon imgIconHover = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(iconHover)));
	        ImageIcon imgIconPressed = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(iconPressed)));
	        setIcon(imgIcon);
	        setRolloverIcon(imgIconHover);
	        this.setPressedIcon(imgIconPressed);
	    }
}
