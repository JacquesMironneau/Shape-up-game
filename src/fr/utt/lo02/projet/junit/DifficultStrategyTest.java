package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.Coordinates;
import fr.utt.lo02.projet.model.board.RectangleBoard;
import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.model.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.model.player.Choice;
import fr.utt.lo02.projet.model.strategy.DifficultStrategy;
import fr.utt.lo02.projet.model.player.MoveRequest;
import fr.utt.lo02.projet.model.player.PlaceRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test for special case of the difficult strategy (virtual player)
 * Theses test are mainly limit test (for a almost full or empty and almost empty board)
 */
class DifficultStrategyTest {


    @Test
    void thirteenCardBoardHorizontal() {
        AbstractBoard board = new RectangleBoard();
        IBoardVisitor visitor = new ScoreCalculatorVisitor();
        board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(2, 0), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(3, 0), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(4, 0), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(0, -1), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, -1), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, -1), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(3, -1), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(4, -1), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(0, -2), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(1, -2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, -2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        Card victoryCard = new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
        Card handCard = new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED);
        List<Card> handCards = new ArrayList<>();
        handCards.add(handCard);
        DifficultStrategy strategy = new DifficultStrategy(visitor);
        Choice choice = strategy.makeChoice(board, victoryCard, handCards);
        System.out.println(choice);
        PlaceRequest request = strategy.makePlaceRequest(board, victoryCard, handCards);
        System.out.println(request);
        MoveRequest request2 = strategy.makeMoveRequest(board, victoryCard, handCards);
        assertNotNull(choice);
        assertNotNull(request);
        assertNotNull(request2);
    }

    @Test
    void fourteenCardBoardHorizontal() {
        AbstractBoard board = new RectangleBoard();
        IBoardVisitor visitor = new ScoreCalculatorVisitor();
        board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(2, 0), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(3, 0), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(4, 0), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(0, -1), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, -1), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, -1), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(3, -1), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(4, -1), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(0, -2), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(1, -2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, -2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(3, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.FILLED));
        Card victoryCard = new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
        Card handCard = new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED);
        List<Card> handCards = new ArrayList<>();
        handCards.add(handCard);
        DifficultStrategy strategy = new DifficultStrategy(visitor);
        Choice choice = strategy.makeChoice(board, victoryCard, handCards);
        System.out.println(choice);
        PlaceRequest request = strategy.makePlaceRequest(board, victoryCard, handCards);
        System.out.println(request);
        MoveRequest request2 = strategy.makeMoveRequest(board, victoryCard, handCards);
        assertNotNull(choice);
        assertNotNull(request);
        assertNotNull(request2);
    }

    @Test
    void emptyBoard() {
        AbstractBoard board = new RectangleBoard();
        IBoardVisitor visitor = new ScoreCalculatorVisitor();
        List<Card> handCards = new ArrayList<>();
        Card victoryCard = new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
        Card handCard = new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED);
        handCards.add(handCard);
        DifficultStrategy strategy = new DifficultStrategy(visitor);
        Choice choice = strategy.makeChoice(board, victoryCard, handCards);
        System.out.println(choice);
        PlaceRequest request = strategy.makePlaceRequest(board, victoryCard, handCards);
        System.out.println(request);
        assertNotNull(choice);
        assertNotNull(request);

    }


    @Test
    void oneCardBoard() {
        AbstractBoard board = new RectangleBoard();
        IBoardVisitor visitor = new ScoreCalculatorVisitor();
        List<Card> handCards = new ArrayList<>();
        board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        Card victoryCard = new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
        Card handCard = new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED);
        handCards.add(handCard);
        DifficultStrategy strategy = new DifficultStrategy(visitor);
        Choice choice = strategy.makeChoice(board, victoryCard, handCards);
        System.out.println(choice);
        PlaceRequest request = strategy.makePlaceRequest(board, victoryCard, handCards);
        System.out.println(request);
        MoveRequest request2 = strategy.makeMoveRequest(board, victoryCard, handCards);
        assertNotNull(choice);
        assertNotNull(request);
        assertNotNull(request2);
    }

    @Test
    void twoCardBoard() {
        AbstractBoard board = new RectangleBoard();
        IBoardVisitor visitor = new ScoreCalculatorVisitor();
        List<Card> handCards = new ArrayList<Card>();
        board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        Card victoryCard = new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
        Card handCard = new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED);
        handCards.add(handCard);
        DifficultStrategy strategy = new DifficultStrategy(visitor);
        Choice choice = strategy.makeChoice(board, victoryCard, handCards);
        System.out.println(choice);
        PlaceRequest request = strategy.makePlaceRequest(board, victoryCard, handCards);
        System.out.println(request);
        MoveRequest request2 = strategy.makeMoveRequest(board, victoryCard, handCards);
        assertNotNull(choice);
        assertNotNull(request);
        assertNotNull(request2);
    }

    @Test
    void thirteenCardBoardVertical() {
        AbstractBoard board = new RectangleBoard();
        IBoardVisitor visitor = new ScoreCalculatorVisitor();
        board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(0, 1), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(0, 2), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(0, 3), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(0, 4), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(1, 3), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 4), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, 0), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(2, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, 2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        Card victoryCard = new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
        Card handCard = new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED);
        List<Card> handCards = new ArrayList<Card>();
        handCards.add(handCard);
        DifficultStrategy strategy = new DifficultStrategy(visitor);
        Choice choice = strategy.makeChoice(board, victoryCard, handCards);
        System.out.println(choice);
        PlaceRequest request = strategy.makePlaceRequest(board, victoryCard, handCards);
        System.out.println(request);
        MoveRequest request2 = strategy.makeMoveRequest(board, victoryCard, handCards);
        assertNotNull(choice);
        assertNotNull(request);
        assertNotNull(request2);
    }

    @Test
    void fourteenCardBoardVertical() {
        AbstractBoard board = new RectangleBoard();
        IBoardVisitor visitor = new ScoreCalculatorVisitor();
        board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(0, 1), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(0, 2), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(0, 3), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(0, 4), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 1), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(1, 3), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(1, 4), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, 0), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(2, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
        board.getPlacedCards().put(new Coordinates(2, 2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
        board.getPlacedCards().put(new Coordinates(2, 3), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.FILLED));
        Card victoryCard = new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
        Card handCard = new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED);
        List<Card> handCards = new ArrayList<Card>();
        handCards.add(handCard);
        DifficultStrategy strategy = new DifficultStrategy(visitor);
        Choice choice = strategy.makeChoice(board, victoryCard, handCards);
        System.out.println(choice);
        PlaceRequest request = strategy.makePlaceRequest(board, victoryCard, handCards);
        System.out.println(request);
        MoveRequest request2 = strategy.makeMoveRequest(board, victoryCard, handCards);
        assertNotNull(choice);
        assertNotNull(request);
        assertNotNull(request2);
    }
}
