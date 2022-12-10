package gui;
import java.awt.BorderLayout;

import javax.swing.*;

public class CalculatorFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	static int height;
	static int width;
	static CalculatorPanel myPanel = new CalculatorPanel();
	
	public CalculatorFrame(int width, int height)
	{
		
		CalculatorFrame.width = width;
		CalculatorFrame.height = height;
		this.setSize(width, height);
		this.setTitle("Calculator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setFocusable(true);
		this.setLayout(new BorderLayout());
		this.add(myPanel, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
		
	}
}
