package fr.utt.lo02.projet.strategy;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class InitModel
{

    private InitState state;

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


    public void addPropertyChangeListener(PropertyChangeListener pcl)
    {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl)
    {
        support.removePropertyChangeListener(pcl);
    }
}
