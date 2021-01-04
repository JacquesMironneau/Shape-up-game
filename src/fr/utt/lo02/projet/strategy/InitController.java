package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.GameView;
import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.game.*;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class InitController
{

    private GameMode gameMode;
    private IBoardVisitor visitor;
    private List<Player> players;
    private AbstractBoard board;

    private Set<InitView> views;
    private AbstractShapeUpGame model;

    public InitController(AbstractShapeUpGame model, Set<InitView> viewSet)
    {

        this.model = model;
        this.views = viewSet;

    }

    public void setGameMode(GameMode gm)
    {
        this.gameMode = gm;
    }

    public void setScoreCalculator(IBoardVisitor visitor)
    {
        this.visitor = visitor;
    }

    public void shapeBoard(AbstractBoard board)
    {
        this.board = board;
    }

    public void setPlayer(List<Player> players)
    {
        this.players = players;
    }

    public void quit()
    {
        System.exit(0);
    }

    public void launch()
    {
        for (InitView view : views)
        {
            model.removePropertyChangeListener(view);
        }
        switch (gameMode)
        {
            case NORMAL -> {
                model = new ShapeUpGame(visitor, players, board);
            }
            case ADVANCED -> {
                model = new ShapeUpGameAdvanced(visitor, players, board);

            }
            case NO_ADJACENCY -> {
                model = new ShapeUpGameWithoutAdjacencyRule(visitor, players, board);

            }
        }
        Set<GameView> gameViewSet = new HashSet<>();
        RectangleBoardFrameTest hmiView = new RectangleBoardFrameTest(board, model);
        GameConsoleView consoleView = new GameConsoleView(model, board);

        gameViewSet.add(hmiView);
        gameViewSet.add(consoleView);

        JFrame frame = new JFrame();

        frame.removeAll();
        frame.add(hmiView);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
//        comp.fakeUpdate();

        GameController sugc = new AdvancedShapeUpGameController(model, gameViewSet);

        hmiView.setController(sugc);
        consoleView.setController(sugc);
        model.addPropertyChangeListener(hmiView);
        model.addPropertyChangeListener(consoleView);


        frame.setVisible(true);
        model.setState(GameState.FIRST_TURN);


    }


}
