package page;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Content extends JPanel {
	JLabel la;

	public Content() {
		la = new JLabel("친구목록");
		
		add(la);
		
		setPreferredSize(new Dimension(700, 500));
		setBackground(Color.ORANGE);
	}
}

