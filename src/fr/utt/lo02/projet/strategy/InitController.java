package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.GameView;
import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.CircleBoard;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.TriangleBoard;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorWithBonusVisitor;
import fr.utt.lo02.projet.game.*;

import javax.swing.*;

import java.io.IOException;
import java.util.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class InitController
{

    private IBoardVisitor visitor;
    private GameMode gm;
    private List<Player> players;
    private AbstractBoard board;

    private Set<InitView> views;
    private InitModel initModel;
    private JFrame frame;

    public InitController(InitModel model, Set<InitView> viewSet, JFrame frame)
    {

        this.initModel = model;
        this.views = viewSet;
        this.frame = frame;
        players = new ArrayList<>();

    }

    public void startMenu(int menu)
    {
        switch (menu)
        {
            case 1 -> initModel.setState(InitState.GAME_MODE_CHOICE);
            case 2 -> initModel.setState(InitState.RULES);
            case 3 -> initModel.setState(InitState.CREDITS);
            case 4 -> initModel.setState(InitState.QUIT);
            default -> initModel.setState(InitState.START_MENU);
        }
    }


    public void setGameMode(int mode)
    {
        switch (mode)
        {
            case 0 -> {
                initModel.setState(InitState.START_MENU);
                return;
            }
            case 1 -> gm = GameMode.NORMAL;
            case 2 -> gm = GameMode.ADVANCED;
            case 3 -> gm = GameMode.NO_ADJACENCY;
            default -> {
                initModel.setState(InitState.GAME_MODE_CHOICE);
                return;
            }
        }
        initModel.setState(InitState.SCORE_CALCULATOR_CHOICE);

    }


    public void setScoreCalculator(int visitor)
    {
        switch (visitor)
        {
            case 0 -> {
                initModel.setState(InitState.GAME_MODE_CHOICE);
                return;
            }
            case 1 -> this.visitor = new ScoreCalculatorVisitor();
            case 2 -> this.visitor = new ScoreCalculatorWithBonusVisitor();
            default -> {
                initModel.setState(InitState.SCORE_CALCULATOR_CHOICE);
                return;
            }
        }
        initModel.setState(InitState.SHAPE_BOARD_CHOICE);
    }

    public void shapeBoard(int board)
    {
        switch (board)
        {
            case 0 -> {
                initModel.setState(InitState.SCORE_CALCULATOR_CHOICE);
                return;
            }
            case 1 -> this.board = new RectangleBoard();
            case 2 -> this.board = new TriangleBoard();
            case 3 -> this.board = new CircleBoard();
            default -> {
                initModel.setState(InitState.SHAPE_BOARD_CHOICE);
                return;
            }
        }
        initModel.setState(InitState.PLAYER_CHOICE);
    }

    public void setNbPlayers(int nb) {
    	switch (nb) {
    	case 0:
    		initModel.setState(InitState.SHAPE_BOARD_CHOICE);
    		return;
    	case 2,3:
    		return;
    	case 1:
    		initModel.setState(InitState.PLAYER_CHOICE);
    		return;
    	}
    		
    }
    public void setPlayer(Map<Integer, String> realPlayers, Map<Integer, String> virtualPlayers)
    {


        int playerNumber = realPlayers.size() + virtualPlayers.size();


        for (int i = 1; i <= playerNumber; i++)
        {
            boolean real = realPlayers.containsKey(i);

            if (real)
            {
                players.add(new RealPlayer(realPlayers.get(i), board));

            } else
            {

                if (virtualPlayers.get(i).equals(InitConsoleView.EASY))
                {
                    players.add(new VirtualPlayer(virtualPlayers.get(i), board, new RandomStrategy()));

                } else
                {
                    players.add(new VirtualPlayer(virtualPlayers.get(i), board, new DifficultStrategy(visitor)));
                }
            }

        }
        initModel.setState(InitState.INIT_DONE);

    }

    public void quit()
    {
        System.exit(0);
    }

    public void launch()
    {
        for (InitView view : views)
        {
            initModel.removePropertyChangeListener(view);
        }

        AbstractShapeUpGame game = null;
        switch (gm)
        {

            case NORMAL -> {
                game = new ShapeUpGame(visitor,players, board);
            }
            case ADVANCED -> {
                game = new ShapeUpGameAdvanced(visitor,players, board);

            }
            case NO_ADJACENCY -> {
                game = new ShapeUpGameWithoutAdjacencyRule(visitor,players, board);

            }
        }
        Set<GameView> gameViewSet = new HashSet<>();
        RectangleBoardFrameTest hmiView = new RectangleBoardFrameTest(board, game);
//        GameConsoleView consoleView = new GameConsoleView(game, board);

        gameViewSet.add(hmiView);
//        gameViewSet.add(consoleView);


        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();
        frame.add(hmiView);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);


        GameController sugc;
        switch (gm)
        {
            case NORMAL -> {
                sugc = new ShapeUpGameController(game, gameViewSet);
            }
            case ADVANCED -> {
                sugc = new AdvancedShapeUpGameController(game, gameViewSet);

            }
            default -> throw new IllegalStateException("Unexpected value: " + gm);
        }

        hmiView.setController(sugc);
//        consoleView.setController(sugc);
        game.addPropertyChangeListener(hmiView);
//        initModel.addPropertyChangeListener(consoleView);


        frame.setVisible(true);
        game.setState(GameState.FIRST_TURN);


    }

    public static void main(String[] args) throws IOException
    {
        InitModel model = new InitModel();
 //      InitConsoleView v = new InitConsoleView();
       InitFrameView view = new InitFrameView();
       JFrame frame = new JFrame("Shape Up");

        Set<InitView> icv = new HashSet<>();
        icv.add(view);
//        icv.add(v);
        
        frame.add(view);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        InitController ic = new InitController(model, icv, frame);
        view.setController(ic);
 //      v.setController(ic);
       model.addPropertyChangeListener(view);
//       model.addPropertyChangeListener(v);

        model.setState(InitState.START_MENU);
        frame.setVisible(true);
    }

}
