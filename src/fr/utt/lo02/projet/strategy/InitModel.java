package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

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
        support.firePropertyChange("init", this.state, state);

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
