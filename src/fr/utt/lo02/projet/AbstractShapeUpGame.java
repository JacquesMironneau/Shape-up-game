package fr.utt.lo02.projet;

import java.util.ArrayList;

public abstract class AbstractShapeUpGame
{

	private int roundNumber;
	
	private ArrayList<ArrayList<Integer>> scores;
	
	protected abstract void initRound();
	
	
	public void playGame()
	{
		for (this.roundNumber = 0; this.roundNumber < 4; ++this.roundNumber)
		{
			playRound();
		}
	}
	
	protected abstract void playRound();

	protected abstract void playTurn();
	
	public boolean placeCardRequest(Card aCard, int x, int y)
	{
		return false;
	}
	
	public boolean moveCardRequest(Card aCard, int x, int y)
	{
		return false;
	}
	
	protected void drawCard(int playerIndex)
	{
		
	}
	
	protected abstract void calculateGameScore();

}
