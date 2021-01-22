/**
 * Implementation of the strategy pattern used for the virtual player actions
 *
 * Each virtual player has a strategy which determines his "difficulty level"
 * The random strategy is considered easy, all of his actions are randomly calculated.
 * The Difficult strategy choose the best movement+placement or just placement to maximise his
 * scores for one turn.
 */
package fr.utt.lo02.projet.model.strategy;