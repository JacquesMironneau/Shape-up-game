package fr.utt.lo02.projet.strategy;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InitFrameView extends JPanel implements InitView {

	private Image backgroundImage;
	
	private InitController controller;
	private static Font font = null;
	public static final String EASY = "easy";
	public static final String MEDIUM = "medium";
	
	private static JLabel instr;
	private static JLabel titlePage;
	private static JLabel code;
	private static JLabel graphics;
	private static JLabel music;
	private static JLabel rulesImg;
	private static JLabel real;
	private static JLabel virtual;
	private static JLabel nbReal;
	private static JLabel nbVirtual;
	private static JLabel enterNames;
	private static JLabel enterDifficulties;
	private static JLabel p1;
	private static JLabel p2;
	private static JLabel p3;
	private static JLabel comp1;
	private static JLabel comp2;
	private static JLabel comp3;
	
	private static JTextField name1;
	private static JTextField name2;
	private static JTextField name3;
	
	JCheckBox comp1Easy;
	JCheckBox comp1Difficult;
	JCheckBox comp2Easy;
	JCheckBox comp2Difficult;
	JCheckBox comp3Easy;
	JCheckBox comp3Difficult;
	
	private static MyButton play;
	private static MyButton rules;
	private static MyButton credits;
	private static MyButton quit;
	private static MyButton normal;
	private static MyButton advanced;
	private static MyButton noAdjacency;
	private static MyButton backStartMenu;
	private static MyButton normalCalculator;
	private static MyButton bonusCalculator;
	private static MyButton backGMChoice;
	private static MyButton rectangle;
	private static MyButton triangle;
	private static MyButton circle;
	private static MyButton backSCChoice;
	private static MyButton add;
	private static MyButton minus;
	private static MyButton add2;
	private static MyButton minus2;
	private static MyButton backSBChoice;
	private static MyButton start;
	
	private int i=0;
	private int j=0;
	
	public InitFrameView() throws IOException {
		
		backgroundImage = ImageIO.read(new File("res/background.png"));
		Dimension preferredSize = new Dimension(1408, 864);
		

        setPreferredSize(preferredSize);
        setBounds(0, 0, preferredSize.width, preferredSize.height);
        setLayout(null);
        
        //Music
      /*  try {
	           URL url = Main.class.getClassLoader().getResource("res/music/music.wav");
	           AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	           Clip clip = AudioSystem.getClip();
	           clip.open(audioIn);
	           clip.start();
	       }
		catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			throw new RuntimeException(e);
		}
	    */
        
        // Add Fonts
        try {
            if (font == null) {
                font = AddFont.createFont();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Font paintTitle = new Font(font.getFontName(),Font.PLAIN,160);
        Font titlePageFont = new Font(font.getFontName(),Font.PLAIN,80);
        Font buttonFont = new Font(font.getFontName(), Font.PLAIN, 40);
        
        // Add Title
        JLabel title = new JLabel("<html><font color = #6699FF >S</font><font color = #66CC66 >H</font><font color = #CC3333 >A</font><font color = #6699FF >P</font><font color = #66CC66 >E</font><font color = #CC3333 > U</font><font color = #6699FF >P</font></html>");
        title.setBounds(399, 30, 610, 180);
        title.setFont(paintTitle);
        title.setVisible(true);
        this.add(title);
        
        
        // Add Instructions & Title's page
        instr = new JLabel("");
        instr.setBounds(399, 280, 800, 50);
        instr.setFont(buttonFont);
        instr.setVisible(false);
        this.add(instr);
        titlePage = new JLabel("");
        titlePage.setFont(titlePageFont);
        titlePage.setVisible(false);
        this.add(titlePage);
        
        // Add Official Rules Image
        ImageIcon icon = new ImageIcon("res/rules.PNG");
        rulesImg = new JLabel();
        rulesImg.setIcon(icon);
        rulesImg.setBounds(274, 280, 860, 564);
        rulesImg.setVisible(false);
        this.add(rulesImg);
        
        // Add Credits text
        code = new JLabel("<html><font color = #FFFFFF>The project has been developped by Jacques Mironneau and Baptiste Guichard. The game is coded in Java.</font></html>");
        code.setBounds(300, 310, 900, 100);
        code.setFont(buttonFont);
        code.setVisible(false);
        this.add(code);
        graphics = new JLabel("<html><font color = #FFFFFF>All graphics have been made by Thomas Durand.</font></html>");
        graphics.setBounds(300, 450, 900, 50);
        graphics.setFont(buttonFont);
        graphics.setVisible(false);
        this.add(graphics);
        music = new JLabel("<html><font color = #FFFFFF>The music was created by Marceau Canu.</font></html>");
        music.setBounds(300, 550, 900, 50);
        music.setFont(buttonFont);
        music.setVisible(false);
        this.add(music);
        
        // ADD BUTONS
        // Start Menu
        play = new MyButton("PLAY", "res/buttons/empty-button.png", "res/buttons/empty-button-hover.png", "res/buttons/empty-button-hover.png");
        this.setMenuButton(play, 250, buttonFont);
        play.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.startMenu(1);
        	}
        });
        rules = new MyButton("RULES", "res/buttons/empty-button.png", "res/buttons/empty-button-hover.png", "res/buttons/empty-button-hover.png");
        this.setMenuButton(rules, 370, buttonFont);
        rules.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.startMenu(2);
        	}
        });
        credits = new MyButton("CREDITS", "res/buttons/empty-button.png", "res/buttons/empty-button-hover.png", "res/buttons/empty-button-hover.png");
        this.setMenuButton(credits, 490, buttonFont);
        credits.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.startMenu(3);
        	}
        });
        quit = new MyButton("QUIT", "res/buttons/empty-button.png", "res/buttons/empty-button-hover.png", "res/buttons/empty-button-hover.png");
        this.setMenuButton(quit, 610, buttonFont);
        quit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.startMenu(4);
        	}
        });
        
        // Game Mode Choice
        normal = new MyButton("NORMAL MODE", "res/buttons/empty-button.png", "res/buttons/empty-button-hover.png", "res/buttons/empty-button-hover.png");
		this.setMenuButton(normal, 360, buttonFont);
		normal.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.setGameMode(1);
        	}
        });
		advanced = new MyButton("ADVANCED MODE", "res/buttons/empty-button.png", "res/buttons/empty-button-hover.png", "res/buttons/empty-button-hover.png");
		this.setMenuButton(advanced, 480, buttonFont);
		advanced.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.setGameMode(2);
        	}
        });
		noAdjacency = new MyButton("NO ADJACENCY MODE", "res/buttons/empty-button.png", "res/buttons/empty-button-hover.png", "res/buttons/empty-button-hover.png");
		this.setMenuButton(noAdjacency, 600, buttonFont);
		noAdjacency.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.setGameMode(3);
        	}
        });
		backStartMenu = new MyButton("", "res/buttons/backward.png", "res/buttons/backward-hover.png", "res/buttons/backward-hover.png");
		this.setBackButton(backStartMenu);
		backStartMenu.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.setGameMode(0);
        	}
        });
		
		// Score Calculator Choice
		normalCalculator = new MyButton("NORMAL CALCULATOR", "res/buttons/empty-button.png", "res/buttons/empty-button-hover.png", "res/buttons/empty-button-hover.png");
		this.setMenuButton(normalCalculator, 360, buttonFont);
		normalCalculator.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.setScoreCalculator(1);
        	}
        });
		bonusCalculator = new MyButton("BONUS CALCULATOR", "res/buttons/empty-button.png", "res/buttons/empty-button-hover.png", "res/buttons/empty-button-hover.png");
		this.setMenuButton(bonusCalculator, 480, buttonFont);
		bonusCalculator.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.setScoreCalculator(2);
        	}
        });
		backGMChoice = new MyButton("", "res/buttons/backward.png", "res/buttons/backward-hover.png", "res/buttons/backward-hover.png");
		this.setBackButton(backGMChoice);
		backGMChoice.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.setScoreCalculator(0);
        	}
        });
		
		// Shape Board Choice
		rectangle = new MyButton("", "res/buttons/shape-square.png", "res/buttons/shape-square-hover.png", "res/buttons/shape-square-hover.png");
		this.setShapeButton(rectangle, 360, buttonFont);
		rectangle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.shapeBoard(1);
        	}
        });
		triangle = new MyButton("", "res/buttons/shape-triangle.png", "res/buttons/shape-triangle-hover.png", "res/buttons/shape-triangle-hover.png");
		this.setShapeButton(triangle, 490, buttonFont);
		triangle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.shapeBoard(2);
        	}
        });
		circle = new MyButton("", "res/buttons/shape-circle.png", "res/buttons/shape-circle-hover.png", "res/buttons/shape-circle-hover.png");
		this.setShapeButton(circle, 620, buttonFont);
		circle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.shapeBoard(3);
        	}
        });
		backSCChoice = new MyButton("", "res/buttons/backward.png", "res/buttons/backward-hover.png", "res/buttons/backward-hover.png");
		this.setBackButton(backSCChoice);
		backSCChoice.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.shapeBoard(0);
        	}
        });
		
		// Players Choice
		real = new JLabel("<html><font color = #FFFFFF>REAL PLAYERS:</font></html>");
		real.setBounds(399, 360, 900, 100);
		real.setFont(buttonFont);
		real.setVisible(false);
        this.add(real);
        virtual = new JLabel("<html><font color = #FFFFFF>VIRTUAL PLAYERS:</font></html>");
        virtual.setBounds(399, 580, 900, 50);
        virtual.setFont(buttonFont);
        virtual.setVisible(false);
        this.add(virtual);
        nbReal = new JLabel("<html><font color = #FFFFFF>"+i+"</font></html>");
        nbReal.setBounds(833, 373, 80, 80);
        nbReal.setFont(titlePageFont);
        nbReal.setVisible(false);
        this.add(nbReal);
        nbVirtual = new JLabel("<html><font color = #FFFFFF>"+j+"</font></html>");
        nbVirtual.setBounds(833, 570, 80, 80);
        nbVirtual.setFont(titlePageFont);
        nbVirtual.setVisible(false);
        this.add(nbVirtual);
        enterNames = new JLabel("");
		enterNames.setBounds(399, 430, 800, 50);
		enterNames.setFont(buttonFont);
		enterNames.setVisible(false);
        this.add(enterNames);
        name1 = new JTextField(15);
        name1.setBounds(450, 490, 200, 50);
        name1.setFont(buttonFont);
        name1.setBackground(Color.LIGHT_GRAY);
        name1.setVisible(false);
        this.add(name1);
        name2 = new JTextField(15);
        name2.setBounds(724, 490, 200, 50);
        name2.setFont(buttonFont);
        name2.setBackground(Color.LIGHT_GRAY);
        name2.setVisible(false);
        this.add(name2);
        name3 = new JTextField(15);
        name3.setBounds(999, 490, 200, 50);
        name3.setFont(buttonFont);
        name3.setBackground(Color.LIGHT_GRAY);
        name3.setVisible(false);
        this.add(name3);
        p1 = new JLabel("<html><font color = #FFFFFF>P1-</font></html>");
        p1.setBounds(399, 490, 50, 50);
        p1.setFont(buttonFont);
        p1.setVisible(false);
        this.add(p1);
        p2 = new JLabel("<html><font color = #FFFFFF>P2-</font></html>");
        p2.setBounds(674, 490, 50, 50);
        p2.setFont(buttonFont);
        p2.setVisible(false);
        this.add(p2);
        p3 = new JLabel("<html><font color = #FFFFFF>P3-</font></html>");
        p3.setBounds(949, 490, 50, 50);
        p3.setFont(buttonFont);
        p3.setVisible(false);
        this.add(p3);
        enterDifficulties = new JLabel("");
        enterDifficulties.setBounds(399, 630, 800, 50);
        enterDifficulties.setFont(buttonFont);
        enterDifficulties.setVisible(false);
        this.add(enterDifficulties);
        comp1 = new JLabel("<html><font color = #FFFFFF>COMP1-</font></html>");
        comp1.setBounds(399, 700, 150, 50);
        comp1.setFont(buttonFont);
        comp1.setVisible(false);
        this.add(comp1);
        comp2 = new JLabel("<html><font color = #FFFFFF>COMP2-</font></html>");
        comp2.setBounds(730, 700, 150, 50);
        comp2.setFont(buttonFont);
        comp2.setVisible(false);
        this.add(comp2);
        comp3 = new JLabel("<html><font color = #FFFFFF>COMP3-</font></html>");
        comp3.setBounds(1061, 700, 150, 50);
        comp3.setFont(buttonFont);
        comp3.setVisible(false);
        this.add(comp3);
        comp1Easy = new JCheckBox(" Easy");
        setCheckBox(comp1Easy, 515, 670);
        comp1Easy.setFont(buttonFont);
        comp1Difficult = new JCheckBox(" Difficult");
        comp1Difficult.setFont(buttonFont);
        setCheckBox(comp1Difficult, 515, 725);
        comp2Easy = new JCheckBox(" Easy");
        setCheckBox(comp2Easy, 845, 670);
        comp2Easy.setFont(buttonFont);
        comp2Difficult = new JCheckBox(" Difficult");
        comp2Difficult.setFont(buttonFont);
        setCheckBox(comp2Difficult, 845, 725);
        comp3Easy = new JCheckBox(" Easy");
        setCheckBox(comp3Easy, 1180, 670);
        comp3Easy.setFont(buttonFont);
        comp3Difficult = new JCheckBox(" Difficult");
        comp3Difficult.setFont(buttonFont);
        setCheckBox(comp3Difficult, 1180, 725);
		add = new MyButton("", "res/buttons/add.png", "res/buttons/add-hover.png", "res/buttons/add-hover.png");
		add.setBounds(900, 370, 64, 64);
		add.setVisible(false);
		this.add(add);
		add.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (i+j<3) {
        			i++;
        		}
        		nbReal.setText("<html><font color = #FFFFFF>"+i+"</font></html>");
        		switch (i) {
        		case 0:
        			enterNames.setVisible(false);
        			p1.setVisible(false);
        			name1.setVisible(false);
        			p2.setVisible(false);
        			name2.setVisible(false);
        			p3.setVisible(false);
        			name3.setVisible(false);
        			break;
        		case 1:
        			enterNames.setText("<html><font color = #FFFFFF>Please enter real player's name.</font></html>");
        			enterNames.setVisible(true);
        			p1.setVisible(true);
        			name1.setVisible(true);
        			p2.setVisible(false);
        			name2.setVisible(false);
        			p3.setVisible(false);
        			name3.setVisible(false);
        			break;
        		case 2:
        			enterNames.setText("<html><font color = #FFFFFF>Please enter real players' names.</font></html>");
        			enterNames.setVisible(true);
        			p1.setVisible(true);
        			name1.setVisible(true);
        			p2.setVisible(true);
        			name2.setVisible(true);
        			p3.setVisible(false);
        			name3.setVisible(false);
        			break;
        		case 3:
        			enterNames.setText("<html><font color = #FFFFFF>Please enter real players' names.</font></html>");
        			enterNames.setVisible(true);
        			p1.setVisible(true);
        			name1.setVisible(true);
        			p2.setVisible(true);
        			name2.setVisible(true);
        			p3.setVisible(true);
        			name3.setVisible(true);
        			break;
        		default:
        			break;
        		}
        	}
        });
		minus = new MyButton("", "res/buttons/minus.png", "res/buttons/minus-hover.png", "res/buttons/minus-hover.png");
		minus.setBounds(750, 370, 64, 64);
		minus.setVisible(false);
		this.add(minus);
		minus.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (i>0) {
            		i--;       			
        		}
        		nbReal.setText("<html><font color = #FFFFFF>"+i+"</font></html>");
        		switch (i) {
        		case 0:
        			enterNames.setVisible(false);
        			p1.setVisible(false);
        			name1.setVisible(false);
        			name1.setText("");
        			p2.setVisible(false);
        			name2.setVisible(false);
        			name2.setText("");
        			p3.setVisible(false);
        			name3.setVisible(false);
        			name3.setText("");
        			break;
        		case 1:
        			enterNames.setText("<html><font color = #FFFFFF>Please enter real player's name.</font></html>");
        			enterNames.setVisible(true);
        			p1.setVisible(true);
        			name1.setVisible(true);
        			p2.setVisible(false);
        			name2.setVisible(false);
        			name2.setText("");
        			p3.setVisible(false);
        			name3.setVisible(false);
        			name3.setText("");
        			break;
        		case 2:
        			enterNames.setText("<html><font color = #FFFFFF>Please enter real players' names.</font></html>");
        			enterNames.setVisible(true);
        			p1.setVisible(true);
        			name1.setVisible(true);
        			p2.setVisible(true);
        			name2.setVisible(true);
        			p3.setVisible(false);
        			name3.setVisible(false);
        			name3.setText("");
        			break;
        		case 3:
        			enterNames.setText("<html><font color = #FFFFFF>Please enter real players' names.</font></html>");
        			enterNames.setVisible(true);
        			p1.setVisible(true);
        			name1.setVisible(true);
        			p2.setVisible(true);
        			name2.setVisible(true);
        			p3.setVisible(true);
        			name3.setVisible(true);
        			break;
        		default:
        			break;
        		}
        	}
        });
		add2 = new MyButton("", "res/buttons/add.png", "res/buttons/add-hover.png", "res/buttons/add-hover.png");
		add2.setBounds(900, 565, 64, 64);
		add2.setVisible(false);
		this.add(add2);
		add2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (j+i<3) {
            		j++;       			
        		}
        		nbVirtual.setText("<html><font color = #FFFFFF>"+j+"</font></html>");
        		switch (j) {
        		case 0:
        			enterDifficulties.setVisible(false);
        			comp1.setVisible(false);
        			comp1Easy.setVisible(false);
        			comp1Easy.setSelected(false);
        			comp1Difficult.setVisible(false);
        			comp1Difficult.setSelected(false);
        			comp2.setVisible(false);
        			comp2Easy.setVisible(false);
        			comp2Easy.setSelected(false);
        			comp2Difficult.setVisible(false);
        			comp2Difficult.setSelected(false);
        			comp3.setVisible(false);
        			comp3Easy.setVisible(false);
        			comp3Easy.setSelected(false);
        			comp3Difficult.setVisible(false);
        			comp3Difficult.setSelected(false);
        			break;
        		case 1:
        			enterDifficulties.setText("<html><font color = #FFFFFF>Please enter virtual player's difficulty.</font></html>");
        			enterDifficulties.setVisible(true);
        			comp1.setVisible(true);
        			comp1Easy.setVisible(true);
        			comp1Difficult.setVisible(true);
        			comp2.setVisible(false);
        			comp2Easy.setVisible(false);
        			comp2Easy.setSelected(false);
        			comp2Difficult.setVisible(false);
        			comp2Difficult.setSelected(false);
        			comp3.setVisible(false);
        			comp3Easy.setVisible(false);
        			comp3Easy.setSelected(false);
        			comp3Difficult.setVisible(false);
        			comp3Difficult.setSelected(false);
        			break;
        		case 2:
        			enterDifficulties.setText("<html><font color = #FFFFFF>Please enter virtual players' difficulties.</font></html>");
        			enterDifficulties.setVisible(true);
        			comp1.setVisible(true);
        			comp1Easy.setVisible(true);
        			comp1Difficult.setVisible(true);
        			comp2.setVisible(true);
        			comp2Easy.setVisible(true);
        			comp2Difficult.setVisible(true);
        			comp3.setVisible(false);
        			comp3Easy.setVisible(false);
        			comp3Easy.setSelected(false);
        			comp3Difficult.setVisible(false);
        			comp3Difficult.setSelected(false);
        			break;
        		case 3: 
        			enterDifficulties.setText("<html><font color = #FFFFFF>Please enter virtual players' difficulties.</font></html>");
        			enterDifficulties.setVisible(true);
        			comp1.setVisible(true);
        			comp1Easy.setVisible(true);
        			comp1Difficult.setVisible(true);
        			comp2.setVisible(true);
        			comp2Easy.setVisible(true);
        			comp2Difficult.setVisible(true);
        			comp3.setVisible(true);
        			comp3Easy.setVisible(true);
        			comp3Difficult.setVisible(true);
        			break;
        		default:
        			break;
        		}
        	}
        });
		minus2 = new MyButton("", "res/buttons/minus.png", "res/buttons/minus-hover.png", "res/buttons/minus-hover.png");
		minus2.setBounds(750, 565, 64, 64);
		minus2.setVisible(false);
		this.add(minus2);
		minus2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (j>0) {
            		j--;       			
        		}
        		nbVirtual.setText("<html><font color = #FFFFFF>"+j+"</font></html>");
        		switch (j) {
        		case 0:
        			enterDifficulties.setVisible(false);
        			comp1.setVisible(false);
        			comp1Easy.setVisible(false);
        			comp1Easy.setSelected(false);
        			comp1Difficult.setVisible(false);
        			comp1Difficult.setSelected(false);
        			comp2.setVisible(false);
        			comp2Easy.setVisible(false);
        			comp2Easy.setSelected(false);
        			comp2Difficult.setVisible(false);
        			comp2Difficult.setSelected(false);
        			comp3.setVisible(false);
        			comp3Easy.setVisible(false);
        			comp3Easy.setSelected(false);
        			comp3Difficult.setVisible(false);
        			comp3Difficult.setSelected(false);
        			break;
        		case 1:
        			enterDifficulties.setText("<html><font color = #FFFFFF>Please enter virtual player's difficulty.</font></html>");
        			enterDifficulties.setVisible(true);
        			comp1.setVisible(true);
        			comp1Easy.setVisible(true);
        			comp1Difficult.setVisible(true);
        			comp2.setVisible(false);
        			comp2Easy.setVisible(false);
        			comp2Easy.setSelected(false);
        			comp2Difficult.setVisible(false);
        			comp2Difficult.setSelected(false);
        			comp3.setVisible(false);
        			comp3Easy.setVisible(false);
        			comp3Easy.setSelected(false);
        			comp3Difficult.setVisible(false);
        			comp3Difficult.setSelected(false);
        			break;
        		case 2:
        			enterDifficulties.setText("<html><font color = #FFFFFF>Please enter virtual players' difficulties.</font></html>");
        			enterDifficulties.setVisible(true);
        			comp1.setVisible(true);
        			comp1Easy.setVisible(true);
        			comp1Difficult.setVisible(true);
        			comp2.setVisible(true);
        			comp2Easy.setVisible(true);
        			comp2Difficult.setVisible(true);
        			comp3.setVisible(false);
        			comp3Easy.setVisible(false);
        			comp3Easy.setSelected(false);
        			comp3Difficult.setVisible(false);
        			comp3Difficult.setSelected(false);
        			break;
        		case 3: 
        			enterDifficulties.setText("<html><font color = #FFFFFF>Please enter virtual players' difficulties.</font></html>");
        			enterDifficulties.setVisible(true);
        			comp1.setVisible(true);
        			comp1Easy.setVisible(true);
        			comp1Difficult.setVisible(true);
        			comp2.setVisible(true);
        			comp2Easy.setVisible(true);
        			comp2Difficult.setVisible(true);
        			comp3.setVisible(true);
        			comp3Easy.setVisible(true);
        			comp3Difficult.setVisible(true);
        			break;
        		default:
        			break;
        		}
        	}
        });
		start = new MyButton("START THE GAME", "res/buttons/empty-button.png", "res/buttons/empty-button-hover.png", "res/buttons/empty-button-hover.png");
		this.setMenuButton(start, 780, buttonFont);
		start.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (i+j == 1 || i+j == 0) {
        			i=0;
        			j=0;
        			enterNames.setVisible(false);
        			p1.setVisible(false);
        			name1.setVisible(false);
        			enterDifficulties.setVisible(false);
        			comp1.setVisible(false);
        			comp1Easy.setVisible(false);
        			comp1Easy.setSelected(false);
        			comp1Difficult.setVisible(false);
        			comp1Difficult.setSelected(false);
        			nbReal.setText("<html><font color = #FFFFFF>"+i+"</font></html>");
        			nbVirtual.setText("<html><font color = #FFFFFF>"+j+"</font></html>");
        			return;
        		}
        		Map<Integer, String> realPlayers = new HashMap<Integer, String>();
				Map<Integer, String> virtualPlayers = new HashMap<Integer, String>();
        		int nbPReal=0;
				for(int incr=1; incr<=i; incr++) {
        			switch (incr) {
        			case 1:
        				realPlayers.put(incr, name1.getText());
        				break;
        			case 2:
        				realPlayers.put(incr, name2.getText());
        				break;
        			case 3:
        				realPlayers.put(incr, name3.getText());
        				break;
        			default:
        				break;
        			}
        			nbPReal=incr;
        		}
        		for (int incr2=1; incr2<=j; incr2++) {
        			switch(incr2) {
        			case 1:
        				if (comp1Easy.isSelected()&&!comp1Difficult.isSelected()) {
        					virtualPlayers.put(incr2+nbPReal, EASY);
        				} else if (!comp1Easy.isSelected()&&comp1Difficult.isSelected()) {
        					virtualPlayers.put(incr2+nbPReal, MEDIUM);
        				} else {
        					i=0;
                			j=0;
                			enterNames.setVisible(false);
                			p1.setVisible(false);
                			name1.setVisible(false);
                			p2.setVisible(false);
                			name2.setVisible(false);
                			p3.setVisible(false);
                			name3.setVisible(false);
                			enterDifficulties.setVisible(false);
                			comp1.setVisible(false);
                			comp1Easy.setVisible(false);
                			comp1Easy.setSelected(false);
                			comp1Difficult.setVisible(false);
                			comp1Difficult.setSelected(false);
                			comp2.setVisible(false);
                			comp2Easy.setVisible(false);
                			comp2Easy.setSelected(false);
                			comp2Difficult.setVisible(false);
                			comp2Difficult.setSelected(false);
                			comp3.setVisible(false);
                			comp3Easy.setVisible(false);
                			comp3Easy.setSelected(false);
                			comp3Difficult.setVisible(false);
                			comp3Difficult.setSelected(false);
                			nbReal.setText("<html><font color = #FFFFFF>"+i+"</font></html>");
                			nbVirtual.setText("<html><font color = #FFFFFF>"+j+"</font></html>");
                			realPlayers.clear();
                			virtualPlayers.clear();
                			return;
        				}
        				break;
        			case 2:
        				if (comp2Easy.isSelected()&&!comp2Difficult.isSelected()) {
        					virtualPlayers.put(incr2+nbPReal, EASY);
        				} else if (!comp2Easy.isSelected()&&comp2Difficult.isSelected()) {
        					virtualPlayers.put(incr2+nbPReal, MEDIUM);
        				} else {
        					i=0;
                			j=0;
                			enterNames.setVisible(false);
                			p1.setVisible(false);
                			name1.setVisible(false);
                			p2.setVisible(false);
                			name2.setVisible(false);
                			p3.setVisible(false);
                			name3.setVisible(false);
                			enterDifficulties.setVisible(false);
                			comp1.setVisible(false);
                			comp1Easy.setVisible(false);
                			comp1Easy.setSelected(false);
                			comp1Difficult.setVisible(false);
                			comp1Difficult.setSelected(false);
                			comp2.setVisible(false);
                			comp2Easy.setVisible(false);
                			comp2Easy.setSelected(false);
                			comp2Difficult.setVisible(false);
                			comp2Difficult.setSelected(false);
                			comp3.setVisible(false);
                			comp3Easy.setVisible(false);
                			comp3Easy.setSelected(false);
                			comp3Difficult.setVisible(false);
                			comp3Difficult.setSelected(false);
                			nbReal.setText("<html><font color = #FFFFFF>"+i+"</font></html>");
                			nbVirtual.setText("<html><font color = #FFFFFF>"+j+"</font></html>");
                			realPlayers.clear();
                			virtualPlayers.clear();
                			return;
        				}
        				break;
        			case 3:
        				if (comp3Easy.isSelected()&&!comp3Difficult.isSelected()) {
        					virtualPlayers.put(incr2, EASY);
        				} else if (!comp3Easy.isSelected()&&comp3Difficult.isSelected()) {
        					virtualPlayers.put(incr2, MEDIUM);
        				} else {
        					i=0;
                			j=0;
                			enterNames.setVisible(false);
                			p1.setVisible(false);
                			name1.setVisible(false);
                			p2.setVisible(false);
                			name2.setVisible(false);
                			p3.setVisible(false);
                			name3.setVisible(false);
                			enterDifficulties.setVisible(false);
                			comp1.setVisible(false);
                			comp1Easy.setVisible(false);
                			comp1Easy.setSelected(false);
                			comp1Difficult.setVisible(false);
                			comp1Difficult.setSelected(false);
                			comp2.setVisible(false);
                			comp2Easy.setVisible(false);
                			comp2Easy.setSelected(false);
                			comp2Difficult.setVisible(false);
                			comp2Difficult.setSelected(false);
                			comp3.setVisible(false);
                			comp3Easy.setVisible(false);
                			comp3Easy.setSelected(false);
                			comp3Difficult.setVisible(false);
                			comp3Difficult.setSelected(false);
                			nbReal.setText("<html><font color = #FFFFFF>"+i+"</font></html>");
                			nbVirtual.setText("<html><font color = #FFFFFF>"+j+"</font></html>");
                			realPlayers.clear();
                			virtualPlayers.clear();
                			return;
        				}
        				break;
        			default:
        				break;
        			}
        		}
        		controller.setPlayer(realPlayers, virtualPlayers);
        	}
        });
		backSBChoice = new MyButton("", "res/buttons/backward.png", "res/buttons/backward-hover.png", "res/buttons/backward-hover.png");
		this.setBackButton(backSBChoice);
		backSBChoice.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.setNbPlayers(0);
        	}
        });
		// TO DO 
	}

	public void propertyChange(PropertyChangeEvent evt) {
		
		InitState is = (InitState) evt.getNewValue();
		
		switch (is)
		{
		case CREDITS:
			
			// Components to Hide
			play.setVisible(false);
			rules.setVisible(false);
			credits.setVisible(false);
			quit.setVisible(false);
			
			// Visible Components
			code.setVisible(true);
			graphics.setVisible(true);
			music.setVisible(true);
			backStartMenu.setVisible(true);
			titlePage.setVisible(true);
			titlePage.setText("<html><font color = #FFFFFF>CREDITS</font></html>");
			titlePage.setBounds(564, 190, 280, 90);
			
			break;
		case GAME_MODE_CHOICE:
			
			// Components to Hide
			play.setVisible(false);
			rules.setVisible(false);
			credits.setVisible(false);
			quit.setVisible(false);
			normalCalculator.setVisible(false);
			bonusCalculator.setVisible(false);
			backGMChoice.setVisible(false);
			
			// Visible Components
			normal.setVisible(true);
			advanced.setVisible(true);
			noAdjacency.setVisible(true);
			backStartMenu.setVisible(true);
			instr.setText("<html><font color = #FFFFFF>Please choose one game mode.</font></html>");
			instr.setVisible(true);
			titlePage.setVisible(true);
			titlePage.setText("<html><font color = #FFFFFF>GAME MODE</font></html>");
			titlePage.setBounds(519, 190, 370, 90);
			
			break;
		case INIT_DONE:
			
			this.controller.launch();
			
			break;
		case PLAYER_CHOICE:
			
			//Components to Hide
			rectangle.setVisible(false);
			triangle.setVisible(false);
			circle.setVisible(false);
			backSCChoice.setVisible(false);
			
			// Visible Components
			instr.setText("<html><font color = #FFFFFF>Please setup players. (2 Players Min and 3 Players Max in total)</font></html>");
			titlePage.setText("<html><font color = #FFFFFF>PLAYERS</font></html>");
			titlePage.setBounds(559, 190, 270, 90);
			instr.setBounds(399, 280, 800, 100);
			backSBChoice.setVisible(true);
			add.setVisible(true);
			minus.setVisible(true);
			add2.setVisible(true);
			minus2.setVisible(true);
			real.setVisible(true);
			virtual.setVisible(true);
			nbReal.setText("<html><font color = #FFFFFF>"+i+"</font></html>");
			nbReal.setVisible(true);
			nbVirtual.setText("<html><font color = #FFFFFF>"+j+"</font></html>");
			nbVirtual.setVisible(true);
			start.setVisible(true);
			
			break;
		case QUIT:
			
			this.controller.quit();
			
			break;
		case RULES:
			
			// Components to Hide
			play.setVisible(false);
			rules.setVisible(false);
			credits.setVisible(false);
			quit.setVisible(false);
			
			// Visible Components
			backStartMenu.setVisible(true);
			titlePage.setVisible(true);
			titlePage.setText("<html><font color = #FFFFFF>RULES</font></html>");
			titlePage.setBounds(604, 190, 200, 90);
			rulesImg.setVisible(true);
			
			
			break;
		case SCORE_CALCULATOR_CHOICE:
			
			// Components to Hide
			normal.setVisible(false);
			advanced.setVisible(false);
			noAdjacency.setVisible(false);
			backStartMenu.setVisible(false);
			rectangle.setVisible(false);
			triangle.setVisible(false);
			circle.setVisible(false);
			backSCChoice.setVisible(false);			
			
			// Visible Components
			normalCalculator.setVisible(true);
			bonusCalculator.setVisible(true);
			backGMChoice.setVisible(true);
			instr.setText("<html><font color = #FFFFFF>Please choose one score calculator.</font></html>");
			titlePage.setText("<html><font color = #FFFFFF>SCORE CALCULATOR</font></html>");
			titlePage.setBounds(389, 190, 630, 90);
			
			break;
		case SHAPE_BOARD_CHOICE:
			
			// Components to Hide
			normalCalculator.setVisible(false);
			bonusCalculator.setVisible(false);
			backGMChoice.setVisible(false);
			backSBChoice.setVisible(false);
			add.setVisible(false);
			minus.setVisible(false);
			add2.setVisible(false);
			minus2.setVisible(false);
			real.setVisible(false);
			virtual.setVisible(false);
			nbReal.setVisible(false);
			i=0;
			nbReal.setText("<html><font color = #FFFFFF></font></html>"+i);
			nbVirtual.setVisible(false);
			j=0;
			nbVirtual.setText("<html><font color = #FFFFFF></font></html>"+j);
			start.setVisible(false);
			enterNames.setVisible(false);
			p1.setVisible(false);
			name1.setVisible(false);
			p2.setVisible(false);
			name2.setVisible(false);
			p3.setVisible(false);
			name3.setVisible(false);
			enterDifficulties.setVisible(false);
			comp1.setVisible(false);
			comp1Easy.setVisible(false);
			comp1Easy.setSelected(false);
			comp1Difficult.setVisible(false);
			comp1Difficult.setSelected(false);
			comp2.setVisible(false);
			comp2Easy.setVisible(false);
			comp2Easy.setSelected(false);
			comp2Difficult.setVisible(false);
			comp2Difficult.setSelected(false);
			comp3.setVisible(false);
			comp3Easy.setVisible(false);
			comp3Easy.setSelected(false);
			comp3Difficult.setVisible(false);
			comp3Difficult.setSelected(false);
			
			// Visible Components
			rectangle.setVisible(true);
			triangle.setVisible(true);
			circle.setVisible(true);
			backSCChoice.setVisible(true);
			instr.setText("<html><font color = #FFFFFF>Please choose one shape of board.</font></html>");
			titlePage.setText("<html><font color = #FFFFFF>BOARD SHAPE</font></html>");
			titlePage.setBounds(489, 190, 430, 90);
			instr.setBounds(399, 280, 600, 50);
			
			break;
		case START_MENU:
			
			// Components to Hide
			normal.setVisible(false);
			advanced.setVisible(false);
			noAdjacency.setVisible(false);
			backStartMenu.setVisible(false);
			instr.setVisible(false);
			code.setVisible(false);
			graphics.setVisible(false);
			music.setVisible(false);
			titlePage.setVisible(false);
			rulesImg.setVisible(false);
			
			// Visible Components
			play.setVisible(true);
			rules.setVisible(true);
			credits.setVisible(true);
			quit.setVisible(true);
			
			break;
		default:
			break;
		
		}
	}
	
	
	public void paintComponent(Graphics g) {
	       super.paintComponent(g);

	       // Draw the background image.
	       g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	     }
	
	public void setController(InitController controller)
	{
		this.controller = controller;
	}
	
	public void setBackButton(MyButton button) {
		button.setBounds(50, 50, 80, 80);
		button.setVisible(false);
		this.add(button);
		
	}
	
	public void setMenuButton(MyButton button, int y, Font font) {
		button.setBounds(474, y, 460, 80);
		button.setVisible(false);
		button.setFont(font);
		this.add(button);
		
	}
	
	public void setShapeButton(MyButton button, int y, Font font) {
		button.setBounds(474, y, 460, 111);
		button.setVisible(false);
		button.setFont(font);
		this.add(button);
		
	}
	
	public void setCheckBox(JCheckBox cb, int x, int y) {
		ImageIcon checkbox = new ImageIcon("res/buttons/checkbox.png");
        ImageIcon box = new ImageIcon("res/buttons/box.png");
        cb.setBounds(x, y, 200, 50);
        cb.setVisible(false);
        cb.setBackground(Color.LIGHT_GRAY);
        cb.setIcon(box);
        cb.setSelectedIcon(checkbox);
        cb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(cb);
	}
}
