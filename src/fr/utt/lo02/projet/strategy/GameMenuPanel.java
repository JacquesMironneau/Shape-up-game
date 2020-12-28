package fr.utt.lo02.projet.strategy;

import javax.swing.*;
import java.awt.*;

public class GameMenuPanel  extends JComponent
{

	public GameMenuPanel()
	{

		setBounds(0,0, 1280, 720);
		setLayout(null);
		setOpaque(false);

	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawString("yow",10,30);
	}
}
