package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.GameView;
import fr.utt.lo02.projet.board.*;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class RectangleBoardFrameTest extends JPanel implements GameView, MouseListener, MouseMotionListener
{

    private static final int CARD_WIDTH = 64;
    private static final int OFFSET_X = 32;
    private static final int OFFSET_Y = 32;
    private static final int CARD_HEIGHT = 64;
    private static final int LEFT_BOARD_OFFSET = 450;
    private static final int TOP_BOARD_OFFSET = 150;
    private static final int PLAYER_HAND_Y = 600;

    private final List<Image> sprite;

    int xadj;
    int yadj;
    int prevX;
    int prevY;
    int currX;
    int currY;
    int cardIndex;

    private GameController controller;

    private final AbstractShapeUpGame model;

    private final AbstractBoard boardModel /*= new RectangleBoard()*/;

    private final CopyOnWriteArrayList<CardImage> boardView;
    private final List<CardImage> handView;

    private CardImage cardImage;
    private boolean placementTime;
    private GameState gs;
    List<Coordinates> goodRequestsScreen = new CopyOnWriteArrayList<>();

    public RectangleBoardFrameTest(AbstractBoard modelboard, AbstractShapeUpGame game)
    {
        this.model = game;
        this.boardModel = modelboard;
        Dimension preferredSize = new Dimension(1280, 720);


        setPreferredSize(preferredSize);
        setBounds(0, 0, 1280, 720);
        sprite = splitSprite();

        UIManager.put("Slider.onlyLeftMouseButtonDrag", Boolean.TRUE);
        //TODO remove those (in prop change)
//        addMouseListener(this);
//        addMouseMotionListener(this);
        setBackground(Color.getColor("brown"));


        boardView = new CopyOnWriteArrayList<>();
        handView = Collections.synchronizedList(new ArrayList<>());


        placementTime = false;

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        System.out.println("notified" + evt.getNewValue() + " " + evt.getPropertyName());

        gs = (GameState) evt.getNewValue();
        System.out.println(gs);

        switch (gs)
        {


            case MOVE -> {

               goodRequestsScreen.clear();
                System.out.println("move");
                updateDisplayBoard();
                placementTime = false;
                removeMouseMotionListener(this);
                removeMouseListener(this);
                addMouseListener(this);
                addMouseMotionListener(this);

            }
            case PLACE -> {
                generateFreeLocation();
                System.out.println("place");
                removeMouseMotionListener(this);
                removeMouseListener(this);
                addMouseListener(this);
                addMouseMotionListener(this);
                updateDisplayBoard();
                updateDisplayHand();
                placementTime = true;


            }
            case PLACE_DONE -> {



                updateDisplayBoard();

                System.out.println("placed");
                removeMouseListener(this);
                removeMouseMotionListener(this);





            }
            case MOVE_DONE -> {


                updateDisplayBoard();

                System.out.println("moved");
                removeMouseListener(this);
                removeMouseMotionListener(this);





            }

            case ACTION_FAILED -> {

            }
            case FIRST_TURN -> {

                goodRequestsScreen.clear();
                goodRequestsScreen.add(new Coordinates(LEFT_BOARD_OFFSET, TOP_BOARD_OFFSET));
                repaint();


                this.controller.play();
            }
            case FIRST_CHOICE -> {
                removeMouseListener(this);
                removeMouseMotionListener(this);
                addMouseListener(this);
                addMouseMotionListener(this);
                goodRequestsScreen.clear();
                goodRequestsScreen.add(new Coordinates(LEFT_BOARD_OFFSET, TOP_BOARD_OFFSET));
                repaint();



                placementTime = true;
                this.controller.askChoice(1, 2);
                // Allow place and move listener ?
                // If user click on hand => place case
                // if click on board => move case

            }
            case SECOND_CHOICE -> {
                this.controller.askChoice(2, 1);

                // Allow display end turn button and ?
            }
            case END_TURN -> {
                removeMouseMotionListener(this);
                removeMouseListener(this);
                updateDisplayBoard();
                this.controller.endTurn();
                // Display end turn (next player animation I guess)
            }
            case CARD_DRAW, VICTORY_CARD -> {

                //TODO add draw animation
                repaint();

            }
            // ?
            case END_ROUND -> {
                // Display end round (scores...)

                goodRequestsScreen.clear();
                updateDisplayBoard();
                boardView.clear();
                handView.clear();
//                validate();
//                repaint();

                System.out.println("The round is finished");
                System.out.println();

//                JButton ok = new JButton("Ok");
//                ok.setBounds(700,7000,50,50);
//                ok.addActionListener(new ActionListener()
//                {
//                    @Override
//                    public void actionPerformed(ActionEvent actionEvent)
//                    {
//                        controller.endRound();
//
//                    }
//                });
//                add(ok);
                this.controller.endRound();


            }
            case END_GAME -> {
                // Display end game (every scores, + main menu ?)

            }
        }

    }

    public void mouseClicked(MouseEvent mouseEvent)
    {

    }

    public void mousePressed(MouseEvent mouseEvent)
    {

        if (mouseEvent.getButton() != MouseEvent.BUTTON1) return;
        cardImage = null;

        // Select card among player's hand
        if (placementTime)
        {

            for (CardImage cardImage : handView)
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

            //cardImage = new CardImage(cardImage.getX(), cardImage.getY(), null));

        } // Select card among board
        else
        {
            // TODO optimize
            for (CardImage cardImage : boardView)
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


            // boardView.set(boardView.indexOf(cardImage), new CardImage(cardImage.getX(), cardImage.getY(), null));
            boardView.remove(cardImage);
//            generateFreeLocation();
//
//            repaint();

        }


        prevX = cardImage.getX();
        prevY = cardImage.getY();
        xadj = prevX - mouseEvent.getX();
        yadj = prevY - mouseEvent.getY();
        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        System.out.println(cardImage);


    }

    public void mouseDragged(MouseEvent mouseEvent)
    {


        if (cardImage == null) return;

        currX = mouseEvent.getX();
        currY = mouseEvent.getY();
        cardImage = new CardImage(currX + xadj, currY + yadj, cardImage.getCard());


        // CREATE EMPTY LOCALISATIONS AND DISPLAY


        repaint();
    }

    public void mouseReleased(MouseEvent mouseEvent)
    {

        if (mouseEvent.getButton() != MouseEvent.BUTTON1) return;


        if (cardImage == null) return;
        boolean hasBeenPlaced = false;

        if (boardView.isEmpty())
        {
            boardView.add(new CardImage(LEFT_BOARD_OFFSET, TOP_BOARD_OFFSET, cardImage.getCard()));
            hasBeenPlaced = true;
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            CardImage ci = cardImage;
            cardImage = null;
            repaint();
            this.controller.askPlace(0, 0, cardIndex);

            //TODO check
            return;

        }
        // Put image in an empty location if it exists under cursor
        for (Coordinates cimage : goodRequestsScreen)
        {

            if (mouseEvent.getX() >= cimage.getX() && mouseEvent.getX() <= cimage.getX() + CARD_WIDTH)
            {
                if (mouseEvent.getY() >= cimage.getY() && mouseEvent.getY() <= cimage.getY() + CARD_HEIGHT)
                {
                    boardView.add(new CardImage(cimage.getX(), cimage.getY(), cardImage.getCard()));
                    hasBeenPlaced = true;
                    break;
                }
            }

        }
        // Else put back to previous location
        if (!hasBeenPlaced)
        {
            //TODO animation instead of quick back to previous coordinates
//            cardImage = new CardImage((cardImage.getX() + prevX)/2, (cardImage.getY() + prevY) /2, cardImage.getCard());
//            repaint();
            if (placementTime)
            {
                handView.add(new CardImage(prevX, prevY, cardImage.getCard()));

            } else
            {
                boardView.add(new CardImage(prevX, prevY, cardImage.getCard()));

            }

        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        CardImage ci = cardImage;
        cardImage = null;
        repaint();

        int x = currX;
        int y = currY;


        Map<Coordinates, Card> placedCards = this.boardModel.getPlacedCards();

        System.out.println(placedCards.size());
        Set<Integer> abscissaCoordinates = new HashSet<>();
        Set<Integer> ordinateCoordinates = new HashSet<>();

        for (Coordinates coord : placedCards.keySet())
        {
            abscissaCoordinates.add(coord.getX());
            ordinateCoordinates.add(coord.getY());
        }

        int minAbscissa = Collections.min(abscissaCoordinates);
        int maxOrdinate = Collections.max(ordinateCoordinates);


        Coordinates coordinates = screenToGameCoordinates(x, y, minAbscissa, maxOrdinate);
        if (placementTime)
        {
            this.controller.askPlace(coordinates.getX(), coordinates.getY(), cardIndex);

        } else
        {
            Coordinates previous = screenToGameCoordinates(prevX, prevY, minAbscissa, maxOrdinate);
            this.controller.askMove(previous.getX(), previous.getY(), coordinates.getX(), coordinates.getY());
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


    public void mouseMoved(MouseEvent mouseEvent)
    {

    }


    @Override
    public void displayMoveFailed(PlaceRequestResult prr)
    {

        System.out.println(prr);
    }

    @Override
    public void displayPlaceFailed(MoveRequestResult mrr)
    {
        System.out.println(mrr);


    }

    @Override
    public void displayScoresEndRound()
    {

    }

    @Override
    public void displayBoard()
    {

        updateDisplayBoard();
        repaint();
    }

    @Override
    public void setController(GameController sugc)
    {
        this.controller = sugc;
    }


    private void updateDisplayHand()
    {

        List<Card> playerHand = this.model.getCurrentPlayer().getPlayerHand();

        handView.clear();

        for (int j = 0; j < playerHand.size(); j++)
        {
            int x = j * (CARD_WIDTH + OFFSET_X) + LEFT_BOARD_OFFSET;
            handView.add(new CardImage(x, PLAYER_HAND_Y, playerHand.get(j)));
        }
        repaint();
    }

    private void updateDisplayBoard()
    {

        Map<Coordinates, Card> placedCards = this.boardModel.getPlacedCards();
        if (placedCards.isEmpty()) return;
        boardView.clear();

        System.out.println(placedCards.size());
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

                Coordinates coord = gameToScreenCoordinates(i, j, minAbscissa, maxOrdinate);
                boardView.add(new CardImage(coord.getX(), coord.getY(), card));

            }
        }
        System.out.println("REPAINT");
        repaint();
    }

    // Draw the board + the dragged card
    public void draw(Graphics2D g2d)
    {
        //TODO draw background here

        // Draw location
        if (gs == GameState.MOVE || gs == GameState.PLACE || gs == GameState.FIRST_TURN)
        {
            g2d.setColor(Color.YELLOW);
            for (Coordinates coord : goodRequestsScreen)
            {
                //TODO replace by image

                g2d.fillRect(coord.getX(), coord.getY(), CARD_WIDTH, CARD_HEIGHT);
            }
        }

        System.out.println("BOARD VIEW + size"+ boardView.size());
        for (CardImage cardImage : boardView)
        {
            Card card = cardImage.getCard();
            int x = cardImage.getX();
            int y = cardImage.getY();

            if (card != null)
            {
                g2d.drawImage(cardImageMatcher(card), x, y, null);
            }
        }


        if (cardImage != null)
        {
            System.out.println("card above!!!!!");
            g2d.drawImage(cardImageMatcher(cardImage.getCard()), cardImage.getX(), cardImage.getY(), null);
        }

    }

    public void drawHand(Graphics2D g2d)
    {

        for (CardImage cardImage : handView)
        {
            g2d.drawImage(cardImageMatcher(cardImage.getCard()), cardImage.getX(), cardImage.getY(), null);
        }

    }

    private void drawVictoryCard(Graphics2D g2d)
    {
        Card victoryCard = this.model.getCurrentPlayer().getVictoryCard();
        //Card victoryCard = new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW);
        if (victoryCard != null)
            g2d.drawImage(cardImageMatcher(victoryCard), 50, PLAYER_HAND_Y, null);

    }


    public List<Image> splitSprite()
    {
        int[][] spriteSheetCoords = {{0, 0, 32, 32}, {32, 0, 32, 32}, {64, 0, 32, 32},
                {0, 32, 32, 32}, {32, 32, 32, 32}, {64, 32, 32, 32},
                {0, 64, 32, 32}, {32, 64, 32, 32}, {64, 64, 32, 32},

                {0, 96, 32, 32}, {32, 96, 32, 32}, {64, 96, 32, 32},
                {0, 128, 32, 32}, {32, 128, 32, 32}, {64, 128, 32, 32},
                {0, 160, 32, 32}, {32, 160, 32, 32}, {64, 160, 32, 32},

        };

        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File("res/cards_rocks_rework.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        List<Image> imgArray = new ArrayList<>();
        for (int[] a : spriteSheetCoords)
        {

            Image img2 = img.getSubimage(a[0], a[1], a[2], a[3]);
            img2 = img2.getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            imgArray.add(img2);
        }

        return imgArray;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setColor(getBackground());

        draw((Graphics2D) g);
        drawHand((Graphics2D) g);
        drawVictoryCard((Graphics2D) g);

    }


    private Image cardImageMatcher(Card card)
    {
        int index;
        if (new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 0;
        } else if (new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 1;
        } else if (new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 2;
        } else if (new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED).equals(card))
        {
            index = 4;
        } else if (new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.FILLED).equals(card))
        {
            index = 3;
        } else if (new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED).equals(card))
        {
            index = 5;
        }else if (new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW).equals(card))
        {
            index = 6;
        } else if (new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.HOLLOW).equals(card))
        {
            index = 7;
        } else if (new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW).equals(card))
        {
            index = 8;
        } else if (new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.FILLED).equals(card))
        {
            index = 9;
        } else if (new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED).equals(card))
        {
            index = 10;
        } else if (new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED).equals(card))
        {
            index = 11;
        }else if (new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 12;
        } else if (new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 13;
        } else if (new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW).equals(card))
        {
            index = 14;

        }   else if (new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED).equals(card))
        {
            index = 15;
        } else if (new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED).equals(card))
        {
            index = 16;
        } else if (new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED).equals(card))
        {
            index = 17;
        } else
        {
            System.out.println(card);
            System.out.println("ERROR, card unavailable");
            index = 0;
        }
        return sprite.get(index);
    }


    // TODO: remove (test purpose only)
    private void fakeUpdate()
    {
        this.boardModel.addCard(new Coordinates(0, 0), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        this.boardModel.addCard(new Coordinates(1, 0), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        this.boardModel.addCard(new Coordinates(1, 1), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        this.boardModel.addCard(new Coordinates(0, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        this.boardModel.addCard(new Coordinates(3, 1), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        this.boardModel.addCard(new Coordinates(2, 1), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));

        // redraw the layout
        updateDisplayHand();
        updateDisplayBoard();

    }

    // TODO: remove (test purpose only)
    private void fakeUpdate2()
    {
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        this.boardModel.removeCard(new Coordinates(0, 0), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));

        this.boardModel.addCard(new Coordinates(3, 2), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));
        this.boardModel.addCard(new Coordinates(2, 2), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));
        System.out.println(this.boardModel.getPlacedCards().size());

        updateDisplayBoard();
        updateDisplayHand();
        // redraw the layout
    }



    private Coordinates gameToScreenCoordinates(int i, int j, int minAbscissa, int maxOrdinate)
    {
        int x = (i - minAbscissa) * (CARD_WIDTH + OFFSET_X);
        x += LEFT_BOARD_OFFSET;
        int y = (maxOrdinate - j) * (CARD_HEIGHT + OFFSET_Y);
        y += TOP_BOARD_OFFSET;

        return new Coordinates(x, y);
    }

    private Coordinates screenToGameCoordinates(int x, int y, int minAbscissa, int maxOrdinate)
    {
        int i = ((x - LEFT_BOARD_OFFSET) / (CARD_WIDTH + OFFSET_X)) + minAbscissa;
        int j = maxOrdinate - ((y - TOP_BOARD_OFFSET) / (CARD_HEIGHT + OFFSET_Y));

        if (x < LEFT_BOARD_OFFSET)
        {
            i = minAbscissa - 1;
        } else if (y < TOP_BOARD_OFFSET)
        {
            j = maxOrdinate + 1;

        }


        return new Coordinates(i, j);
    }

    private void generateFreeLocation()
    {
        if (!boardModel.getPlacedCards().isEmpty())
        {
            AbstractBoard board =  boardModel;
            List<Coordinates> coordsMap = new ArrayList<Coordinates>(board.getPlacedCards().keySet());
            List<Coordinates> goodRequests = new ArrayList<Coordinates>();

//            if (!placementTime)
//            {
//                Map<Coordinates, Card> placedCards = this.boardModel.getPlacedCards();
//
//
//                Set<Integer> abscissaCoordinates = new HashSet<>();
//                Set<Integer> ordinateCoordinates = new HashSet<>();
//
//                for (Coordinates coord : placedCards.keySet())
//                {
//                    abscissaCoordinates.add(coord.getX());
//                    ordinateCoordinates.add(coord.getY());
//                }
//
//                int minAbscissa = Collections.min(abscissaCoordinates);
//                int maxOrdinate = Collections.max(ordinateCoordinates);
//                int x = cardImage.getX();
//                int y = cardImage.getY();
//                Coordinates coordinates = screenToGameCoordinates(x,y, minAbscissa, maxOrdinate);
//                coordsMap.remove(coordinates);
//            }


            int testX = Coordinates.smallestAbscissa(coordsMap) - 1;
            int testY = Coordinates.smallestOrdinate(coordsMap) - 1;
            Coordinates testCoord = new Coordinates(testX, testY);

            while (testY <= Coordinates.biggestOrdinate(coordsMap) + 1)
            {
                testCoord.setY(testY);
                testX = Coordinates.smallestAbscissa(coordsMap) - 1;
                while (testX <= Coordinates.biggestAbscissa(coordsMap) + 1)
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

            goodRequestsScreen = new ArrayList<Coordinates>();

            for (Coordinates coord : goodRequests)
            {
                goodRequestsScreen.add(gameToScreenCoordinates(coord.getX(), coord.getY(), Coordinates.smallestAbscissa(coordsMap), Coordinates.biggestOrdinate(coordsMap)));


            }

        }
    }

    public static void main(String[] args)
    {


        List<Player> ps = new ArrayList<>();
        AbstractBoard rb = new TriangleBoard();
        ps.add(new RealPlayer("Jacques", rb));
//        ps.add(new RealPlayer("Baptiste", rb));
//		ps.add(new RealPlayer("Th1", rb));
//		ps.add(new RealPlayer("Aaa", rb));
        ScoreCalculatorVisitor visitor = new ScoreCalculatorVisitor();

        ps.add(new VirtualPlayer("ord1", rb, new RandomStrategy()));
//        ps.add(new VirtualPlayer("ord2", rb, new RandomStrategy()));
        AbstractShapeUpGame model = new ShapeUpGame(visitor, ps, rb);
        RectangleBoardFrameTest view = new RectangleBoardFrameTest(rb, model);


        JFrame frame = new JFrame();

        frame.add(view);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
//        comp.fakeUpdate();

        GameController sugc = new ShapeUpGameController(model, view);

        view.setController(sugc);
        model.addPropertyChangeListener(view);


        frame.setVisible(true);
        model.setState(GameState.FIRST_TURN);


//        comp.fakeUpdate2();
    }

}

