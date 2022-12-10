package main;

import gui.CalculatorFrame;

public class MainClass {
	static CalculatorFrame calculator; 
	public static void main(String[] args) {
		calculator = new CalculatorFrame(400, 400);
	}

}
