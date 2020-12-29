package fr.utt.lo02.projet.strategy;



import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ScoreFrameTest extends JPanel {
	
	private Image backgroundImage;
	public static Font font = null;
	
	public ScoreFrameTest() throws IOException, FontFormatException {
		
		backgroundImage = ImageIO.read(new File("res/background.png"));
		Dimension preferredSize = new Dimension(1280, 720);
		setPreferredSize(preferredSize);
        setBounds(0, 0, 1280, 720);
        setBackground(Color.DARK_GRAY);
        this.setLayout(null);
        JLabel title = new JLabel("<html><font color = #6699FF >S</font><font color = #66CC66 >C</font><font color = #CC3333 >O</font><font color = #6699FF >R</font><font color = #66CC66 >E</font><font color = #CC3333 >S</font></html>");
        JLabel player1 = new JLabel("<html><font color = #6699FF >PLAYER 1 :::::::::::: 12</font></html>");
        JLabel player2 = new JLabel("<html><font color = #66CC66 >PLAYER 2 :::::::::::: 24</font></html>");
        JLabel player3 = new JLabel("<html><font color = #CC3333 >PLAYER 3 :::::::::::: 18</font></html>");
        JLabel wonThisRound = new JLabel("<html><font color = #66CC66 >PLAYER 2</font><font color = #CCCC99> WON THIS ROUND !</font></html>");
        try {
            if (font == null) {
                font = AddFont.createFont();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Font paintTitle = new Font(font.getFontName(),Font.PLAIN,120);
        Font paintInfos = new Font(font.getFontName(),Font.PLAIN,60);
        Font buttonFont = new Font("Arial", Font.BOLD, 15);
        title.setBounds(470, 90, 340, 140);
        player1.setBounds(350, 270, 680, 70);
        player2.setBounds(350, 370, 680, 70);
        player3.setBounds(350, 470, 680, 70);
        wonThisRound.setBounds(260, 610, 800, 70);
        title.setFont(paintTitle);
        player1.setFont(paintInfos);
        player2.setFont(paintInfos);
        player3.setFont(paintInfos);
        wonThisRound.setFont(paintInfos);
        title.setVisible(true);
        player1.setVisible(true);
        player2.setVisible(true);
        player3.setVisible(true);
        wonThisRound.setVisible(true);
        this.add(title);
        this.add(player1);
        this.add(player2);
        this.add(player3);
        this.add(wonThisRound);
        MyButton button = new MyButton("", "res/buttons/next.png", "res/buttons/circleNext.png", "res/buttons/nextPressed.png");
        button.setBounds(1050, 285, 150, 200);
        button.setVisible(true);
        button.setFont(buttonFont);
        this.add(button);
        this.revalidate();
        this.repaint();
	}
	
	public void paintComponent(Graphics g) {
	       super.paintComponent(g);

	       // Draw the background image.
	       g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	     }

public static void main(String[] args) throws IOException, FontFormatException
    {
		ScoreFrameTest view = new ScoreFrameTest();


		JFrame frame = new JFrame();

		frame.add(view);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    frame.setResizable(false);
	    frame.pack();
	    frame.setLocationRelativeTo(null);
		frame.setVisible(true);
    }
}
