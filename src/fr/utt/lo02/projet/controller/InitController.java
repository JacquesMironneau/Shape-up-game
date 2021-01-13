package fr.utt.lo02.projet.controller;

import fr.utt.lo02.projet.model.InitModel;
import fr.utt.lo02.projet.model.InitState;
import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.CircleBoard;
import fr.utt.lo02.projet.model.board.RectangleBoard;
import fr.utt.lo02.projet.model.board.TriangleBoard;
import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.model.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.model.board.visitor.ScoreCalculatorWithBonusVisitor;
import fr.utt.lo02.projet.model.game.*;
import fr.utt.lo02.projet.model.player.Player;
import fr.utt.lo02.projet.model.player.RealPlayer;
import fr.utt.lo02.projet.model.player.VirtualPlayer;
import fr.utt.lo02.projet.model.strategy.*;
import fr.utt.lo02.projet.view.*;
import fr.utt.lo02.projet.view.console.GameConsoleView;
import fr.utt.lo02.projet.view.console.InitConsoleView;
import fr.utt.lo02.projet.view.hmi.InitFrameView;
import fr.utt.lo02.projet.view.hmi.SwingHmiView;

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
    private boolean lockLaunch;

    public InitController(InitModel model, Set<InitView> viewSet, JFrame frame)
    {

        this.initModel = model;
        this.views = viewSet;
        this.frame = frame;
        players = new ArrayList<>();
        lockLaunch = false;

//        playSound();

    }



    public synchronized void startMenu(int menu)
    {
        reset();
        switch (menu)
        {
            case 1 -> initModel.setState(InitState.GAME_MODE_CHOICE);
            case 2 -> initModel.setState(InitState.RULES);
            case 3 -> initModel.setState(InitState.CREDITS);
            case 4 -> initModel.setState(InitState.QUIT);
            default -> initModel.setState(InitState.START_MENU);
        }
    }


    public synchronized void setGameMode(int mode)
    {
        reset();

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


    public synchronized void setScoreCalculator(int visitor)
    {
        reset();

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

    public synchronized void shapeBoard(int board)
    {
        reset();

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

    public synchronized void setNbPlayers(int nb)
    {
//        reset();

        switch (nb)
        {
            case 0:
                initModel.setState(InitState.SHAPE_BOARD_CHOICE);
                return;
            case 2, 3:
                return;
            case 1:
                initModel.setState(InitState.PLAYER_CHOICE);
        }

    }

    public synchronized void setPlayer(Map<Integer, String> realPlayers, Map<Integer, String> virtualPlayers)
    {

        reset();

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
                    players.add(new VirtualPlayer("Player" + i, board, new RandomStrategy()));

                } else
                {
                    players.add(new VirtualPlayer("Player" + i, board, new DifficultStrategy(visitor)));
                }
            }

        }
        initModel.setState(InitState.INIT_DONE);

    }

    public synchronized void quit()
    {
        System.exit(0);
    }

    public synchronized void launch()
    {
        reset();

        if (!lockLaunch)
        {
            lockLaunch = true;
            for (InitView view : views)
            {
                initModel.removePropertyChangeListener(view);
            }

            AbstractShapeUpGame game = null;
            switch (gm)
            {

                case NORMAL -> game = new ShapeUpGame(visitor, players, board);
                case ADVANCED -> game = new ShapeUpGameAdvanced(visitor, players, board);
                case NO_ADJACENCY -> game = new ShapeUpGameWithoutAdjacencyRule(visitor, players, board);
            }
            Set<GameView> gameViewSet = new HashSet<>();
            SwingHmiView hmiView = new SwingHmiView(board, game);
            GameConsoleView consoleView = new GameConsoleView(game, board);

            gameViewSet.add(hmiView);
            gameViewSet.add(consoleView);


            frame.getContentPane().removeAll();
            frame.getContentPane().repaint();
            frame.add(hmiView);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);


            GameController sugc;
            if (gm == GameMode.ADVANCED)
            {
                sugc = new AdvancedShapeUpGameController(game, gameViewSet);
            } else
            {
                sugc = new ShapeUpGameController(game, gameViewSet);
            }

            hmiView.setController(sugc);
            consoleView.setController(sugc);
            game.addPropertyChangeListener(hmiView);
            game.addPropertyChangeListener(consoleView);

            frame.setVisible(true);

            game.setState(GameState.FIRST_TURN);
        } else
        {
            lockLaunch = false;
        }
    }


    // TODO: 1/7/21 reset only if the hmi call the method (not if it's coming from the console)
    private void reset()
    {
        System.out.println(Thread.currentThread().getName());
        if (!Thread.currentThread().getName().equals(InitFrameView.THREAD_FROM_INIT_VIEW_NAME))
        {
            return;
        }
        for (InitView view : views)
        {
            if (view instanceof InitConsoleView)
            {
                InitConsoleView icv = (InitConsoleView) view;
                Thread thread = icv.getThread();
                if (thread.isAlive() && !thread.isInterrupted())
                {
                    thread.interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        InitModel model = new InitModel();
        InitConsoleView v = new InitConsoleView();
        InitFrameView view = new InitFrameView();
        JFrame frame = new JFrame("Shape Up");

        Set<InitView> icv = new HashSet<>();
        icv.add(view);
        icv.add(v);

        frame.add(view);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);

        InitController ic = new InitController(model, icv, frame);
        view.setController(ic);
        v.setController(ic);
        model.addPropertyChangeListener(view);
        model.addPropertyChangeListener(v);
        frame.setVisible(true);

        model.setState(InitState.START_MENU);
    }

}
