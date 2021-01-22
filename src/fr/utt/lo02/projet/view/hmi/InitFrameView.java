package fr.utt.lo02.projet.view.hmi;

import fr.utt.lo02.projet.controller.InitController;
import fr.utt.lo02.projet.model.InitState;
import fr.utt.lo02.projet.view.InitView;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the initialization view in HMI.
 * It extends JPanel to create a frame and it implements InitView to follow the initialization view's construction.
 * @author Baptiste, Jacques
 *
 */
public class InitFrameView extends JPanel implements InitView
{

	/**
	 * Initialization view's thread's name.
	 */
    public static final String THREAD_FROM_INIT_VIEW_NAME = "swing";
    
    /**
     * Game & menu's background.
     */
    private Image backgroundImage;

    /**
     * Controller of the MVC model.
     */
    private InitController controller;
    
    /**
     * Font used for the initialization menu and the game.
     */
    private static Font font = null;
    
    /**
     * Easy strategy for the virtual Player.
     */
    public static final String EASY = "easy";
    
    /**
     * Medium strategy for the virtual Player.
     */
    public static final String MEDIUM = "medium";

    /**
     * Label used on the initialization menu.
     */
    private static JLabel instr;
	/**
	 * Label used on the initialization menu.
	 */
    private static JLabel titlePage;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel code;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel graphics;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel music;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel rulesImg;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel real;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel virtual;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel nbReal;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel nbVirtual;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel enterNames;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel enterDifficulties;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel p1;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel p2;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel p3;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel comp1;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel comp2;
    /**
     * Label used on the initialization menu.
     */
    private static JLabel comp3;

    /**
     * Text field used on the initialization menu for Real Player's name.
     */
    private static JTextField name1;
    /**
     * Text field used on the initialization menu for Real Player's name.
     */
    private static JTextField name2;
    /**
     * Text field used on the initialization menu for Real Player's name.
     */
    private static JTextField name3;

    /**
     * Check box used on the initialization menu to set the Virtual Player.
     */
    JCheckBox comp1Easy;
    /**
     * Check box used on the initialization menu to set the Virtual Player.
     */
    JCheckBox comp1Difficult;
    /**
     * Check box used on the initialization menu to set the Virtual Player.
     */
    JCheckBox comp2Easy;
    /**
     * Check box used on the initialization menu to set the Virtual Player.
     */
    JCheckBox comp2Difficult;
    /**
     * Check box used on the initialization menu to set the Virtual Player.
     */
    JCheckBox comp3Easy;
    /**
     * Check box used on the initialization menu to set the Virtual Player.
     */
    JCheckBox comp3Difficult;

    /**
     * Button used on the initialization menu.
     */
    private static MyButton play;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton rules;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton credits;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton quit;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton normal;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton advanced;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton noAdjacency;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton backStartMenu;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton normalCalculator;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton bonusCalculator;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton backGMChoice;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton rectangle;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton triangle;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton circle;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton backSCChoice;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton add;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton minus;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton add2;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton minus2;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton backSBChoice;
    /**
     * Button used on the initialization menu.
     */
    private static MyButton start;

    /**
     * Real player's number.
     */
    private int i = 0;
    /**
     * Virtual player's number.
     */
    private int j = 0;

    /**
     * Constructor of the view. Sets the background's image, frame's dimension, the music.
     * It instantiate all components and set it not visible.
     * @throws IOException
     */
    public InitFrameView() throws IOException
    {

        backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("background.png"));

        Dimension preferredSize = new Dimension(1408, 864);

        setPreferredSize(preferredSize);
        setBounds(0, 0, preferredSize.width, preferredSize.height);
        setLayout(null);

        //Music
        playMusic();

        // Add Fonts
        try
        {
            if (font == null)
            {
                font = AddFont.createFont();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Font paintTitle = new Font(font.getFontName(), Font.PLAIN, 160);
        Font titlePageFont = new Font(font.getFontName(), Font.PLAIN, 80);
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
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("rules.PNG"));
        rulesImg = new JLabel();
        rulesImg.setIcon(icon);
        rulesImg.setBounds(274, 280, 860, 564);
        rulesImg.setVisible(false);
        this.add(rulesImg);

        // Add Credits text
        code = new JLabel("<html><font color = #FFFFFF>The project has been developped by Jacques Mironneau and Baptiste Guichard. The game is coded in Java.</font></html>");
        this.setUpCreditsText(code, buttonFont);
        graphics = new JLabel("<html><font color = #FFFFFF>All graphics have been made by Thomas Durand.</font></html>");
        this.setUpCreditsText(graphics, buttonFont);
        music = new JLabel("<html><font color = #FFFFFF>The music was created by Marceau Canu.</font></html>");
        this.setUpCreditsText(music, buttonFont);

        // ADD BUTONS
        // Start Menu
        this.initStartMenuPage(buttonFont);

        // Game Mode Choice
        this.initGameModeChoicePage(buttonFont);

        // Score Calculator Choice
        this.initScoreCalculatorChoicePage(buttonFont);

        // Shape Board Choice
        this.initShapeBoardChoicePage(buttonFont);

        // Players Choice
        this.initPlayersChoicePage(buttonFont, titlePageFont);
    }

    /**
     * Changes view's components visibility according to the current state. 
     */
    public void propertyChange(PropertyChangeEvent evt)
    {

        InitState is = (InitState) evt.getNewValue();

        switch (is)
        {
            case CREDITS:

                // Components to Hide
                this.hideStartMenu();

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
            	this.hideStartMenu();
            	this.hideScoreCalculatorPage();

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
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.launch();

                    }
                }.start();

                break;
            case PLAYER_CHOICE:

                //Components to Hide
            	this.hideShapeBoardPage();

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
                nbReal.setText("<html><font color = #FFFFFF>" + i + "</font></html>");
                nbReal.setVisible(true);
                nbVirtual.setText("<html><font color = #FFFFFF>" + j + "</font></html>");
                nbVirtual.setVisible(true);
                start.setVisible(true);

                break;
            case QUIT:

                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.quit();

                    }
                }.start();

                break;
            case RULES:

                // Components to Hide
            	this.hideStartMenu();

                // Visible Components
                backStartMenu.setVisible(true);
                titlePage.setVisible(true);
                titlePage.setText("<html><font color = #FFFFFF>RULES</font></html>");
                titlePage.setBounds(604, 190, 200, 90);
                rulesImg.setVisible(true);


                break;
            case SCORE_CALCULATOR_CHOICE:

                // Components to Hide
            	this.hideGameModeChoicePage();
                this.hideShapeBoardPage();

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
                this.hideScoreCalculatorPage();
                backSBChoice.setVisible(false);
                add.setVisible(false);
                minus.setVisible(false);
                add2.setVisible(false);
                minus2.setVisible(false);
                real.setVisible(false);
                virtual.setVisible(false);
                nbReal.setVisible(false);
                i = 0;
                nbReal.setText("<html><font color = #FFFFFF></font></html>" + i);
                nbVirtual.setVisible(false);
                j = 0;
                nbVirtual.setText("<html><font color = #FFFFFF></font></html>" + j);
                start.setVisible(false);
                enterNames.setVisible(false);
                this.name1VisibleFalse();
                this.name2VisibleFalse();
                this.name3VisibleFalse();
                enterDifficulties.setVisible(false);
                this.comp1VisibleFalse();
                this.comp2VisibleFalse();
                this.comp3VisibleFalse();

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
            	this.hideGameModeChoicePage();
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


    /**
     * Paints the background image.
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Draw the background image.
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    /**
     * Sets view's controller.
     * @param controller
     */
    public void setController(InitController controller)
    {
        this.controller = controller;
    }

    /**
     * Creates and initializes all start menu's components. 
     * @param font the font used for this components.
     */
    public void initStartMenuPage(Font font) {
    	play = new MyButton("PLAY", "buttons/empty-button.png", "buttons/empty-button-hover.png", "buttons/empty-button-hover.png");
        this.setUpMenuButton(play, 250, font);
        play.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.startMenu(1);

                    }
                }.start();
            }
        });
        rules = new MyButton("RULES", "buttons/empty-button.png", "buttons/empty-button-hover.png", "buttons/empty-button-hover.png");
        this.setUpMenuButton(rules, 370, font);
        rules.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.startMenu(2);

                    }
                }.start();
            }
        });
        credits = new MyButton("CREDITS", "buttons/empty-button.png", "buttons/empty-button-hover.png", "buttons/empty-button-hover.png");
        this.setUpMenuButton(credits, 490, font);
        credits.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.startMenu(3);

                    }
                }.start();
            }
        });
        quit = new MyButton("QUIT", "buttons/empty-button.png", "buttons/empty-button-hover.png", "buttons/empty-button-hover.png");
        this.setUpMenuButton(quit, 610, font);
        quit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.startMenu(4);

                    }
                }.start();
            }
        });
    }
    
    /**
     * Creates and initializes all game mode choice page's components. 
     * @param font the font used for this components.
     */
    public void initGameModeChoicePage(Font font) {
    	normal = new MyButton("NORMAL MODE", "buttons/empty-button.png", "buttons/empty-button-hover.png", "buttons/empty-button-hover.png");
        this.setUpMenuButton(normal, 360, font);
        normal.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.setGameMode(1);


                    }
                }.start();
            }
        });
        advanced = new MyButton("ADVANCED MODE", "buttons/empty-button.png", "buttons/empty-button-hover.png", "buttons/empty-button-hover.png");
        this.setUpMenuButton(advanced, 480, font);
        advanced.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.setGameMode(2);

                    }
                }.start();
            }
        });
        noAdjacency = new MyButton("NO ADJACENCY MODE", "buttons/empty-button.png", "buttons/empty-button-hover.png", "buttons/empty-button-hover.png");
        this.setUpMenuButton(noAdjacency, 600, font);
        noAdjacency.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.setGameMode(3);

                    }
                }.start();
            }
        });
        backStartMenu = new MyButton("", "buttons/backward.png", "buttons/backward-hover.png", "buttons/backward-hover.png");
        this.setUpBackButton(backStartMenu);
        backStartMenu.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.setGameMode(0);

                    }
                }.start();
            }
        });
    }
    
    /**
     * Creates and initializes all score calculator choice page's components. 
     * @param font the font used for this components.
     */
    public void initScoreCalculatorChoicePage(Font font) {
    	 normalCalculator = new MyButton("NORMAL CALCULATOR", "buttons/empty-button.png", "buttons/empty-button-hover.png", "buttons/empty-button-hover.png");
         this.setUpMenuButton(normalCalculator, 360, font);
         normalCalculator.addActionListener(new ActionListener()
         {
             public void actionPerformed(ActionEvent e)
             {
                 new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                 {
                     @Override
                     public void run()
                     {
                         controller.setScoreCalculator(1);

                     }
                 }.start();
             }
         });
         bonusCalculator = new MyButton("BONUS CALCULATOR", "buttons/empty-button.png", "buttons/empty-button-hover.png", "buttons/empty-button-hover.png");
         this.setUpMenuButton(bonusCalculator, 480, font);
         bonusCalculator.addActionListener(new ActionListener()
         {
             public void actionPerformed(ActionEvent e)
             {
                 new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                 {
                     @Override
                     public void run()
                     {
                         controller.setScoreCalculator(2);

                     }
                 }.start();
             }
         });
         backGMChoice = new MyButton("", "buttons/backward.png", "buttons/backward-hover.png", "buttons/backward-hover.png");
         this.setUpBackButton(backGMChoice);
         backGMChoice.addActionListener(new ActionListener()
         {
             public void actionPerformed(ActionEvent e)
             {
                 new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                 {
                     @Override
                     public void run()
                     {
                         controller.setScoreCalculator(0);

                     }
                 }.start();
             }
         });
    }
    
    /**
     * Creates and initializes all shape board choice page's components. 
     * @param font the font used for this components.
     */
    public void initShapeBoardChoicePage(Font font) {
    	rectangle = new MyButton("", "buttons/shape-square.png", "buttons/shape-square-hover.png", "buttons/shape-square-hover.png");
        this.setUpShapeButton(rectangle, 360);
        rectangle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.shapeBoard(1);

                    }
                }.start();
            }
        });
        triangle = new MyButton("", "buttons/shape-triangle.png", "buttons/shape-triangle-hover.png", "buttons/shape-triangle-hover.png");
        this.setUpShapeButton(triangle, 490);
        triangle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.shapeBoard(2);

                    }
                }.start();
            }
        });
        circle = new MyButton("", "buttons/shape-circle.png", "buttons/shape-circle-hover.png", "buttons/shape-circle-hover.png");
        this.setUpShapeButton(circle, 620);
        circle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.shapeBoard(3);

                    }
                }.start();
            }
        });
        backSCChoice = new MyButton("", "buttons/backward.png", "buttons/backward-hover.png", "buttons/backward-hover.png");
        this.setUpBackButton(backSCChoice);
        backSCChoice.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.shapeBoard(0);

                    }
                }.start();
            }
        });
    }
    
	/**
	 * Creates and initializes all players choice page's components. 
     * @param font one of the 2 fonts used for this components.
	 * @param font2 the other font used for this components.
	 */
    public void initPlayersChoicePage(Font font, Font font2) {
    	real = new JLabel("<html><font color = #FFFFFF>REAL PLAYERS:</font></html>");
        this.setUpPlayersPageTexts(real, 399, 360, 900, 100, font);
        virtual = new JLabel("<html><font color = #FFFFFF>VIRTUAL PLAYERS:</font></html>");
        this.setUpPlayersPageTexts(virtual, 399, 580, 900, 50, font);
        nbReal = new JLabel("<html><font color = #FFFFFF>" + i + "</font></html>");
        this.setUpPlayersPageTexts(nbReal, 833, 373, 80, 80, font2);
        nbVirtual = new JLabel("<html><font color = #FFFFFF>" + j + "</font></html>");
        this.setUpPlayersPageTexts(nbVirtual, 833, 570, 80, 80, font2);
        enterNames = new JLabel("");
        this.setUpPlayersPageTexts(enterNames, 399, 430, 800, 50, font);
        name1 = new JTextField(15);
        this.setUpTextFields(name1, 450, font);
        name2 = new JTextField(15);
        this.setUpTextFields(name2, 724, font);
        name3 = new JTextField(15);
        this.setUpTextFields(name3, 999, font);
        p1 = new JLabel("<html><font color = #FFFFFF>P1-</font></html>");
        this.setUpPlayersPageTexts(p1, 399, 490, 50, 50, font);
        p2 = new JLabel("<html><font color = #FFFFFF>P2-</font></html>");
        this.setUpPlayersPageTexts(p2, 674, 490, 50, 50, font);
        p3 = new JLabel("<html><font color = #FFFFFF>P3-</font></html>");
        this.setUpPlayersPageTexts(p3, 949, 490, 50, 50, font);
        enterDifficulties = new JLabel("");
        this.setUpPlayersPageTexts(enterDifficulties, 399, 630, 800, 50, font);
        comp1 = new JLabel("<html><font color = #FFFFFF>COMP1-</font></html>");
        comp1.setBounds(399, 700, 150, 50);
        this.setUpPlayersPageTexts(comp1, 399, 700, 150, 50, font);
        comp2 = new JLabel("<html><font color = #FFFFFF>COMP2-</font></html>");
        this.setUpPlayersPageTexts(comp2, 730, 700, 150, 50, font);
        comp3 = new JLabel("<html><font color = #FFFFFF>COMP3-</font></html>");
        this.setUpPlayersPageTexts(comp3, 1061, 700, 150, 50, font);
        comp1Easy = new JCheckBox(" Easy");
        setUpCheckBox(comp1Easy, 515, 670, font);
        comp1Difficult = new JCheckBox(" Difficult");
        setUpCheckBox(comp1Difficult, 515, 725, font);
        comp2Easy = new JCheckBox(" Easy");
        setUpCheckBox(comp2Easy, 845, 670, font);
        comp2Difficult = new JCheckBox(" Difficult");
        setUpCheckBox(comp2Difficult, 845, 725, font);
        comp3Easy = new JCheckBox(" Easy");
        setUpCheckBox(comp3Easy, 1180, 670, font);
        comp3Difficult = new JCheckBox(" Difficult");
        setUpCheckBox(comp3Difficult, 1180, 725, font);
        add = new MyButton("", "buttons/add.png", "buttons/add-hover.png", "buttons/add-hover.png");
        add.setBounds(900, 370, 64, 64);
        add.setVisible(false);
        this.add(add);
        add.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (i + j < 3)
                {
                    i++;
                }
                nbReal.setText("<html><font color = #FFFFFF>" + i + "</font></html>");
                InitFrameView.this.addRealPlayers();
            }
        });
        minus = new MyButton("", "buttons/minus.png", "buttons/minus-hover.png", "buttons/minus-hover.png");
        minus.setBounds(750, 370, 64, 64);
        minus.setVisible(false);
        this.add(minus);
        minus.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (i > 0)
                {
                    i--;
                }
                nbReal.setText("<html><font color = #FFFFFF>" + i + "</font></html>");
                InitFrameView.this.addRealPlayers();
            }
        });
        add2 = new MyButton("", "buttons/add.png", "buttons/add-hover.png", "buttons/add-hover.png");
        add2.setBounds(900, 565, 64, 64);
        add2.setVisible(false);
        this.add(add2);
        add2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (j + i < 3)
                {
                    j++;
                }
                nbVirtual.setText("<html><font color = #FFFFFF>" + j + "</font></html>");
                InitFrameView.this.addVirtualPlayers();
            }
        });
        minus2 = new MyButton("", "buttons/minus.png", "buttons/minus-hover.png", "buttons/minus-hover.png");
        minus2.setBounds(750, 565, 64, 64);
        minus2.setVisible(false);
        this.add(minus2);
        minus2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (j > 0)
                {
                    j--;
                }
                nbVirtual.setText("<html><font color = #FFFFFF>" + j + "</font></html>");
                InitFrameView.this.addVirtualPlayers();
            }
        });
        start = new MyButton("START THE GAME", "buttons/empty-button.png", "buttons/empty-button-hover.png", "buttons/empty-button-hover.png");
        this.setUpMenuButton(start, 780, font);
        start.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (i + j == 1 || i + j == 0)
                {
                    i = 0;
                    j = 0;
                    enterNames.setVisible(false);
                    InitFrameView.this.name1VisibleFalse();
                    enterDifficulties.setVisible(false);
                    InitFrameView.this.comp1VisibleFalse();
                    nbReal.setText("<html><font color = #FFFFFF>" + i + "</font></html>");
                    nbVirtual.setText("<html><font color = #FFFFFF>" + j + "</font></html>");
                    return;
                }
                Map<Integer, String> realPlayers = new HashMap<>();
                Map<Integer, String> virtualPlayers = new HashMap<>();
                int nbPReal = 0;
                for (int incr = 1; incr <= i; incr++)
                {
                    switch (incr)
                    {
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
                    nbPReal = incr;
                }
                for (int incr2 = 1; incr2 <= j; incr2++)
                {
                    switch (incr2)
                    {
                        case 1:
                            if (comp1Easy.isSelected() && !comp1Difficult.isSelected())
                            {
                                virtualPlayers.put(incr2 + nbPReal, EASY);
                            } else if (!comp1Easy.isSelected() && comp1Difficult.isSelected())
                            {
                                virtualPlayers.put(incr2 + nbPReal, MEDIUM);
                            } else
                            {
                            	InitFrameView.this.resetPlayersPage(realPlayers, virtualPlayers);
                                return;
                            }
                            break;
                        case 2:
                            if (comp2Easy.isSelected() && !comp2Difficult.isSelected())
                            {
                                virtualPlayers.put(incr2 + nbPReal, EASY);
                            } else if (!comp2Easy.isSelected() && comp2Difficult.isSelected())
                            {
                                virtualPlayers.put(incr2 + nbPReal, MEDIUM);
                            } else
                            {
                            	InitFrameView.this.resetPlayersPage(realPlayers, virtualPlayers);
                                return;
                            }
                            break;
                        case 3:
                            if (comp3Easy.isSelected() && !comp3Difficult.isSelected())
                            {
                                virtualPlayers.put(incr2, EASY);
                            } else if (!comp3Easy.isSelected() && comp3Difficult.isSelected())
                            {
                                virtualPlayers.put(incr2, MEDIUM);
                            } else
                            {
                                InitFrameView.this.resetPlayersPage(realPlayers, virtualPlayers);
                                return;
                            }
                            break;
                        default:
                            break;
                    }
                }
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.setPlayer(realPlayers, virtualPlayers);

                    }
                }.start();
            }
        });
        backSBChoice = new MyButton("", "buttons/backward.png", "buttons/backward-hover.png", "buttons/backward-hover.png");
        this.setUpBackButton(backSBChoice);
        backSBChoice.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Thread(InitFrameView.THREAD_FROM_INIT_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.setNbPlayers(0);

                    }
                }.start();
            }
        });
    }
    
    /**
     * Sets up a back button. Used because there are several different back buttons.
     * @param button the back button to set up.
     */
    public void setUpBackButton(MyButton button)
    {
        button.setBounds(50, 50, 80, 80);
        button.setVisible(false);
        this.add(button);

    }

	/**
	 * Sets up a menu button. Used because there are several different menu buttons.
	 * @param button the menu button to set up.
	 * @param y menu button's ordinate position. 
	 * @param font menu button's font.
	 */
    public void setUpMenuButton(MyButton button, int y, Font font)
    {
        button.setBounds(474, y, 460, 80);
        button.setVisible(false);
        button.setFont(font);
        this.add(button);

    }
    
    /**
     * Sets up a shape button. Used because there are several different shape buttons.
     *  @param button the shape button to set up.
	 * @param y shape button's ordinate position. 
     */
    public void setUpShapeButton(MyButton button, int y)
    {
        button.setBounds(474, y, 460, 111);
        button.setVisible(false);
        this.add(button);

    }
    
    /**
     * Sets up credits texts. Used because there are several different credits texts.
     * @param label the text to set up.
     * @param font text's font.
     */
    public void setUpCreditsText(JLabel label, Font font) {
        label.setBounds(300, 550, 900, 100);
        label.setFont(font);
        label.setVisible(false);
        this.add(label);
    }

    /**
     * Sets up a check box. Used because there are several different check boxes.
     * @param cb the check box to set up.
     * @param x check box's abscissa position.
     * @param y check box's ordinate position.
     * @param font check box's font.
     */
    public void setUpCheckBox(JCheckBox cb, int x, int y, Font font)
    {
        ImageIcon checkbox = new ImageIcon(getClass().getClassLoader().getResource("buttons/checkbox.png"));
        ImageIcon box = new ImageIcon(getClass().getClassLoader().getResource("buttons/box.png"));
        cb.setBounds(x, y, 200, 50);
        cb.setFont(font);
        cb.setVisible(false);
        cb.setBackground(Color.LIGHT_GRAY);
        cb.setIcon(box);
        cb.setSelectedIcon(checkbox);
        cb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(cb);
    }
    
    /**
     * Displays the text fields and instruction according to i, real players' index.
     */
    public void addRealPlayers() {
    	switch (i)
        {
            case 0:
                enterNames.setVisible(false);
                this.name1VisibleFalse();
                this.name2VisibleFalse();
                this.name3VisibleFalse();
                break;
            case 1:
                enterNames.setText("<html><font color = #FFFFFF>Please enter real player's name.</font></html>");
                enterNames.setVisible(true);
                this.name1VisibleTrue();
                this.name2VisibleFalse();
                this.name3VisibleFalse();
                break;
            case 2:
                enterNames.setText("<html><font color = #FFFFFF>Please enter real players' names.</font></html>");
                enterNames.setVisible(true);
                this.name1VisibleTrue();
                this.name2VisibleTrue();
                this.name3VisibleFalse();
                break;
            case 3:
                enterNames.setText("<html><font color = #FFFFFF>Please enter real players' names.</font></html>");
                enterNames.setVisible(true);
                this.name1VisibleTrue();
                this.name2VisibleTrue();
                this.name3VisibleTrue();
                break;
            default:
                break;
        }
    }
    
    /**
     * Displays the check boxes and instruction according to j, virtual players' index.
     */
    public void addVirtualPlayers() {
    	switch (j)
        {
            case 0:
                enterDifficulties.setVisible(false);
                this.comp1VisibleFalse();
                this.comp2VisibleFalse();
                this.comp3VisibleFalse();
                break;
            case 1:
                enterDifficulties.setText("<html><font color = #FFFFFF>Please enter virtual player's difficulty.</font></html>");
                enterDifficulties.setVisible(true);
                this.comp1VisibleTrue();
                this.comp2VisibleFalse();
                this.comp3VisibleFalse();
                break;
            case 2:
                enterDifficulties.setText("<html><font color = #FFFFFF>Please enter virtual players' difficulties.</font></html>");
                enterDifficulties.setVisible(true);
                this.comp1VisibleTrue();
                this.comp2VisibleTrue();
                this.comp3VisibleFalse();
                break;
            case 3:
                enterDifficulties.setText("<html><font color = #FFFFFF>Please enter virtual players' difficulties.</font></html>");
                enterDifficulties.setVisible(true);
                this.comp1VisibleTrue();
                this.comp2VisibleTrue();
                this.comp3VisibleTrue();
                break;
            default:
                break;
        }
    }
    
    /**
     * Resets real player1's text field and sets it not visible.
     */
    public void name1VisibleFalse() {
    	p1.setVisible(false);
        name1.setVisible(false);
        name1.setText("");
    }
    
    /**
     * Sets visible real player1's text field.
     */
    public void name1VisibleTrue() {
    	p1.setVisible(true);
        name1.setVisible(true);
    }
    
    /**
     * Resets real player2's text field and sets it not visible.
     */
    public void name2VisibleFalse() {
    	p2.setVisible(false);
        name2.setVisible(false);
        name2.setText("");
    }
    
	/**
	 * Sets visible real player2's text field.
	 */
    public void name2VisibleTrue() {
    	p2.setVisible(true);
        name2.setVisible(true);
    }
    
    /**
     * Resets real player3's text field and sets it not visible.
     */
    public void name3VisibleFalse() {
    	p3.setVisible(false);
        name3.setVisible(false);
        name3.setText("");
    }
    
    /**
     * Sets visible real player3's text field.
     */
    public void name3VisibleTrue() {
    	p3.setVisible(true);
        name3.setVisible(true);
    }
    
    /**
     * Sets virtual player1's components (Text field and Check boxes) not visible.
     */
    public void comp1VisibleFalse() {
    	comp1.setVisible(false);
        comp1Easy.setVisible(false);
        comp1Easy.setSelected(false);
        comp1Difficult.setVisible(false);
        comp1Difficult.setSelected(false);
    }

    /**
     * Sets virtual player1's components (Text field and Check boxes) visible.
     */
    public void comp1VisibleTrue() {
    	comp1.setVisible(true);
        comp1Easy.setVisible(true);
        comp1Difficult.setVisible(true);
    }
    
    /**
     * Sets virtual player2's components (Text field and Check boxes) not visible.
     */
    public void comp2VisibleFalse() {
    	comp2.setVisible(false);
        comp2Easy.setVisible(false);
        comp2Easy.setSelected(false);
        comp2Difficult.setVisible(false);
        comp2Difficult.setSelected(false);
    }
    
    /**
     * Sets virtual player2's components (Text field and Check boxes) visible.
     */
    public void comp2VisibleTrue() {
    	comp2.setVisible(true);
        comp2Easy.setVisible(true);
        comp2Difficult.setVisible(true);
    }
    
    /**
     * Sets virtual player3's components (Text field and Check boxes) not visible.
     */
    public void comp3VisibleFalse() {
    	comp3.setVisible(false);
        comp3Easy.setVisible(false);
        comp3Easy.setSelected(false);
        comp3Difficult.setVisible(false);
        comp3Difficult.setSelected(false);
    }
    
    /**
     * Sets virtual player3's components (Text field and Check boxes) visible.
     */
    public void comp3VisibleTrue() {
    	comp3.setVisible(true);
        comp3Easy.setVisible(true);
        comp3Difficult.setVisible(true);
    }
    
    /**
     * Sets up a players page's text. Used because there are several different players page's texts.
     * @param label the text to set up.
     * @param posx text's abscissa position.
     * @param posy text's ordinate position.
     * @param x label's width.
     * @param y label's height.
     * @param font text's font.
     */
    public void setUpPlayersPageTexts(JLabel label, int posx, int posy, int x, int y, Font font) {
    	label.setBounds(posx, posy, x, y);
        label.setFont(font);
        label.setVisible(false);
        this.add(label);
    }
    
    /**
     * Sets up a text field. Used because there are several different text fields.
     * @param textField the text field to set up.
     * @param x text field's abscissa position.
     * @param font text field's font.
     */
    public void setUpTextFields(JTextField textField,int x, Font font) {
    	textField.setBounds(x, 490, 200, 50);
    	textField.setFont(font);
    	textField.setBackground(Color.LIGHT_GRAY);
    	textField.setVisible(false);
        this.add(textField);
    }
    
    /**
     * Resets players page, sets all components not visible and sets index of real and virtual players to 0.
     * @param realPlayers Map with player's number and name from a real player.
     * @param virtualPlayers Map with player's number and name from a virtual player.
     */
    public void resetPlayersPage(Map<Integer, String> realPlayers, Map<Integer, String> virtualPlayers) {
    	i = 0;
        j = 0;
        enterNames.setVisible(false);
        this.name1VisibleFalse();
        this.name2VisibleFalse();
        this.name3VisibleFalse();
        enterDifficulties.setVisible(false);
        this.comp1VisibleFalse();
        this.comp2VisibleFalse();
        this.comp3VisibleFalse();
        nbReal.setText("<html><font color = #FFFFFF>" + i + "</font></html>");
        nbVirtual.setText("<html><font color = #FFFFFF>" + j + "</font></html>");
        realPlayers.clear();
        virtualPlayers.clear();
    }
    
    /**
     *  Hides all start menu's components.
     */
    public void hideStartMenu() {
    	play.setVisible(false);
        rules.setVisible(false);
        credits.setVisible(false);
        quit.setVisible(false);
    }
    
    /**
     *  Hides all game mode choice page's components.
     */
    public void hideGameModeChoicePage() {
    	normal.setVisible(false);
        advanced.setVisible(false);
        noAdjacency.setVisible(false);
        backStartMenu.setVisible(false);
    }
    
	/**
	 *  Hides all shape board choice page's components.
	 */
    public void hideShapeBoardPage() {
    	rectangle.setVisible(false);
        triangle.setVisible(false);
        circle.setVisible(false);
        backSCChoice.setVisible(false);
    }
    
    /**
     * Hides all score calculator choice page's components.
     */
    public void hideScoreCalculatorPage() {
    	normalCalculator.setVisible(false);
        bonusCalculator.setVisible(false);
        backGMChoice.setVisible(false);
    }
    
    /**
     * Plays background music.
     */
    private void playMusic()
    {

        new Thread(() -> {
            try
            {
                AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("music/canu.wav"));

                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e)
            {
                e.printStackTrace();
            }
        }).start();
    }
}
