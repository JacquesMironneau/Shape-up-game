package fr.utt.lo02.projet.junit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.strategy.Choice;
import fr.utt.lo02.projet.strategy.DifficultStrategy;
import fr.utt.lo02.projet.strategy.MoveRequest;
import fr.utt.lo02.projet.strategy.PlaceRequest;

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
    void emptyBoard() {
        AbstractBoard board = new RectangleBoard();
        IBoardVisitor visitor = new ScoreCalculatorVisitor();
        List<Card> handCards = new ArrayList<Card>();
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
        List<Card> handCards = new ArrayList<Card>();
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
