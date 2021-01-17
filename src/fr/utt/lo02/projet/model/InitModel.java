package fr.utt.lo02.projet.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Allow to manage the initialization with a state machine.
 * @author Baptiste, Jacques
 *
 */
public class InitModel
{

	/**
	 * Represents the current state of the initialization menu.
	 */
    private InitState state;

    /**
     * It's the observable part. Used to notify a state's change.
     */
    private PropertyChangeSupport support;

    public InitModel()
    {

        support = new PropertyChangeSupport(this);
    }

    public void setState(InitState shapeBoardChoice)
    {
        support.firePropertyChange("init", this.state, shapeBoardChoice);

        state = shapeBoardChoice;

    }

    /**
     * Add an observer to the initialization menu.
     * 
     * @param pcl the observer. Here, they are initialization views.
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl)
    {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * Remove an observer to the initialization menu.
     * 
     * @param pcl the observer. Here, they are initialization views.
     */
    public void removePropertyChangeListener(PropertyChangeListener pcl)
    {
        support.removePropertyChangeListener(pcl);
    }
}
