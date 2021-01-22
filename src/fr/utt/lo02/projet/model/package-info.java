/**
 * Model of the game
 *
 * Model are here seen as API with states, to match the MVC pattern a controller changes the state
 * of a model which leads to an update of the views (Model are observable and View are observers).
 * The game loop is then no more represented as a real loop but more like a graph with different states.
 *
 */
package fr.utt.lo02.projet.model;