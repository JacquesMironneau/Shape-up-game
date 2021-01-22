package fr.utt.lo02.projet.view.hmi;

import fr.utt.lo02.projet.controller.GameController;
import fr.utt.lo02.projet.controller.ShapeUpGameController;
import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.CircleBoard;
import fr.utt.lo02.projet.model.board.Coordinates;
import fr.utt.lo02.projet.model.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.model.game.*;
import fr.utt.lo02.projet.model.player.Player;
import fr.utt.lo02.projet.model.player.RealPlayer;
import fr.utt.lo02.projet.model.player.VirtualPlayer;
import fr.utt.lo02.projet.view.console.GameConsoleView;
import fr.utt.lo02.projet.view.GameView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Game view using swing hmi
 * this view is based on the Observer pattern, it basically receives events in the propertyChange method
 * and then display and set the correct listener accordingly.
 */
public class SwingHmiView extends JPanel implements GameView, MouseListener, MouseMotionListener
{

    /**
     * Name of the thread used to call controller method
     * A thread is always used to perform every controller operation
     * This ensures that the thread that call the repaint is a event dispatch one
     * that avoids strange display errors.
     */
    public static final String THREAD_FROM_GAME_VIEW_NAME = "swing_thread_view";

    /**
     * Card width for the stone image
     */
    public static final int CARD_WIDTH = 64;

    /**
     * Card height for the stone image
     */
    public static final int CARD_HEIGHT = 64;

    /**
     * Offset between two cards in the abscissa axis
     */
    public static final int OFFSET_X = 40;

    /**
     * Offset between two cards in the ordinate axis
     */
    public static final int OFFSET_Y = 70;

    /**
     * Width of the hologram in pixel
     */
    public static final int HOLOGRAM_WIDTH = 96;

    /**
     * Height of the hologram in pixel
     */
    public static final int HOLOGRAM_HEIGHT = 128;

    /**
     * Offset for the board beside the left part of the screen
     */
    public static final int LEFT_BOARD_OFFSET = 450;

    /**
     * Offset for the board beside the top of the screen
     */
    public static final int TOP_BOARD_OFFSET = 150;

    /**
     * Offset for the player hand representation
     */
    public static final int PLAYER_HAND_Y = 700;

    /**
     * Refresh rate of the holograms animations
     * So, the number of milliseconds between two repaint during the animations
     */
    public static final int ANIMATION_REFRESH_RATE = 100;

    /**
     * Path to the background image file
     */
    public static final String BACKGROUND_PATH = "background.png";

    /**
     * Number of milliseconds between two actions of the virtual players
     */
    public static final int IA_SLEEP_TIME = 200;

    /**
     * List of the card (stones)
     */
    private final List<Image> sprite;

    /**
     * Images of the hologram animations for each cards
     */
    private final Image[][] spriteGlitchAnimations;


    /**
     * Abscissa offset for drag and drop in order to have the card
     * centered on the user mouse
     */
    private int xAdj;

    /**
     * Ordinate offset for drag and drop in order to have the card
     * centered on the user mouse
     */
    private int yAdj;

    /**
     * Initial abscissa of the dragged card
     */
    private int prevX;

    /**
     * Initial ordinate of the dragged card
     */
    private int prevY;

    /**
     * Current abscissa of the dragged card
     */
    private int currX;

    /**
     * Current ordinate of the dragged card
     */
    private int currY;

    /**
     * The index of the card among the player hand
     * Mainly used for the advanced game
     */
    private int cardIndex;

    /**
     * True if the game should display user indications
     */
    private boolean shouldDrawText;

    /**
     * The font used to draw the texts
     */
    private static Font font = null;


    /**
     * Controller of the game (MVC pattern)
     */
    private GameController controller;


    /**
     * Reference to the to be played game
     */
    private final AbstractShapeUpGame model;


    /**
     * The board of the game
     */
    private final AbstractBoard boardModel;


    /**
     * View of the current board
     * We use a copy of the board in order to manage the drag and drop and the failed action
     */
    private final CopyOnWriteArrayList<CardWithScreenCoordinates> boardView;

    /**
     * View of the hand of the current player
     */
    private final List<CardWithScreenCoordinates> handView;

    /**
     * The dragged card, this card is the one that follows the user cursor
     */
    private CardWithScreenCoordinates cardImage;

    /**
     * True if the player can place false if the action is a move
     */
    private boolean placementTime;

    /**
     * Current state of the game model
     */
    private GameState gameState;

    /**
     * List of the correct location for the current player action
     * (Move or place)
     */
    private List<Coordinates> goodRequestsScreen;


    /**
     * Should the scores be displayed (used in the draws method)
     */
    private boolean displayScores;

    /**
     * List of every holograms
     */
    private final List<Image> spriteHologram;

    /**
     * Current index of the animation frame (between 0 and 7 since our animations uses 8 images)
     */
    private int numberOfFrame;

    /**
     * Boolean used to say if the animation should proceed or not
     */
    private boolean animate;


    /**
     * The button that ends the turn
     * It is displayed during the second choice (after the player places a card if he decided to)
     */
    private MyButton endTurnButton;

    /**
     * The button to set the move request to the controller
     * This is a button that is placed during the first choice (when the player turns just began)
     */
    private MyButton moveButton;

    /**
     * The button to set the place request to the controller
     * This is a button that is placed during the first choice (when the player turns just began)
     */
    private MyButton placeButton;

    /**
     * The button to end the round and launch a new one
     * It is displayed during the scores display.
     */
    private MyButton endRoundButton;

    /**
     * The button to set the move request to the controller
     * This is a button that is placed during the second choice (when the player have placed first)
     */
    private MyButton secondMoveButton;

    /**
     * The background image (wood table for now)
     */
    private BufferedImage backgroundImage;

    /**
     * Convertor to match game and screen coordinates
     */
    private final CoordinatesConvertor coordinatesConvertor;

    /**
     *  Drawer for the end round display
     */
    private final EndRoundScoreDrawer endRoundScoreDrawer;

    /**
     * Drawer for the end game display
     */
    private final EndGameScoreDrawer endGameScoreDrawer;


    /**
     * Creates a SwingHmiView setting the size of the JPanel
     * it splits the different sprites and instantiate every buttons
     * @param modelBoard board of the game
     * @param game reference to the game
     */
    public SwingHmiView(AbstractBoard modelBoard, AbstractShapeUpGame game)
    {
        this.model = game;
        this.boardModel = modelBoard;
        Dimension preferredSize = new Dimension(1408, 864);


        setPreferredSize(preferredSize);
        setBounds(0, 0, preferredSize.width, preferredSize.height);
        setLayout(null);

        SpriteSplitter splitter = new SpriteSplitter();
        sprite = splitter.splitSprite();
        spriteHologram = splitter.splitSpriteHologram();
        spriteGlitchAnimations = splitter.splitSpriteGlitchAnimations();

        instantiateButtons();


        UIManager.put("Slider.onlyLeftMouseButtonDrag", Boolean.TRUE);

        boardView = new CopyOnWriteArrayList<>();
        handView = new CopyOnWriteArrayList<>();

        endGameScoreDrawer = new EndGameScoreDrawer(model);
        endRoundScoreDrawer = new EndRoundScoreDrawer(model);
        goodRequestsScreen = new CopyOnWriteArrayList<>();

        try
        {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource(BACKGROUND_PATH)));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        coordinatesConvertor = new CoordinatesConvertor(boardModel);
        placementTime = false;
        shouldDrawText = false;
        gameState = null;
        displayScores = false;
        animate = false;
    }


    /**
     * Observable method from the game model
     * @param evt the event observed in the model
     * @see GameState
     * @see AbstractShapeUpGame
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        GameState prevGs = gameState;
        gameState = (GameState) evt.getNewValue();

        switch (gameState)
        {
            case MOVE -> {
                endRoundButton.setVisible(false);
                moveButton.setVisible(false);
                placeButton.setVisible(false);
                secondMoveButton.setVisible(false);
                endTurnButton.setVisible(false);
                if (prevGs != GameState.MOVE)
                {
                    shouldDrawText = true;
                }
                placementTime = true;

                generateFreeLocation();
                updateDisplayBoard();
                placementTime = false;

                removeMouseMotionListener(this);
                removeMouseListener(this);
                addMouseListener(this);
                addMouseMotionListener(this);

            }
            case PLACE -> {
                moveButton.setVisible(false);
                placeButton.setVisible(false);
                secondMoveButton.setVisible(false);
                endTurnButton.setVisible(false);

                if (prevGs != GameState.PLACE)
                    shouldDrawText = true;
                placementTime = true;

                generateFreeLocation();

                removeMouseMotionListener(this);
                removeMouseListener(this);

                addMouseListener(this);
                addMouseMotionListener(this);

                updateDisplayBoard();
                updateDisplayHand();

            }
            case PLACE_DONE -> {

                if (model.getCurrentPlayer() instanceof VirtualPlayer && !SwingUtilities.isEventDispatchThread())
                {
                    try
                    {
                        SwingUtilities.invokeAndWait(() -> {
                            updateDisplayBoard();
                            repaint();

                            try
                            {
                                Thread.sleep(IA_SLEEP_TIME);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        });
                    } catch (InterruptedException | InvocationTargetException e)
                    {
                        e.printStackTrace();
                    }
                } else
                {
                    updateDisplayBoard();
                    animate = true;

                    for (int i = 0; i < 8; i++)
                    {
                        repaint();
                        try
                        {
                            Thread.sleep(ANIMATION_REFRESH_RATE);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    animate = false;
                    repaint();

                }
                removeMouseListener(this);
                removeMouseMotionListener(this);

            }
            case MOVE_DONE -> {
                animate = true;
                removeMouseListener(this);
                removeMouseMotionListener(this);

                // Runs inside of the Swing UI thread
                if (model.getCurrentPlayer() instanceof VirtualPlayer && !SwingUtilities.isEventDispatchThread())
                {
                    animate = false;
                    try
                    {
                        SwingUtilities.invokeAndWait(() -> {
                            updateDisplayBoard();
                            try
                            {
                                Thread.sleep(IA_SLEEP_TIME);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        });
                    } catch (InterruptedException | InvocationTargetException e)
                    {
                        e.printStackTrace();
                    }
                } else
                {
                    updateDisplayBoard();
                    animate = true;

                    for (int i = 0; i < 8; i++)
                    {
                        repaint();
                        try
                        {
                            Thread.sleep(ANIMATION_REFRESH_RATE);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    animate = false;
                    repaint();
                }

            }

            case ACTION_FAILED -> {

            }
            case FIRST_TURN -> {

                goodRequestsScreen.clear();
                goodRequestsScreen.add(new Coordinates(LEFT_BOARD_OFFSET, TOP_BOARD_OFFSET));
                repaint();

                new Thread(THREAD_FROM_GAME_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.play();
                    }
                }.start();

            }
            case FIRST_CHOICE -> {

                endRoundButton.setVisible(false);
                moveButton.setVisible(false);
                placeButton.setVisible(false);
                secondMoveButton.setVisible(false);
                endTurnButton.setVisible(false);

                displayScores = false;

                removeMouseListener(this);
                removeMouseMotionListener(this);
                goodRequestsScreen.clear();
                goodRequestsScreen.add(new Coordinates(LEFT_BOARD_OFFSET, TOP_BOARD_OFFSET));
                updateDisplayBoard();
                updateDisplayHand();
                repaint();


                placeButton.setVisible(true);
                moveButton.setVisible(true);
                placeButton.validate();
                moveButton.validate();
                validate();
                repaint();

            }
            case SECOND_CHOICE -> {

                endRoundButton.setVisible(false);
                moveButton.setVisible(false);
                placeButton.setVisible(false);
                secondMoveButton.setVisible(false);
                endTurnButton.setVisible(false);

                removeMouseListener(this);
                removeMouseMotionListener(this);


                endTurnButton.setVisible(true);
                secondMoveButton.setVisible(true);

            }
            case END_TURN -> {

                SwingUtilities.isEventDispatchThread();
                removeMouseMotionListener(this);
                removeMouseListener(this);
                if (!SwingUtilities.isEventDispatchThread())
                {
                    try
                    {
                        SwingUtilities.invokeAndWait(() -> {
                            updateDisplayBoard();
                            try
                            {
                                Thread.sleep(200);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        });
                    } catch (InterruptedException | InvocationTargetException e)
                    {
                        e.printStackTrace();
                    }
                }
                new Thread(THREAD_FROM_GAME_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.endTurn();
                    }
                }.start();


            }
            case CARD_DRAW, VICTORY_CARD -> {

                //TODO add animation
                repaint();

            }
            case END_ROUND -> {

                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                goodRequestsScreen.clear();
                updateDisplayBoard();
                repaint();

                boardView.clear();
                handView.clear();

                new Thread(THREAD_FROM_GAME_VIEW_NAME)
                {
                    @Override
                    public void run()
                    {
                        controller.endRound();
                    }
                }.start();

            }
            case END_GAME -> {
                boardView.clear();
                handView.clear();
                repaint();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {

    }

    /**
     * Mouse event to get the card under cursor, if a card exists, allow it to be dragged and remove it
     * from its previous location
     *
     * @param mouseEvent mouse event of the pressed card
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {

        if (mouseEvent.getButton() != MouseEvent.BUTTON1) return;
        cardImage = null;

        // Select card among player's hand
        if (placementTime)
        {

            for (CardWithScreenCoordinates cardImage : handView)
            {
                if (mouseEvent.getX() >= cardImage.getX() && mouseEvent.getX() <= cardImage.getX() + CARD_WIDTH)
                {

                    if (mouseEvent.getY() >= cardImage.getY() && mouseEvent.getY() <= cardImage.getY() + CARD_HEIGHT)
                    {
                        this.cardImage = cardImage;
                        cardIndex = handView.indexOf(cardImage);

                        break;
                    }
                }
            }

            if (cardImage == null)
                return;
            handView.remove(cardImage);
            repaint();


        } // Select card among board
        else
        {
            for (CardWithScreenCoordinates cardImage : boardView)
            {

                if (mouseEvent.getX() >= cardImage.getX() && mouseEvent.getX() <= cardImage.getX() + CARD_WIDTH)
                {

                    if (mouseEvent.getY() >= cardImage.getY() && mouseEvent.getY() <= cardImage.getY() + CARD_HEIGHT)
                    {
                        this.cardImage = cardImage;
                        break;
                    }
                }
            }
            if (cardImage == null)
                return;

            generateFreeLocation();
            repaint();

            boardView.remove(cardImage);

        }


        prevX = cardImage.getX();
        prevY = cardImage.getY();
        xAdj = prevX - mouseEvent.getX();
        yAdj = prevY - mouseEvent.getY();
        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));


    }

    /**
     * Updates the coordinates of cardImage
     *
     * @param mouseEvent mouse event of the dragged card
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {


        if (cardImage == null) return;

        currX = mouseEvent.getX();
        currY = mouseEvent.getY();
        cardImage.setX(currX + xAdj);
        cardImage.setY(currY+ yAdj);

        repaint();
    }

    /**
     * Released the dragged card and try to fit it in a correct location
     * if not, put it back in the hand or its previous location.
     *
     * @param mouseEvent mouse event of the released card
     */
    public void mouseReleased(MouseEvent mouseEvent)
    {

        if (mouseEvent.getButton() != MouseEvent.BUTTON1) return;


        if (cardImage == null) return;
        boolean hasBeenPlaced = false;

        if (boardView.isEmpty())
        {
            if (mouseEvent.getX() >= LEFT_BOARD_OFFSET && mouseEvent.getX() <= this.getWidth())
            {
                if (mouseEvent.getY() >= TOP_BOARD_OFFSET && mouseEvent.getY() <= PLAYER_HAND_Y)
                {
                    boardView.add(new CardWithScreenCoordinates(LEFT_BOARD_OFFSET, TOP_BOARD_OFFSET, cardImage.getCard()));
                    hasBeenPlaced = true;
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                    cardImage = null;
                    repaint();
                    new Thread(THREAD_FROM_GAME_VIEW_NAME)
                    {
                        @Override
                        public void run()
                        {
                            controller.askPlace(0, 0, cardIndex);
                        }
                    }.start();

                }
            }
            if (!hasBeenPlaced)
            {
                handView.add(new CardWithScreenCoordinates(prevX, prevY, cardImage.getCard()));
            }
            return;

        }
        // Put image in an empty location if it exists under cursor
        for (Coordinates goodRequestCoordinates : goodRequestsScreen)
        {

            if (mouseEvent.getX() >= goodRequestCoordinates.getX() && mouseEvent.getX() <= goodRequestCoordinates.getX() + CARD_WIDTH)
            {
                if (mouseEvent.getY() >= goodRequestCoordinates.getY() && mouseEvent.getY() <= goodRequestCoordinates.getY() + CARD_HEIGHT)
                {
                    boardView.add(new CardWithScreenCoordinates(goodRequestCoordinates.getX(), goodRequestCoordinates.getY(), cardImage.getCard()));
                    hasBeenPlaced = true;
                    break;
                }
            }

        }
        // Else put back to previous location
        if (!hasBeenPlaced)
        {
            if (placementTime)
            {
                handView.add(new CardWithScreenCoordinates(prevX, prevY, cardImage.getCard()));

            } else
            {
                boardView.add(new CardWithScreenCoordinates(prevX, prevY, cardImage.getCard()));

            }

        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        cardImage = null;
        repaint();


        Map<Coordinates, Card> placedCards = this.boardModel.getPlacedCards();

        Set<Integer> abscissaCoordinates = new HashSet<>();
        Set<Integer> ordinateCoordinates = new HashSet<>();

        for (Coordinates coord : placedCards.keySet())
        {
            abscissaCoordinates.add(coord.getX());
            ordinateCoordinates.add(coord.getY());
        }

        int minAbscissa = Collections.min(abscissaCoordinates);
        int maxOrdinate = Collections.max(ordinateCoordinates);


        Coordinates coordinates = coordinatesConvertor.screenToGameCoordinates(currX, currY, minAbscissa, maxOrdinate);
        if (placementTime)
        {
            new Thread(THREAD_FROM_GAME_VIEW_NAME)
            {
                @Override
                public void run()
                {
                    controller.askPlace(coordinates.getX(), coordinates.getY(), cardIndex);
                }
            }.start();

        } else
        {
            Coordinates previous = coordinatesConvertor.screenToGameCoordinates(prevX, prevY, minAbscissa, maxOrdinate);
            new Thread(THREAD_FROM_GAME_VIEW_NAME)
            {
                @Override
                public void run()
                {
                    controller.askMove(previous.getX(), previous.getY(), coordinates.getX(), coordinates.getY());
                }
            }.start();
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {

    }

    /**
     * Displays a failed move
     *
     * @param moveRequestResult fail reason
     */
    @Override
    public void displayMoveFailed(MoveRequestResult moveRequestResult)
    {

    }

    /**
     * Displays a failed place
     *
     * @param placeRequestResult fail reason
     */
    @Override
    public void displayPlaceFailed(PlaceRequestResult placeRequestResult)
    {

    }

    /**
     * Displays the scores of the just ended round
     */
    @Override
    public void displayScoresEndRound()
    {

        endRoundButton.setVisible(false);
        moveButton.setVisible(false);
        placeButton.setVisible(false);
        secondMoveButton.setVisible(false);
        endTurnButton.setVisible(false);
        displayScores = true;

        repaint();


        endRoundButton.setVisible(true);
    }

    @Override
    public void displayBoard()
    {
        updateDisplayBoard();
        repaint();
    }

    @Override
    public void setController(GameController controller)
    {
        this.controller = controller;
    }


    /**
     * Update the content of the handView based on the model current player hand
     * and repaint the panel
     */
    private void updateDisplayHand()
    {

        List<Card> playerHand = this.model.getCurrentPlayer().getPlayerHand();

        handView.clear();

        for (int j = 0; j < playerHand.size(); j++)
        {
            int x = j * (CARD_WIDTH + OFFSET_X) + 100;
            handView.add(new CardWithScreenCoordinates(x, PLAYER_HAND_Y, playerHand.get(j)));
        }
        repaint();
    }

    /**
     * update the content of boardView based on the model board
     * and then repaint the panel
     */
    private void updateDisplayBoard()
    {

        Map<Coordinates, Card> placedCards = this.boardModel.getPlacedCards();
        if (placedCards.isEmpty()) return;
        boardView.clear();

        Set<Integer> abscissaCoordinates = new HashSet<>();
        Set<Integer> ordinateCoordinates = new HashSet<>();

        for (Coordinates coord : placedCards.keySet())
        {
            abscissaCoordinates.add(coord.getX());
            ordinateCoordinates.add(coord.getY());
        }

        int maxAbscissa = Collections.max(abscissaCoordinates);
        int minAbscissa = Collections.min(abscissaCoordinates);
        int minOrdinate = Collections.min(ordinateCoordinates);
        int maxOrdinate = Collections.max(ordinateCoordinates);


        for (int j = maxOrdinate; j >= minOrdinate; j--)
        {
            for (int i = minAbscissa; i <= maxAbscissa; i++)
            {
                Card card = placedCards.get(new Coordinates(i, j));

                Coordinates coord = coordinatesConvertor.gameToScreenCoordinates(i, j, minAbscissa, maxOrdinate);
                boardView.add(new CardWithScreenCoordinates(coord.getX(), coord.getY(), card));

            }
        }

        repaint();
    }


    /**
     * Draws the board and the animation if needed
     *
     * @param g2d graphics2D of the panel
     */
    public void draw(Graphics2D g2d)
    {
        for (CardWithScreenCoordinates cardImage : boardView)
        {
            Card card = cardImage.getCard();
            int x = cardImage.getX();
            int y = cardImage.getY();

            if (card != null)
            {
                Image[] images = cardImageMatcher(card);
                g2d.drawImage(images[0], x, y, null);
                if ((gameState == GameState.MOVE_DONE || gameState == GameState.PLACE_DONE) && animate)
                {

                    if (numberOfFrame > 7)
                    {
                        numberOfFrame = 0;
                    }

                    Image[] anim = glitchAnimationMatcher(card);
                    g2d.drawImage(anim[numberOfFrame], x + (CARD_WIDTH - HOLOGRAM_WIDTH) / 2, y + (CARD_HEIGHT - HOLOGRAM_HEIGHT) / 2, null);


                } else
                {
                    numberOfFrame = 0;
                    g2d.drawImage(images[1], x + (CARD_WIDTH - HOLOGRAM_WIDTH) / 2, y + (CARD_HEIGHT - HOLOGRAM_HEIGHT) / 2, null);

                }

            }
            numberOfFrame++;
        }
        validate();
    }

    /**
     * Draws the moving/dragged card
     *
     * @param g2d graphics2D of the panel
     */
    private void drawDraggedCard(Graphics2D g2d)
    {
        if (cardImage != null)
        {
            g2d.drawImage(cardImageMatcher(cardImage.getCard())[0], cardImage.getX(), cardImage.getY(), null);
        }
    }

    /**
     * Draws the free emplacement position for placement or movement
     *
     * @param g2d graphics2D of the panel
     */
    private void drawFreeLocation(Graphics2D g2d)
    {
        if (gameState == GameState.MOVE || gameState == GameState.PLACE || gameState == GameState.FIRST_TURN)
        {
            if (placementTime)
            {
                g2d.setColor(Color.WHITE);

            } else
            {
                g2d.setColor(Color.YELLOW);
            }

            for (Coordinates coord : goodRequestsScreen)
            {
                //TODO replace by image
                g2d.drawRect(coord.getX(), coord.getY(), CARD_WIDTH, CARD_HEIGHT);
            }

        }
    }

    /**
     * Draws text instruction to help player to make his choice
     *
     * @param g2d graphics2D of the panel
     */
    private void drawTextInstruction(Graphics2D g2d)
    {
        if (shouldDrawText)
        {
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
            Font f = new Font(font.getFontName(), Font.PLAIN, 40);
            g2d.setFont(f);

            String str = "";
            if (gameState == GameState.MOVE)
            {
                str = "Time to move";

            } else if (gameState == GameState.PLACE)
            {
                str = "Time to place";

            }
            g2d.setColor(Color.WHITE);
            g2d.drawString(str, 42, 275);
        }
    }

    /**
     * Draws the background image
     *
     * @param g2d graphics2D of the panel
     */
    private void drawBackground(Graphics2D g2d)
    {
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }

    /**
     * Draws the current player hand on the bottom left of the screen
     *
     * @param g2d graphics2D of the panel
     */
    public void drawHand(Graphics2D g2d)
    {
        for (CardWithScreenCoordinates cardImage : handView)
        {
            g2d.drawImage(cardImageMatcher(cardImage.getCard())[0], cardImage.getX(), cardImage.getY(), null);
        }

    }

    /**
     * Draws the victory card of the current player on the top left of the screen
     *
     * @param g2d graphics2D of the panel
     */
    private void drawVictoryCard(Graphics2D g2d)
    {
        Card victoryCard = null;
        try
        {
            victoryCard = this.model.getCurrentPlayer().getVictoryCard();

        } catch (NullPointerException ignored)
        {

        }
        if (victoryCard != null)
        {
            Image[] images = cardImageMatcher(victoryCard);

            g2d.drawImage(images[0], 50, 100, null);
            g2d.drawImage(images[1], 50 + (CARD_WIDTH - HOLOGRAM_WIDTH) / 2, 100 + (CARD_HEIGHT - HOLOGRAM_HEIGHT) / 2, null);
        }

    }


    /**
     * Method called by the dispatching thread after a called of the repaint() method
     *
     * @param g graphics object provided by the AWT thread
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBackground(g2d);

        if (gameState != GameState.END_GAME)
        {
            drawFreeLocation(g2d);
            drawTextInstruction(g2d);
            draw(g2d);
            drawDraggedCard(g2d);
            drawHand(g2d);
            drawVictoryCard(g2d);

            if (displayScores)
            {
                endRoundScoreDrawer.drawEndRoundScores(g2d);
            }
        } else
        {
            endGameScoreDrawer.drawEndGameScores(g2d);
        }
    }

    /**
     * Match a card and its [stone,hologram] representation
     *
     * @param card the card
     * @return an Image[2] array containing the Stone and Hologram images of the card
     */
    private Image[] cardImageMatcher(Card card)
    {
        int index;

        // Row 1 (triangle filled)
        if (new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.FILLED).equals(card))
        {
            index = 0;
        } else if (new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED).equals(card))
        {
            index = 1;
        } else if (new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED).equals(card))
        {
            index = 2;
        }

        // Row 2 (triangle hollow)
        else if (new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 3;
        } else if (new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 4;
        } else if (new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 5;
        }

        // Row 3 (square filled)
        else if (new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED).equals(card))
        {
            index = 6;
        } else if (new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.FILLED).equals(card))
        {
            index = 7;
        } else if (new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED).equals(card))
        {
            index = 8;
        }
        // Row 4 (square hollow)
        else if (new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.HOLLOW).equals(card))
        {
            index = 9;
        } else if (new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW).equals(card))
        {
            index = 10;
        } else if (new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW).equals(card))
        {
            index = 11;
        }

        // Row 5 (circle filled)
        else if (new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED).equals(card))
        {
            index = 12;
        } else if (new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED).equals(card))
        {
            index = 13;
        } else if (new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED).equals(card))
        {
            index = 14;
        }


        // Row 6 (circle hollow)
        else if (new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 15;
        } else if (new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 16;
        } else if (new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 17;

        } else
        {
            index = 0;
        }
        return new Image[]{sprite.get(index), spriteHologram.get(index)};

    }

    /**
     * Match a card and its hologram animation
     *
     * @param card the card
     * @return an Image array containing every frame of the hologram glitch animation
     */
    private Image[] glitchAnimationMatcher(Card card)
    {
        int rowIndex;

        // Row 1 (triangle filled)
        if (new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.FILLED).equals(card))
        {
            rowIndex = 0;
        } else if (new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED).equals(card))
        {
            rowIndex = 1;
        } else if (new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED).equals(card))
        {
            rowIndex = 2;
        }

        // Row 2 (triangle hollow)
        else if (new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.HOLLOW).equals(card))
        {
            rowIndex = 3;
        } else if (new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW).equals(card))
        {
            rowIndex = 4;
        } else if (new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW).equals(card))
        {
            rowIndex = 5;
        }

        // Row 3 (square filled)
        else if (new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED).equals(card))
        {
            rowIndex = 6;
        } else if (new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.FILLED).equals(card))
        {
            rowIndex = 7;
        } else if (new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED).equals(card))
        {
            rowIndex = 8;
        }
        // Row 4 (square hollow)
        else if (new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.HOLLOW).equals(card))
        {
            rowIndex = 9;
        } else if (new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW).equals(card))
        {
            rowIndex = 10;
        } else if (new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW).equals(card))
        {
            rowIndex = 11;
        }

        // Row 5 (circle filled)
        else if (new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED).equals(card))
        {
            rowIndex = 12;
        } else if (new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED).equals(card))
        {
            rowIndex = 13;
        } else if (new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED).equals(card))
        {
            rowIndex = 14;
        }


        // Row 6 (circle hollow)
        else if (new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW).equals(card))
        {
            rowIndex = 15;
        } else if (new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW).equals(card))
        {
            rowIndex = 16;
        } else if (new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW).equals(card))
        {
            rowIndex = 17;

        } else
        {
            rowIndex = 0;
        }


        return spriteGlitchAnimations[rowIndex];


    }


    /**
     * Generate the empty location for a movement or a place
     * Those are put in the goodRequestScreen attributes
     * They are represented in the game with yellow and white square
     */
    private void generateFreeLocation()
    {
        if (!boardModel.getPlacedCards().isEmpty())
        {
            AbstractBoard board = null;
            try
            {
                board = (AbstractBoard) boardModel.clone();
            } catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
            List<Coordinates> goodRequests = new ArrayList<>();
            List<Coordinates> cordsMap = new ArrayList<>(board.getPlacedCards().keySet());

            if (!placementTime)
            {
                Map<Coordinates, Card> placedCards = board.getPlacedCards();


                Set<Integer> abscissaCoordinates = new HashSet<>();
                Set<Integer> ordinateCoordinates = new HashSet<>();

                for (Coordinates coord : placedCards.keySet())
                {
                    abscissaCoordinates.add(coord.getX());
                    ordinateCoordinates.add(coord.getY());
                }

                int minAbscissa = Collections.min(abscissaCoordinates);
                int maxOrdinate = Collections.max(ordinateCoordinates);
                int x = cardImage.getX();
                int y = cardImage.getY();
                Coordinates coordinates = coordinatesConvertor.screenToGameCoordinates(x, y, minAbscissa, maxOrdinate);
                cordsMap.remove(coordinates);
                board.getPlacedCards().remove(coordinates);
            }


            int testX = Coordinates.smallestAbscissa(cordsMap) - 1;
            int testY = Coordinates.smallestOrdinate(cordsMap) - 1;
            Coordinates testCoord = new Coordinates(testX, testY);

            while (testY <= Coordinates.biggestOrdinate(cordsMap) + 1)
            {
                testCoord.setY(testY);
                testX = Coordinates.smallestAbscissa(cordsMap) - 1;
                while (testX <= Coordinates.biggestAbscissa(cordsMap) + 1)
                {
                    testCoord.setX(testX);
                    if (!board.getPlacedCards().containsKey(testCoord))
                    {
                        if (board.isCardAdjacent(testCoord) && board.isCardInTheLayout(testCoord))
                        {
                            goodRequests.add(new Coordinates(testCoord.getX(), testCoord.getY()));
                        }
                    }
                    testX += 1;
                }
                testY += 1;
            }

            goodRequestsScreen = new ArrayList<>();

            for (Coordinates coord : goodRequests)
            {
                goodRequestsScreen.add(coordinatesConvertor.gameToScreenCoordinates(coord.getX(), coord.getY(), Coordinates.smallestAbscissa(cordsMap), Coordinates.biggestOrdinate(cordsMap)));


            }

        }
    }

    /**
     * Creates the needed buttons with their location and their associated action listener
     */
    private void instantiateButtons()
    {
        moveButton = new MyButton("", "buttons/move.png", "buttons/move_hover.png", "buttons/move_hover.png");
        secondMoveButton = new MyButton("", "buttons/move.png", "buttons/move_hover.png", "buttons/move_hover.png");

        endTurnButton = new MyButton("", "buttons/end_turn.png", "buttons/end_turn_hover.png", "buttons/end_turn_hover.png");
        placeButton = new MyButton("", "buttons/place.png", "buttons/place_hover.png", "buttons/place_hover.png");
        endRoundButton = new MyButton("", "buttons/next_round.png", "buttons/next_round_hover.png", "buttons/next_round_hover.png");


        // hide every button at the beginning
        moveButton.setVisible(false);
        endTurnButton.setVisible(false);
        placeButton.setVisible(false);
        endRoundButton.setVisible(false);
        secondMoveButton.setVisible(false);


        // Set them to their correct location
        placeButton.setBounds(1000, PLAYER_HAND_Y - 80, 293, 100);
        moveButton.setBounds(1000, PLAYER_HAND_Y + 50, 293, 100);
        endTurnButton.setBounds(1000, PLAYER_HAND_Y + 50, 293, 100);
        secondMoveButton.setBounds(1000, PLAYER_HAND_Y - 80, 293, 100);
        endRoundButton.setBounds(499, 760, 407, 100);

        // add them in the panel
        add(moveButton);
        add(secondMoveButton);
        add(placeButton);
        add(endRoundButton);
        add(endTurnButton);

        // Set correct listener for each of them
        placeButton.addActionListener(actionEvent -> {


            moveButton.setVisible(false);
            placeButton.setVisible(false);
            new Thread(THREAD_FROM_GAME_VIEW_NAME)
            {
                @Override
                public void run()
                {
                    controller.askChoice(1, 2);
                }
            }.start();
        });

        moveButton.addActionListener(actionEvent -> {
            moveButton.setVisible(false);
            placeButton.setVisible(false);
            new Thread(THREAD_FROM_GAME_VIEW_NAME)
            {
                @Override
                public void run()
                {
                    controller.askChoice(1, 1);
                }
            }.start();
        });


        endTurnButton.addActionListener(actionEvent -> {
            moveButton.setVisible(false);
            endTurnButton.setVisible(false);
            new Thread(THREAD_FROM_GAME_VIEW_NAME)
            {
                @Override
                public void run()
                {
                    controller.askChoice(2, 2);
                }
            }.start();
        });

        secondMoveButton.addActionListener(actionEvent -> {
            moveButton.setVisible(false);
            endTurnButton.setVisible(false);
            new Thread(THREAD_FROM_GAME_VIEW_NAME)
            {
                @Override
                public void run()
                {
                    controller.askChoice(2, 1);
                }
            }.start();
        });

        endRoundButton.addActionListener(actionEvent -> {
            endRoundButton.setVisible(false);
            displayScores = false;
            repaint();
            new Thread(THREAD_FROM_GAME_VIEW_NAME)
            {
                @Override
                public void run()
                {
                    controller.play();
                }
            }.start();

        });
    }


    /**
     * Entry point for the game only (without the initialization menu)
     * The game will be played for 2 real players in a circle board
     * with a basic score calculator and with 2 views (console and swing)
     */
    public static void main(String[] args)
    {


        List<Player> ps = new ArrayList<>();
        AbstractBoard rb = new CircleBoard();
        ps.add(new RealPlayer("Jacques", rb));
        ps.add(new RealPlayer("Baptiste", rb));
        ScoreCalculatorVisitor visitor = new ScoreCalculatorVisitor();


        AbstractShapeUpGame model = new ShapeUpGame(visitor, ps, rb);
        Set<GameView> gameViewSet = new HashSet<>();
        SwingHmiView view = new SwingHmiView(rb, model);
        GameConsoleView v = new GameConsoleView(model, rb);

        gameViewSet.add(view);
        gameViewSet.add(v);

        JFrame frame = new JFrame();

        frame.add(view);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);

        GameController controller = new ShapeUpGameController(model, gameViewSet);

        view.setController(controller);
        v.setController(controller);
        model.addPropertyChangeListener(view);
        model.addPropertyChangeListener(v);


        frame.setVisible(true);
        model.setState(GameState.FIRST_TURN);


    }

}

