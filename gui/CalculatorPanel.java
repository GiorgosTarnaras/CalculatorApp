package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.*;
import java.awt.Font;

public class CalculatorPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel textField;
	private JButton[] myButtons = new JButton[5*4];
	private String screenText = "";
	private Stack<String> postFix;
	CalculatorPanel()
	{
		this.setVisible(true);
		this.setLayout(new GridBagLayout());
		textField = new JLabel(" ");
		textField.setFont(new Font("Verdana", Font.PLAIN, 20));
        createButtons("C()/789*456-123+0.^=", 20);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 2;
        
        //put textfield 
        
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 400;
        this.add(textField, c);
        c.weightx=0.5;
        c.gridwidth = 1;
        
        //put buttons
        
        for(int i = 0;i<5;i++)
        {
        	
        	for(int j = 0;j<4;j++)
        	{
        		c.gridx = j;
        		c.gridy = i+1;
        		this.add(myButtons[4*i+j], c);
        	}
        }
        
	}
	
	void makeButton(String text, int index)
	{
		JButton btn = new JButton(text);
		btn.addActionListener(this);
		btn.setBorderPainted(false);
		myButtons[index] = btn;
	}
	
	void createButtons(String text, int n)
	{
		int i;
		for(i = 0;i < n;i++)
		{
			makeButton(String.valueOf(text.charAt(i)), i);
			if((i >= 0 && i <= 3) || i%4 == 3)
				myButtons[i].setBackground(Color.pink);
			else myButtons[i].setBackground(Color.lightGray);
		}
		myButtons[19].setBackground(Color.orange);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton pressed = (JButton) e.getSource();
		String command = pressed.getText();
		if(command.equals("C")) {screenText = ""; textField.setText(" ");}
		else if(command.equals("=")) {
			postFix = getPostFix(screenText);
			calculate();
			screenText = "";
			}
		else {screenText += command; textField.setText(screenText);}
		
	}
	
	void calculate() {
		Stack<Double> res = new Stack<Double>();
		for(int i = 0;i<postFix.size();i++)
		{
			String el = postFix.get(i);
			if(el.equals("+"))
				res.push(res.pop()+res.pop());
			else if(el.equals("-"))
			{
				double tmp = res.pop();
				res.push(res.pop()-tmp);
			}
			else if(el.equals("*"))
				res.push(res.pop()*res.pop());
			else if(el.equals("/"))
			{
				double tmp = res.pop();
				if(tmp != 0)
					res.push(res.pop()/tmp);
				
			}
			else if(el.equals("^"))
			{
				double tmp = res.pop();
				res.push(Math.pow(res.pop(), tmp));
			}
			else
			{
				double valOfEl = Double.parseDouble(el);
				res.push(valOfEl);
			}
			
		}
		double calculation = Math.round(res.pop()*1000)/1000.0;
		
		textField.setText(String.valueOf(calculation));	
		
	}

	Stack<String> getPostFix(String inFix)
	{
		
		Stack<String> operators = new Stack<String>();
		Stack<String> postFix = new Stack<String>();
		LinkedList<String> myInfix = getInFixList(inFix);
		int len = myInfix.size();
		System.out.print("\nInFix: ");
		for(int i=0;i<len;i++)
		{
			String el = myInfix.get(i);
			System.out.print(el+",");
			if(Character.isDigit(el.charAt(0)) || el.charAt(0) == '-') postFix.push(el);
			else if(el.equals("(")) operators.push(el);
			else if(el.equals(")"))
			{
				while (!operators.isEmpty() && !(operators.peek().equals("("))) {
	                   postFix.push(operators.pop());
	                }
				
	                if(!operators.isEmpty()) operators.pop();
			}
			else
			{
				while (!operators.isEmpty() && checkPriority(el) <= checkPriority(operators.peek())) {
	                   postFix.push(operators.pop());

	                }
	                operators.push(el);
	        }
		}
		
		while (!operators.isEmpty()) {
            postFix.push(operators.pop());
		}
		
		System.out.print("\nPostFix: ");
		for(int j = 0;j<postFix.size();j++)
		{
			System.out.print(postFix.get(j)+",");
		}
		return postFix;
	}
	
	int checkPriority(String s)
	{
		if(s.equals("+") || s.equals("-")) return 1;
		else if(s.equals("*") || s.equals("/")) return 2;
		else if(s.equals("^")) return 3;
		return -1;
	}
	
	LinkedList<String> getInFixList(String inFix)
	{
		String tempString = "";
		String sign = "";
		LinkedList<String> tempLinkedList = new LinkedList<String>();
		char[] myChars = inFix.toCharArray();
		int k = 0;
		for(int i = 0;i<myChars.length;i++)
		{
			if(Character.isDigit(myChars[i]) || myChars[i] == '.')
				tempString += myChars[i];
			else
			{
				if(!tempString.equals("")) {tempLinkedList.add(k++,sign+tempString);tempString = "";sign="";}
				if(myChars[i] == '-') {
					sign = "-";
					String lastel = tempLinkedList.get(k-1);
					System.out.println(lastel);
					if(Character.isDigit(lastel.charAt(0)) || lastel.charAt(0) == '-') tempLinkedList.add(k++,"+");
					}
				else tempLinkedList.add(k++,String.valueOf(myChars[i]));
			}
		}if(!tempString.equals("")) tempLinkedList.add(k,sign+tempString);
		return tempLinkedList;
		
	}
	

}
