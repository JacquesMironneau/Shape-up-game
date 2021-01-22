/**
 * Views of the game using MVC pattern
 * In the pattern, the model notifies a state change to the views (using the Observer pattern)
 *
 * Then the user acts on the view. This action is then transferred to the Controller which changes the model state
 * accordingly.
 * Here are split the console and the hmi views
 * There are two kinds of different view: the InitViews and the GameViews
 * The Init* are used for the main menu panel and the Game* are used during the game
 *
 * @see java.beans.PropertyChangeListener for the Observer implementation
 * @see fr.utt.lo02.projet.controller.ShapeUpGameController for an example of game controller
 */
package fr.utt.lo02.projet.view;