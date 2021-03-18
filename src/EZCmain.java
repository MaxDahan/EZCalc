import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class EZCmain {
	ResourceLoader RL = new ResourceLoader();

	//START
	public static void main(String[] args) throws Exception {
		new EZCmain(true);
	}
	public EZCmain(boolean x) throws Exception {
		//frame.setIconImage(ImageIO.read(new File("data\\icon.png")));
		frame.setIconImage(ImageIO.read(RL.load("icon.png")));
		
		slowMinimize.start();
		
		slowExit.start();
		
		so.start();
		sc.start();
		
		so2.start();
		sc2.start();
		
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.setSize(400, 200);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
		frame.setLayout(null);
		frame.setVisible(true);
		frame.addMouseListener(mouseAdapt);
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {moveItPress(evt);}

			public void keyReleased(KeyEvent evt) {moveItRelease(evt);}
		});
		
		calc.setFont(new Font("arial", Font.BOLD, 32));
		calc.setForeground(Color.WHITE);
		calc.setBounds(60, 10, 400, 40);
		frame.getContentPane().add(calc);
		
		instructions.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		instructions.setForeground(Color.WHITE);
		instructions.setBounds(80, 127, 400, 40);
		frame.getContentPane().add(instructions);
		
		instructionsp2.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		instructionsp2.setForeground(Color.WHITE);
		instructionsp2.setBounds(80, 145, 400, 40);
		frame.getContentPane().add(instructionsp2);
		
		window.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		window.setForeground(Color.WHITE);
		window.setBounds(17, 70, 320, 60);
		window.setFont(new Font("arial", Font.BOLD, 20));
		frame.getContentPane().add(window);
		
		expand.setForeground(Color.WHITE);
		expand.setBackground(Color.BLACK);
		expand.setFocusable(false);
		expand.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		expand.setBounds(345, 61, 30, 80);
		expand.setFont(new Font("Calibri (body)", Font.BOLD, 30));
		frame.getContentPane().add(expand);
		expand.addActionListener(exp);
		
		expand2.setForeground(Color.WHITE);
		expand2.setBackground(Color.BLACK);
		expand2.setFocusable(false);
		expand2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		expand2.setBounds(5, 135, 70, 43);
		expand2.setFont(new Font("Calibri (body)", Font.BOLD, 30));
		frame.getContentPane().add(expand2);
		expand2.addActionListener(exp2);
		
		BufferedImage image = null; 
	    //try {image = ImageIO.read(new File("data\\minimize.png"));} catch (IOException e1) {e1.printStackTrace();}
	    try {image = ImageIO.read(RL.load("minimize.png"));} catch (IOException e1) {e1.printStackTrace();}
	    minimize = new JButton(new ImageIcon(image));
		minimize.setFocusable(false);
		minimize.setBounds(-5, -5, 60, 50);
		minimize.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		minimize.addActionListener(minimizeAction);
		frame.getContentPane().add(minimize);
		minimize.addActionListener(minimizeAction);
		
		makeButtons();
		makeInstructions();
		if(x) {
			calc.setFont(new Font("arial", Font.BOLD, 30));
			calc.setForeground(Color.WHITE);
			calc.setBounds(45, 5, 400, 40);
			frame.getContentPane().add(calc);
			
			exit.setForeground(Color.BLACK);
			exit.setBackground(Color.RED);
			exit.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
			exit.setBounds(326, -5, 60, 50);
			exit.setFont(new Font("Calibri (body)", Font.BOLD, 20));
			exit.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {
					exit.setBackground(new Color(116, 0, 0));
				}
				public void mouseReleased(MouseEvent e) {
					exit.setBackground(Color.RED);
				}

				public void mouseClicked(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
			frame.getContentPane().add(exit);
			exit.setFocusable(false);
			exit.addActionListener(close);
			frame.repaint();
		}
		
		textchange = changeText;//the thread textchange is equal to the private class changetext
		textchange.start();
	}
	
	
	//USEFULL METHODS
	//used to close the frame, can be used by exterior classes
	public void closeMe() {
		frame.dispose();
		
	}
	//Constantly changes the text being displayed to a certain string
	private class changeText extends Thread {
		public void run() {
			while(true) {
				window.setText(ce);
				try{Thread.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
			}
		}
	}
	//adds a number to curernt Nums or change the current one
	public void addNum() {
		try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();} //used to be precise otherwise its to fast
		if(currentNums.size() > 0) {
			currentNums.remove(currentNums.size() - 1);
			currentNums.add(window.getText());
		} else {
			currentNums.add(window.getText());
		}
	}
	
	//KEY LISTENER METHODS
	//Pressing keys
	public void moveItPress(KeyEvent evt) {
		int key = evt.getKeyCode();
		
		if (key == KeyEvent.VK_1) {
			ce += "1";
			addNum();
		} else if (key == KeyEvent.VK_2) {
			ce += "2";
			addNum();
		} else if (key == KeyEvent.VK_3) {
			ce += "3";
			addNum();
		} else if (key == KeyEvent.VK_4) {
			ce += "4";
			addNum();
		} else if (key == KeyEvent.VK_5) {
			ce += "5";
			addNum();
		} else if (key == KeyEvent.VK_6) {
			ce += "6";
			addNum();
		} else if (key == KeyEvent.VK_7) {
			ce += "7";
			addNum();
		} else if (key == KeyEvent.VK_8) {
			if(!shift) {
				ce += "8";
				addNum();
			} else if(shift) {
				if(!currentNums.get(currentNums.size() - 1).equals("")) {
					currentNums.add("x");
					currentNums.add("");
					ce = "";
				}
			}
		} else if (key == KeyEvent.VK_9) {
			ce += "9";
			addNum();
		} else if (key == KeyEvent.VK_0) {
			ce += "0";
			addNum();
		} else if (key == KeyEvent.VK_SHIFT) {
			shift = true;
		} else if (key == KeyEvent.VK_EQUALS) {
			if(shift) {
				if(!currentNums.get(currentNums.size() - 1).equals("")) {
					currentNums.add("+");
					currentNums.add("");
					ce = "";
				}
			}
		} else if (key == KeyEvent.VK_SLASH) {
			if(!currentNums.get(currentNums.size() - 1).equals("")) {
				currentNums.add("/");
				currentNums.add("");
				ce = "";
			}
		}  else if (key == KeyEvent.VK_P) {
			if(!currentNums.get(currentNums.size() - 1).equals("")) {
				currentNums.add("p");
				currentNums.add("");
				ce = "";
			}
		} else if (key == KeyEvent.VK_R) {
			if(!currentNums.get(currentNums.size() - 1).equals("")) {
				currentNums.add("r");
				currentNums.add("");
				ce = "";
			}
		} else if (key == KeyEvent.VK_MINUS) {
			if(!currentNums.get(currentNums.size() - 1).equals("")) {
				currentNums.add("-");
				currentNums.add("");
				ce = "";
			}
		} else if (key == KeyEvent.VK_C) {
			currentNums.clear();
			ce = "";
		} else if (key == KeyEvent.VK_PERIOD) {
			if(!periodCheck()) {ce += "."; addNum();}
		} else if(key == KeyEvent.VK_BACK_SPACE) {
			if(ce.length() > 1) {ce = ce.substring(0, ce.length() - 1);addNum();}
			else {ce = "";addNum();}
		} else if(key == KeyEvent.VK_ENTER) {
			if(currentNums.size() > 2) {
				calculate();
			}
		}
	}
	//Releasing keys
	public void moveItRelease(KeyEvent evt) {
		int key = evt.getKeyCode();
		
		if (key == KeyEvent.VK_SHIFT) {
			shift = false;
		} 
	}
	

	//CALCULATORS
	//Caclucaltes the answer
	public void calculate() {
		double answer = 0;
		
		//PEMDAS
		
		//Exponents
		for(int j = 0; j < currentNums.size(); j++) {
			if(currentNums.get(j).equals("p")) {
				answer = 0;
				answer = calcc(answer, j);
				j--;
			}
		}
			//roots
		for(int j = 0; j < currentNums.size(); j++) {
			if(currentNums.get(j).equals("r")) {
				answer = 0;
				answer = calcc(answer, j);
				j--;
			}
		}
		
		//Multiplication
		for(int j = 0; j < currentNums.size(); j++) {
			if(currentNums.get(j).equals("x")) {
				answer = 0;
				answer = calcc(answer, j);
				j--;
			}
		}
		
		//Division
		for(int j = 0; j < currentNums.size(); j++) {
			if(currentNums.get(j).equals("/")) {
				answer = 0;
				answer = calcc(answer, j);
				j--;
			}
		}
		
		//ADDING
		for(int j = 0; j < currentNums.size(); j++) {
			if(currentNums.get(j).equals("+")) {
				answer = 0;
				answer = calcc(answer, j);
				j--;
			}
		}
		
		//Subtraction
		for(int j = 0; j < currentNums.size(); j++) {
			if(currentNums.get(j).equals("-")) {
				answer = 0;
				answer = calcc(answer, j);
				j--;
			}
		}
		helpCalculate(answer);
	}
	//A helper for calculate
	public void helpCalculate(double answer) {
		currentNums.clear();
		String ans = answer + "";
		if(ans.length() > 1) {
			if(ans.substring(ans.length() - 2, ans.length()).equals(".0")) {
				ans = ans.substring(0, ans.length() - 2);
			}
		}
		if(answer != 0) {
			currentNums.add(ans);
			ce = ans;
		} else {
			ce = "";
		}
	}
	//Used to identify which operation is selected
	public double calcc(double answer, int i) {
		if(currentNums.get(i).equals("+") && i < currentNums.size() - 1) {
			answer += Double.parseDouble(currentNums.get(i - 1));
			answer += Double.parseDouble(currentNums.get(i + 1));
			currentNums.remove(i - 1);currentNums.remove(i - 1);currentNums.remove(i - 1);
			currentNums.add(i - 1, answer + "");
		} else if(currentNums.get(i).equals("/") && i < currentNums.size() - 1) {
			answer = Double.parseDouble(currentNums.get(i - 1));
			answer /= Double.parseDouble(currentNums.get(i + 1));
			currentNums.remove(i - 1);currentNums.remove(i - 1);currentNums.remove(i - 1);
			currentNums.add(i - 1, answer + "");
		} else if(currentNums.get(i).equals("x") && i < currentNums.size() - 1) {
			answer = Double.parseDouble(currentNums.get(i - 1));
			answer *= Double.parseDouble(currentNums.get(i + 1));
			currentNums.remove(i - 1);currentNums.remove(i - 1);currentNums.remove(i - 1);
			currentNums.add(i - 1, answer + "");
		} else if(currentNums.get(i).equals("-") && i < currentNums.size() - 1) {
			answer = Double.parseDouble(currentNums.get(i - 1));
			answer -= Double.parseDouble(currentNums.get(i + 1));
			currentNums.remove(i - 1);currentNums.remove(i - 1);currentNums.remove(i - 1);
			currentNums.add(i - 1, answer + "");
		} else if(currentNums.get(i).equals("p") && i < currentNums.size() - 1) {
			answer = Double.parseDouble(currentNums.get(i - 1));
			double powerof = Double.parseDouble(currentNums.get(i + 1));
			answer = Math.pow(answer, powerof);
			currentNums.remove(i - 1);currentNums.remove(i - 1);currentNums.remove(i - 1);
			currentNums.add(i - 1, answer + "");
		} else if(currentNums.get(i).equals("r") && i < currentNums.size() - 1) {
			answer = Double.parseDouble(currentNums.get(i - 1));
			double powerof = Double.parseDouble(currentNums.get(i + 1));
			answer = Math.pow(answer, (1/powerof));
			currentNums.remove(i - 1);currentNums.remove(i - 1);currentNums.remove(i - 1);
			currentNums.add(i - 1, answer + "");
		} return answer;
	}
	//checks to see if contains period
	public boolean periodCheck() {
		String cn = currentNums.get(currentNums.size()- 1); //current current number
		return cn.indexOf(".") >= 0;
	}
	
	//when the minimize button is pressed
	public class minimizeAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			minimizex = (int) frame.getLocation().getX();
			minimizey = (int) frame.getLocation().getY();
			slowmin = true;
		}
	}
	
	private class close implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			minimize.setVisible(false);
			slowex = true;
		}
	}
	
	private class slowExit extends Thread {
		public void run() {
			while(true) {
				while(slowex) {
					frame.setLocation(frame.getX() + 20, frame.getY());
					frame.setSize(frame.getWidth() - 40, frame.getHeight());
					if(frame.getWidth() == 0) {
						System.exit(0);
					}
					try {Thread.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
				}
				try {Thread.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
	
	private class slowMinimize extends Thread {
		private int oldwidth = 0;
		private int oldheight = 0;
		
		public void run() {
			while(true) {
				oldwidth = frame.getWidth();
				oldheight = frame.getHeight();
				while(slowmin) {
					minimize.setVisible(false);
					if(oldwidth == 400 && oldheight == 200) {
						frame.setLocation(frame.getX() + 10, frame.getY() + 5);
						frame.setSize(frame.getWidth() - 20, frame.getHeight() - 10);
					}
					if(oldwidth == 600 && oldheight == 200) {
						frame.setLocation(frame.getX() + 15, frame.getY() + 5);
						frame.setSize(frame.getWidth() - 30, frame.getHeight() - 10);
					}
					if(oldwidth == 400 && oldheight == 600) {
						frame.setLocation(frame.getX() + 10, frame.getY() + 15);
						frame.setSize(frame.getWidth() - 20, frame.getHeight() - 25);
					}
					if(oldwidth == 600 && oldheight == 600) {
						frame.setLocation(frame.getX() + 15, frame.getY() + 15);
						frame.setSize(frame.getWidth() - 30, frame.getHeight() - 25);
					}
					if(frame.getWidth() == 0 && frame.getHeight() == 0) {
						slowmin = false;
						frame.setState(JFrame.ICONIFIED);
						minimize.setVisible(true);
						frame.setLocation(minimizex, minimizey);
						if(oldwidth == 400 && oldheight == 200) {
							frame.setSize(400, 200);
						}
						if(oldwidth == 600 && oldheight == 200) {
							frame.setSize(600, 200);
						}
						if(oldwidth == 400 && oldheight == 600) {
							frame.setSize(400, 600);
						}
						if(oldwidth == 600 && oldheight == 600) {
							frame.setSize(600, 600);
						}
					}
					try {Thread.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
				}
				try {Thread.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
	
	//OPENING AND CLOSING
	//Slowly closes width
	private class slowClose1 extends Thread{
		public void run() {
			while (true) {
				while(go2) {
					if(frame.getWidth() >= 600 || frame.getWidth() != 400) {
						frame.setSize(frame.getWidth() - 10, frame.getHeight());
					} else {
						expand.setText(">");
						go2 = false;
					}
					try {Thread.sleep(5);} catch(InterruptedException e) {e.printStackTrace();}
				}
				try {Thread.sleep(5);} catch(InterruptedException e) {e.printStackTrace();}
			}
		}
	}
	//Slowly opens width
	private class slowOpen1 extends Thread{
		public void run() {
			while (true) {
				while(go) {
					if(frame.getWidth() <= 400 || frame.getWidth() != 600) {
						frame.setSize(frame.getWidth() + 10, frame.getHeight());
					} else {
						expand.setText("<");
						go = false;
					}
					try {Thread.sleep(5);} catch(InterruptedException e) {e.printStackTrace();}
				}
				try {Thread.sleep(5);} catch(InterruptedException e) {e.printStackTrace();}
			}
		}
	}
	//Slowly closes height
	private class slowClose2 extends Thread{
		public void run() {
			while (true) {
				while(go22) {
					if(frame.getHeight() >= 600 || frame.getHeight() != 200) {
						frame.setSize(frame.getWidth(), frame.getHeight() - 10);
					} else {
						expand2.setText("v");
						go22 = false;
					}
					try {Thread.sleep(3);} catch(InterruptedException e) {e.printStackTrace();}
				}
				try {Thread.sleep(3);} catch(InterruptedException e) {e.printStackTrace();}
			}
		}
	}
	//Slowly opens height
	private class slowOpen2 extends Thread{
		public void run() {
			while (true) {
				while(go1) {
					if(frame.getHeight() <= 200 || frame.getHeight() != 600) {
						frame.setSize(frame.getWidth(), frame.getHeight() + 10);
					} else {
						expand2.setText("^");
						go1 = false;
					}
					try {Thread.sleep(3);} catch(InterruptedException e) {e.printStackTrace();}
				}
				try {Thread.sleep(3);} catch(InterruptedException e) {e.printStackTrace();}
			}
		}
	}
	//Activates one of the slow opens or closes for the width
	private class exp implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//if(frame.getWidth() == 400 || frame.getWidth() == 600) {
				if(frame.getWidth() > 400) {
					go = false;
					go2 = true;
				} else {
					go2 = false;
					go = true;
				}
			//}
		}
	}
	//Activates one of the slow open or closes for the height
	private class exp2 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//if(frame.getHeight() == 600 || frame.getHeight() == 200) {
				if(frame.getHeight() > 200) {
					go1 = false;
					go22 = true;
				} else {
					go22 = false;
					go1 = true;
				}
			//}
		}
	}
	
	
	//MAKE GUI PIECES
	//Makes the JLabel instructions
	public void makeInstructions(){
		instruc.setFont(new Font("arial", Font.BOLD, 23));
		instruc.setForeground(Color.WHITE);
		instruc.setBounds(395, 2, 400, 40);
		frame.getContentPane().add(instruc);
		
		instructions2.setFont(new Font("arial", Font.BOLD, 16));
		instructions2.setForeground(Color.WHITE);
		instructions2.setBounds(400, 30, 400, 40);
		frame.getContentPane().add(instructions2);
		
		instructions3.setFont(new Font("arial", Font.BOLD, 16));
		instructions3.setForeground(Color.WHITE);
		instructions3.setBounds(400, 60, 400, 40);
		frame.getContentPane().add(instructions3);
		
		instructions4.setFont(new Font("arial", Font.BOLD, 16));
		instructions4.setForeground(Color.WHITE);
		instructions4.setBounds(400, 90, 400, 40);
		frame.getContentPane().add(instructions4);
	}
	//Makes all of the JButtons on the full calculator
	public void makeButtons() {
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.WHITE);
		b1.setFont(new Font("arial", Font.BOLD, 30));
		b1.setBounds(20, 420, 70, 70);
		b1.setFocusable(false);
		b1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		frame.getContentPane().add(b1);
		b1.addActionListener(B1);
		
		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.WHITE);
		b2.setFont(new Font("arial", Font.BOLD, 30));
		b2.setBounds(110, 420, 70, 70);
		b2.setFocusable(false);
		b2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		frame.getContentPane().add(b2);
		b2.addActionListener(B2);
		
		b3.setBackground(Color.BLACK);
		b3.setForeground(Color.WHITE);
		b3.setFont(new Font("arial", Font.BOLD, 30));
		b3.setBounds(200, 420, 70, 70);
		b3.setFocusable(false);
		b3.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		frame.getContentPane().add(b3);
		b3.addActionListener(B3);
		
		b4.setBackground(Color.BLACK);
		b4.setForeground(Color.WHITE);
		b4.setFont(new Font("arial", Font.BOLD, 30));
		b4.setBounds(20, 340, 70, 70);
		b4.setFocusable(false);
		b4.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		frame.getContentPane().add(b4);
		b4.addActionListener(B4);
		
		b5.setBackground(Color.BLACK);
		b5.setForeground(Color.WHITE);
		b5.setFont(new Font("arial", Font.BOLD, 30));
		b5.setBounds(110, 340, 70, 70);
		b5.setFocusable(false);
		b5.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		frame.getContentPane().add(b5);
		b5.addActionListener(B5);
		
		b6.setBackground(Color.BLACK);
		b6.setForeground(Color.WHITE);
		b6.setFont(new Font("arial", Font.BOLD, 30));
		b6.setBounds(200, 340, 70, 70);
		b6.setFocusable(false);
		b6.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		frame.getContentPane().add(b6);
		b6.addActionListener(B6);
		
		b7.setBackground(Color.BLACK);
		b7.setForeground(Color.WHITE);
		b7.setFont(new Font("arial", Font.BOLD, 30));
		b7.setBounds(20, 260, 70, 70);
		b7.setFocusable(false);
		b7.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		frame.getContentPane().add(b7);
		b7.addActionListener(B7);
		
		b8.setBackground(Color.BLACK);
		b8.setForeground(Color.WHITE);
		b8.setFont(new Font("arial", Font.BOLD, 30));
		b8.setBounds(110, 260, 70, 70);
		b8.setFocusable(false);
		b8.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		frame.getContentPane().add(b8);
		b8.addActionListener(B8);
		
		b9.setBackground(Color.BLACK);
		b9.setForeground(Color.WHITE);
		b9.setFont(new Font("arial", Font.BOLD, 30));
		b9.setBounds(200, 260, 70, 70);
		b9.setFocusable(false);
		b9.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		frame.getContentPane().add(b9);
		b9.addActionListener(B9);
		
		b0.setBackground(Color.BLACK);
		b0.setForeground(Color.WHITE);
		b0.setFont(new Font("arial", Font.BOLD, 30));
		b0.setBounds(110, 500, 70, 70);
		b0.setFocusable(false);
		b0.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		frame.getContentPane().add(b0);
		b0.addActionListener(B0);
		
		bdot.setBackground(Color.BLACK);
		bdot.setForeground(Color.WHITE);
		bdot.setFont(new Font("arial", Font.BOLD, 30));
		bdot.setBounds(200, 500, 70, 70);
		bdot.setFocusable(false);
		bdot.setBorder(BorderFactory.createLineBorder(new Color(255, 187, 0), 4));
		frame.getContentPane().add(bdot);
		bdot.addActionListener(Bdot);
		
		be.setBackground(Color.BLACK);
		be.setForeground(Color.WHITE);
		be.setFont(new Font("arial", Font.BOLD, 30));
		be.setBounds(290, 500, 70, 70);
		be.setFocusable(false);
		be.setBorder(BorderFactory.createLineBorder(new Color(255, 187, 0), 4));
		frame.getContentPane().add(be);
		be.addActionListener(Be);
		
		ba.setBackground(Color.BLACK);
		ba.setForeground(Color.WHITE);
		ba.setFont(new Font("arial", Font.BOLD, 30));
		ba.setBounds(20, 500, 70, 70);
		ba.setFocusable(false);
		ba.setBorder(BorderFactory.createLineBorder(new Color(255, 187, 0), 4));
		frame.getContentPane().add(ba);
		ba.addActionListener(Ba);
		
		bs.setBackground(Color.BLACK);
		bs.setForeground(Color.WHITE);
		bs.setFont(new Font("arial", Font.BOLD, 30));
		bs.setBounds(290, 420, 70, 70);
		bs.setFocusable(false);
		bs.setBorder(BorderFactory.createLineBorder(new Color(255, 187, 0), 4));
		frame.getContentPane().add(bs);
		bs.addActionListener(Bs);
		
		bstar.setBackground(Color.BLACK);
		bstar.setForeground(Color.WHITE);
		bstar.setFont(new Font("arial", Font.BOLD, 30));
		bstar.setBounds(290, 340, 70, 70);
		bstar.setFocusable(false);
		bstar.setBorder(BorderFactory.createLineBorder(new Color(255, 187, 0), 4));
		frame.getContentPane().add(bstar);
		bstar.addActionListener(Bstar);
		
		bd.setBackground(Color.BLACK);
		bd.setForeground(Color.WHITE);
		bd.setFont(new Font("arial", Font.BOLD, 30));
		bd.setBounds(290, 260, 70, 70);
		bd.setFocusable(false);
		bd.setBorder(BorderFactory.createLineBorder(new Color(255, 187, 0), 4));
		frame.getContentPane().add(bd);
		bd.addActionListener(Bd);
		
		b.setBackground(Color.BLACK);
		b.setForeground(Color.WHITE);
		b.setFont(new Font("arial", Font.BOLD, 30));
		b.setBounds(203, 200, 150, 40);
		b.setFocusable(false);
		b.setBorder(BorderFactory.createLineBorder(new Color(255, 187, 0), 4));
		frame.getContentPane().add(b);
		b.addActionListener(B);
		
		bc.setBackground(Color.BLACK);
		bc.setForeground(Color.WHITE);
		bc.setFont(new Font("arial", Font.BOLD, 30));
		bc.setBounds(23, 200, 150, 40);
		bc.setFocusable(false);
		bc.setBorder(BorderFactory.createLineBorder(new Color(255, 187, 0), 4));
		frame.getContentPane().add(bc);
		bc.addActionListener(Bc);
		
		bp.setBackground(Color.BLACK);
		bp.setForeground(Color.WHITE);
		bp.setFont(new Font("arial", Font.BOLD, 25));
		bp.setBounds(380, 200, 100, 70);
		bp.setFocusable(false);
		bp.setBorder(BorderFactory.createLineBorder(new Color(255, 187, 0), 4));
		frame.getContentPane().add(bp);
		bp.addActionListener(Bp);
		
		br.setBackground(Color.BLACK);
		br.setForeground(Color.WHITE);
		br.setFont(new Font("arial", Font.BOLD, 25));
		br.setBounds(490, 200, 80, 70);
		br.setFocusable(false);
		br.setBorder(BorderFactory.createLineBorder(new Color(255, 187, 0), 4));
		frame.getContentPane().add(br);
		br.addActionListener(Br);
	}
	
	//MOUSE FOLLOWS
	//Mouse functions like clicking and releasing
	public class mouseAdapt extends MouseAdapter {
    	private Thread follow = new Thread();
        public void mousePressed(MouseEvent e) {
        	follow = new follow();
        	follow.start();
        }
        public void mouseReleased(MouseEvent e) {
        	follow.stop();
        }
	}
	//Class used to make the frame follow mouse
	private class follow extends Thread {
		private boolean first = true; //used to get og mouse pos
		public void run() {
			while(true) {
				try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
				Point mousePos = MouseInfo.getPointerInfo().getLocation();
				if(!first) {
					mouseLastx = (int)mousePos.getX() - mouseLastx;
					mouseLasty = (int)mousePos.getY() - mouseLasty;
					frame.setLocation(frame.getX() + mouseLastx, frame.getY() + mouseLasty);
				}
				first = false;
				mouseLastx = (int)mousePos.getX();
				mouseLasty = (int)mousePos.getY();
			}
		}
	}
	
	
	//FULL CALC ALL BUTTONS
	//b1-b9 are the number buttons
	private class b1 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ce += "1";
			addNum();
			
		}
	}
	private class b2 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ce += "2";
			addNum();
			
		}
	}
	private class b3 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ce += "3";
			addNum();
			
		}
	}
	private class b4 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ce += "4";
			addNum();
			
		}
	}
	private class b5 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ce += "5";
			addNum();
			
		}
	}
	private class b6 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ce += "6";
			addNum();
			
		}
	}
	private class b7 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ce += "7";
			addNum();
			
		}
	}
	private class b8 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ce += "8";
			addNum();
			
		}
	}
	private class b9 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ce += "9";
			addNum();
			
		}
	}
	private class b0 implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ce += "0";
			addNum();
			
		}
	}
	//multiplication
	private class bstar implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!currentNums.get(currentNums.size() - 1).equals("")) {
				currentNums.add("x");
				currentNums.add("");
				ce = "";
			}
		}
	}
	//point like 0.0
	private class bdot implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!periodCheck()) {ce += "."; addNum();}
		}
	}
	//root
	private class br implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!currentNums.get(currentNums.size() - 1).equals("")) {
				currentNums.add("r");
				currentNums.add("");
				ce = "";
			}
		}
	}
	//power
	private class bp implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!currentNums.get(currentNums.size() - 1).equals("")) {
				currentNums.add("p");
				currentNums.add("");
				ce = "";
			}
		}
	}
	//equals
	private class be implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(currentNums.size() > 2) {
				calculate();
			}
		}
	}
	//add
	private class ba implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!currentNums.get(currentNums.size() - 1).equals("")) {
				currentNums.add("+");
				currentNums.add("");
				ce = "";
			}
		}
	}
	//subtract
	private class bs implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!currentNums.get(currentNums.size() - 1).equals("")) {
				currentNums.add("-");
				currentNums.add("");
				ce = "";
			}
		}
	}
	//back
	private class b implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(ce.length() > 1) {ce = ce.substring(0, ce.length() - 1);addNum();}
			else {ce = "";addNum();}
		}
	}
	//divide
	private class bd implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!currentNums.get(currentNums.size() - 1).equals("")) {
				currentNums.add("/");
				currentNums.add("");
				ce = "";
			}
		}
	}
	//Clear
	private class bc implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			currentNums.clear();
			ce = "";
		}
	}
	//Symbol Switch
	private class bss implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			
		}
		
	}
	
	//FIELDS
	private JFrame frame = new JFrame("EZCalc");
	//Usefull classes
	private mouseAdapt mouseAdapt = new mouseAdapt();
	private changeText changeText = new changeText(); private Thread textchange = new Thread(); //constantly changes text
	//Booleans
	private boolean go = false, go2 = false, go22 = false, go1 = false, slowmin = false, slowex = false; //open and closing booleans
	//Opening and closing
	private Thread slowExit = new slowExit();
	private Thread slowMinimize = new slowMinimize();
	private exp exp = new exp();
	private exp2 exp2 = new exp2();
	private slowOpen1 slowOpen = new slowOpen1();
	private slowClose1 slowClose = new slowClose1();
	private Thread so = slowOpen; 
	private Thread sc = slowClose;
	private slowOpen2 slowOpen2 = new slowOpen2();
	private slowClose2 slowClose2 = new slowClose2();
	private Thread so2 = slowOpen2; 
	private Thread sc2 = slowClose2;
	//Fields to help with calculating
	private String ce = ""; //ce means current num on screen
	private ArrayList<String> currentNums = new ArrayList<String>(); //the current equation
	//Integers
	private int mouseLastx, mouseLasty, minimizex, minimizey;
	//Button classes
	private b1 B1 = new b1(); private b2 B2 = new b2();private b3 B3 = new b3();private b4 B4 = new b4();private  b5 B5 = new b5();private b6 B6 = new b6();
	private b7 B7 = new b7();bdot Bdot = new bdot();private b8 B8 = new b8();private b9 B9 = new b9();private b0 B0 = new b0();private bstar Bstar = new bstar();
	private bp Bp = new bp();private br Br = new br();private be Be = new be();private ba Ba = new ba();private bs Bs = new bs(); private b B = new b(); 
	private bd Bd = new bd(); private bc Bc = new bc(); private minimizeAction minimizeAction = new minimizeAction();
	//JButtons
	private JButton b1 = new JButton("1"), b2 = new JButton("2"), b3 = new JButton("3"), b4 = new JButton("4"), b5 = new JButton("5");
	private JButton b6 = new JButton("6"), b7 = new JButton("7"), b8 = new JButton("8"), b9 = new JButton("9"), b0 = new JButton("0"), bdot = new JButton("."); 
	private JButton bstar = new JButton("*"), br = new JButton("Root"), bp = new JButton("Power"), be = new JButton("="),  ba = new JButton("+"), bs = new JButton("-"); 
	private JButton b = new JButton("DELETE"), bd = new JButton("/"), bc = new JButton("CLEAR"), exit = new JButton("X"), expand = new JButton(">");
	private JButton expand2 = new JButton("v");	 private JButton minimize = new JButton();
	private close close = new close();
	private boolean shift = false;
	//JLabels
	private JLabel calc = new JLabel("     EZ  Calculator"), instructions = new JLabel("SHIFT	 * = Multiply         / = Divide");
	private JLabel instructions2 = new JLabel("C = Clear       R = Root"), instructions3 = new JLabel("ENTER = Get Answer"), instructions4 = new JLabel("P = Power");
	private JLabel window = new JLabel(), instructionsp2 = new JLabel("MINUS - = Subtract      SHIFT + = Add"), instruc = new JLabel("More Functions"); 
	
}