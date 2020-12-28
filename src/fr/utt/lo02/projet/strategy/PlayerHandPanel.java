package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class PlayerHandPanel extends JPanel
{

	private List<Card> hand;

	public PlayerHandPanel(EventListener win)
	{
//		setMinimumSize(new Dimension(100,100));
//
//		setPreferredSize(new Dimension(200,200));
//
//		setLayout(new GridLayout(1,3));
//		addMouseListener((MouseListener) win);
//		addMouseMotionListener((MouseMotionListener) win);
//		hand = new ArrayList<>();
//		hand.add(new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED));
//
//		for (int i = 0; i < hand.size(); i++) {
//			JPanel location = new JPanel(new BorderLayout());
//			location.setBackground((i)%2 == 0 ? Color.BLACK : Color.DARK_GRAY);
//			add(location);
//		}
//		for (int i = 0; i < hand.size(); i++) {
//			ImageIcon card = new ImageIcon(new ImageIcon("../cards/filled_red_circle.png").getImage().getScaledInstance(120,180, Image.SCALE_FAST));
//			JLabel piece = new JLabel(card);
//			JPanel panel = (JPanel) getComponent(i);
//			panel.add(piece);
//		}



	}



}
