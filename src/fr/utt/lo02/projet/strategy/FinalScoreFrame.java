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

public class FinalScoreFrame extends JPanel {
	
	private Image backgroundImage;
	public static Font font = null;

	public FinalScoreFrame() throws IOException, FontFormatException {
		backgroundImage = ImageIO.read(new File("res/background.png"));
		Dimension preferredSize = new Dimension(1280, 720);
		setPreferredSize(preferredSize);
        setBounds(0, 0, 1280, 720);
        setBackground(Color.DARK_GRAY);
        this.setLayout(null);
        JLabel title = new JLabel("<html><font color = #6699FF >F</font><font color = #66CC66 >I</font><font color = #CC3333 >N</font><font color = #6699FF >A</font><font color = #66CC66 >L S</font><font color = #CC3333 >C</font><font color = #6699FF >O</font><font color = #66CC66 >R</font><font color = #CC3333 >E</font><font color = #6699FF >S</font></html>");
        JLabel players = new JLabel("<html><font color = #6699FF >PLAYER 1 ::::: </font><font color = #66CC66>PLAYER 2 ::::: </font><font color = #CC3333>PLAYER 3</font></html>");
        JLabel round1 = new JLabel("<html><font color = #CCCC99 >ROUND 1 :::::::: </font><font color = #6699FF>24 :::::::::::::::::: </font><font color = #66CC66>34 :::::::::::::::::: </font><font color = #CC3333>18</font></html>");
        JLabel round2 = new JLabel("<html><font color = #CCCC99 >ROUND 2 :::::::: </font><font color = #6699FF>22 :::::::::::::::::: </font><font color = #66CC66>16 :::::::::::::::::: </font><font color = #CC3333>39</font></html>");
        JLabel round3 = new JLabel("<html><font color = #CCCC99 >ROUND 3 :::::::: </font><font color = #6699FF>15 :::::::::::::::::: </font><font color = #66CC66>40 :::::::::::::::::: </font><font color = #CC3333>28</font></html>");
        JLabel round4 = new JLabel("<html><font color = #CCCC99 >ROUND 4 :::::::: </font><font color = #6699FF>10 :::::::::::::::::: </font><font color = #66CC66>06 :::::::::::::::::: </font><font color = #CC3333>12</font></html>");
        JLabel total = new JLabel("<html><font color = #CCCC99 >TOTAL ::: :::::::: </font><font color = #6699FF>71 :::::::::::::::::: </font><font color = #66CC66>96 :::::::::::::::::: </font><font color = #CC3333>97</font></html>");
        JLabel wonTheGame = new JLabel("<html><font color = #CC3333 >PLAYER 3</font><font color = #CCCC99> WON THE GAME !</font></html>");
        try {
            if (font == null) {
                font = AddFont.createFont();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Font paintTitle = new Font(font.getFontName(),Font.PLAIN,100);
        Font paintBoard = new Font(font.getFontName(),Font.PLAIN,50);
        Font paintInfos = new Font(font.getFontName(),Font.PLAIN,60);
        Font buttonFont = new Font("Arial", Font.BOLD, 15);
        title.setBounds(340, 70, 600, 140);
        players.setBounds(300, 220, 1000, 70);
        round1.setBounds(50, 300, 1000, 70);
        round2.setBounds(50, 360, 1000, 70);
        round3.setBounds(50, 420, 1000, 70);
        round4.setBounds(50, 480, 1000, 70);
        total.setBounds(50, 540, 1000, 70);
        wonTheGame.setBounds(260, 630, 800, 70);
        
        title.setFont(paintTitle);
        players.setFont(paintBoard);
        round1.setFont(paintBoard);
        round2.setFont(paintBoard);
        round3.setFont(paintBoard);
        round4.setFont(paintBoard);
        total.setFont(paintBoard);
        wonTheGame.setFont(paintInfos);
        title.setVisible(true);
        players.setVisible(true);
        round1.setVisible(true);
        round2.setVisible(true);
        round3.setVisible(true);
        round4.setVisible(true);
        total.setVisible(true);
        wonTheGame.setVisible(true);
        this.add(title);
        this.add(players);
        this.add(round1);
        this.add(round2);
        this.add(round3);
        this.add(round4);
        this.add(total);
        this.add(wonTheGame);
        MyButton button = new MyButton("", "res/buttons/next.png", "res/buttons/circleNext.png", "res/buttons/nextPressed.png");
        button.setBounds(1075, 525, 150, 200);
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
	
	public static void main(String[] args) throws IOException, FontFormatException {
		FinalScoreFrame view = new FinalScoreFrame();


		JFrame frame = new JFrame();

		frame.add(view);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    frame.setResizable(false);
	    frame.pack();
	    frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
