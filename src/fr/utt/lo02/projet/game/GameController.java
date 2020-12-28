package fr.utt.lo02.projet.game;

public interface GameController
{

	void askChoice(int choiceNumber, int choice);

	void askMove(int x, int y, int x2, int y2);

	void askPlace(int x, int y, int cardIndex);

	void endTurn();

	void play();

	void endRound();

	void endGame();

}
