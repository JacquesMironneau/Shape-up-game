package fr.utt.lo02.projet.game;

import fr.utt.lo02.projet.board.*;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.strategy.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public abstract class AbstractShapeUpGame
{

	public static final int MAX_ROUND_NUMBER = 4;

	public int getRoundNumber()
	{
		return roundNumber;
	}

	/**
	 * Round index
	 */
	protected int roundNumber;

	/**
	 * is the current turn the first one ever played (by one of the player)
	 */
	protected boolean isFirstTurn;

	/**
	 * Deck of cards
	 */
	protected Queue<Card> deck;

	/**
	 * Players of the game
	 */
	protected List<Player> players;

	/**
	 * Game board of the game
	 */
	protected AbstractBoard board;


	/**
	 * Visitor used for score calculation at the end of each round
	 */
	private final IBoardVisitor visitor;

	protected GameState state;

	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}

	protected Player currentPlayer;

	private PropertyChangeSupport support;

	public AbstractShapeUpGame(IBoardVisitor visitor, List<Player> players, AbstractBoard board)
	{
		this.visitor = visitor;
		this.players = players;
		this.board = board;


		this.roundNumber = 0;
		support = new PropertyChangeSupport(this);
	}

	/**
	 * Initiate a round
	 */
	protected void initRound()
	{
		this.board.getPlacedCards().clear();
		this.deck = new LinkedList<>();
		initDeck();
		Collections.shuffle((LinkedList<Card>) this.deck);

		for (Player player : players)
		{
			player.getPlayerHand().clear();
			player.setVictoryCard(null);
		}

		// Remove hidden card
		this.deck.poll();

		if (board instanceof CircleBoard)
		{
			this.deck.poll();
		}
		this.isFirstTurn = true;
		currentPlayer = players.get(0);
	}


	public abstract void playTurn() throws PlayerHandEmptyException, BoardEmptyException;

	/**
	 * Request to place a card from the player hand to a position on the board
	 *
	 * @param placeRequest Which card and where the player want to put it
	 * @return if the request is matching the game rules
	 */
	public PlaceRequestResult placeCardRequest(PlaceRequest placeRequest)
	{

		Card aCard = placeRequest.getCard();
		Coordinates coord = placeRequest.getCoordinates();

		if (!currentPlayer.getPlayerHand().contains(aCard))
		{

			return PlaceRequestResult.PLAYER_DOESNT_OWN_CARD;
		}

		boolean cardInTheLayout = board.isCardInTheLayout(coord);
		boolean cardAdjacentToAnExistingCard = true;

		if (!isFirstTurn)
		{
			cardAdjacentToAnExistingCard = board.isCardAdjacent(coord);
		}
		if (cardAdjacentToAnExistingCard && cardInTheLayout)
		{
			board.addCard(coord, aCard);
			currentPlayer.getPlayerHand().remove(aCard);
//			System.out.println("CARD SIZE" + currentPlayer.getPlayerHand().size());
			//System.out.println("[LOG] "+ aCard + " has been placed at " + coord);
			this.isFirstTurn = false;
			return PlaceRequestResult.CORRECT_PLACEMENT;
		}

		if (!cardAdjacentToAnExistingCard)
		{
//			player.PlaceResult(PlaceRequestResult.CARD_NOT_ADJACENT);
			return PlaceRequestResult.CARD_NOT_ADJACENT;
		}
//		player.PlaceResult(PlaceRequestResult.CARD_NOT_IN_THE_LAYOUT);
		return PlaceRequestResult.CARD_NOT_IN_THE_LAYOUT;
	}

	/**
	 * * Request to move a existing card from the board to another position
	 *
	 * @param moveRequest player request
	 * @return if the card has been moved or not
	 */
	public MoveRequestResult moveCardRequest(MoveRequest moveRequest)
	{

		Coordinates origin = moveRequest.getOrigin();
		Coordinates destination = moveRequest.getDestination();

		if (origin.equals(destination))
		{
			return MoveRequestResult.ORIGIN_AND_DESTINATION_ARE_EQUAL;
		}

		Card card = this.board.getPlacedCards().get(origin);

		if (card == null)
		{
			return MoveRequestResult.NO_CARD_IN_THE_ORIGIN_COORDINATE;
		}

		board.removeCard(origin, card);

		if (board.getPlacedCards().isEmpty())
		{
			board.addCard(destination, card);
			return MoveRequestResult.MOVE_VALID;
		}
		boolean cardAdjacentToAnExistingCard = board.isCardAdjacent(destination);
		boolean cardInTheLayout = board.isCardInTheLayout(destination);

		if (cardAdjacentToAnExistingCard && cardInTheLayout)
		{
			board.addCard(destination, card);
			return MoveRequestResult.MOVE_VALID;
		}
		board.addCard(origin, card);

		if (!cardAdjacentToAnExistingCard)
		{
			return MoveRequestResult.CARD_NOT_ADJACENT;
		}
		return MoveRequestResult.CARD_NOT_IN_THE_LAYOUT;

	}

	/**
	 * Add a card from the deck to a given player based on its index
	 * If the deck is empty, it doesn't add anything to the player.
	 */
	public void drawCard()
	{
		if (!this.deck.isEmpty())
		{
			currentPlayer.drawCard(this.deck.poll());
//			setState(GameState.CARD_DRAW);
		}
	}

	public void setState(GameState state)
	{

		support.firePropertyChange("state", this.state, state);

		this.state = state;
	}

	public void nextPlayer()
	{
		currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % this.players.size());
	}

	protected void initDeck()
	{
		if (!deck.isEmpty()) return;

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
		}

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		}

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
		}

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.CIRCLE, Card.Filling.FILLED));
		}

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.SQUARE, Card.Filling.FILLED));
		}

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.TRIANGLE, Card.Filling.FILLED));
		}
	}

	protected abstract boolean isRoundFinished();

	public void endRound()
	{
		for (Player player : players)
		{
			player.addRoundScore(board.accept(visitor, player.getVictoryCard()));
		}
		roundNumber++;
	}

	public List<Player> getPlayers()
	{
		return players;
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl)
	{
		support.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl)
	{
		support.removePropertyChangeListener(pcl);
	}
}
