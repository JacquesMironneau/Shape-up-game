package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.model.board.*;
import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.model.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.model.game.ChoiceOrder;
import fr.utt.lo02.projet.model.player.MoveRequest;
import fr.utt.lo02.projet.model.player.PlaceRequest;
import fr.utt.lo02.projet.model.player.PlayerHandEmptyException;
import fr.utt.lo02.projet.model.player.VirtualPlayer;
import fr.utt.lo02.projet.model.strategy.DifficultStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for virtual player
 * Here we test the different strategies (difficult and random) for placement and movement
 */
class VirtualPlayerTest
{

    @Test
    void place() throws PlayerHandEmptyException
    {

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

        VirtualPlayer player1 = new VirtualPlayer("ord1", board, new DifficultStrategy(visitor));
        player1.drawCard(handCard);
        player1.addRoundScore(15);
        player1.addRoundScore(25);
        player1.setVictoryCard(victoryCard);

        System.out.println(player1.askChoice(ChoiceOrder.FIRST_CHOICE));
        PlaceRequest request = new PlaceRequest(player1.askPlaceCard().getCoordinates(), player1.askPlaceCard().getCard());
        System.out.println("Player 1 wants to place " + request.getCard() + " to : " + request.getCoordinates());
        player1.displayRoundScore();
        player1.displayFinalScore();
        assertNotNull(request);
    }

    @Test
    void move() throws BoardEmptyException
    {

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
        VirtualPlayer player1 = new VirtualPlayer("ord1", board, new DifficultStrategy(visitor));
        player1.drawCard(handCard);
        player1.drawCard(handCard);
        player1.drawCard(handCard);
        player1.setVictoryCard(victoryCard);
        player1.addRoundScore(15);
        player1.addRoundScore(25);

        System.out.println(player1.askChoice(ChoiceOrder.FIRST_CHOICE));
        MoveRequest request2 = new MoveRequest(player1.askMoveCard().getOrigin(), player1.askMoveCard().getDestination());
        System.out.println("Player 1 wants to move card from " + request2.getOrigin() + " to : " + request2.getDestination());
        player1.displayRoundScore();
        player1.displayFinalScore();
        assertNotNull(request2);
    }

}
