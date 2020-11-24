package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import function.Astar;
import function.Move;
import function.Shuffle;

public class ComVsPlGui extends JFrame implements ActionListener {

	private JPanel Upanel;
	private JButton startButton;
	private JPanel Dpanel1,Dpanel2;
	private static int[][] puzzleNum = Shuffle.PuzShuffle(3);
	private JButton[][] buttons1;
	private panelDownSetPl plyPanel = new panelDownSetPl(puzzleNum);
	private panelDownSetCom comPanel = new panelDownSetCom(puzzleNum);
	private int[][] setNum1, setNum2;
	private int[][] End;
	private int count = 1;
	private ArrayList<int[][]> multiList = new ArrayList<int[][]>();
	private ComMove move;
	private Thread th;
	
	ComVsPlGui() {
		setTitle("Computer VS Player");
		setBounds(100, 100, 1240, 600);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				th.interrupt();
				dispose();
			}
		});
		Upanel = new JPanel();
		Upanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		Upanel.setBackground(Color.LIGHT_GRAY);
		startButton = new JButton("GAME START");
		startButton.addActionListener(this);
		Upanel.add(startButton);
		add(Upanel, BorderLayout.NORTH);
		
		setVisible(true);
		

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == startButton) {
			Dpanel2 = new JPanel();
			Dpanel2.setLayout(new GridLayout(1,1, 5, 0));
			Dpanel2.add(comPanel);
			add(Dpanel2,BorderLayout.EAST);
			StartCom();
			setVisible(true);
			Dpanel1 = new JPanel();
			Dpanel1.setLayout(new GridLayout(1,1, 5, 0));
			Dpanel1.add(plyPanel);
			setFocusable(true);
			requestFocus();
			addKeyListener(new plKeyMove());
			add(Dpanel1, BorderLayout.WEST);
			setVisible(true);
			startButton.setEnabled(false);
		}
	}

	class plKeyMove extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			setNum1 = plyPanel.GetMoveNum1();
			buttons1 = plyPanel.GetButton1();
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP: {
				Move.moveDir(setNum1, 4, 3);
				plyPanel.removeAll();
				plyPanel.ButtonMove1(setNum1, buttons1);
				setVisible(true);
				LeftshowMessage(setNum1, 3);
				break;
			}
			case KeyEvent.VK_DOWN: {
				Move.moveDir(setNum1, 3, 3);
				plyPanel.removeAll();
				plyPanel.ButtonMove1(setNum1, buttons1);
				setVisible(true);
				LeftshowMessage(setNum1, 3);
				break;
			}
			case KeyEvent.VK_LEFT: {
				Move.moveDir(setNum1, 1, 3);
				plyPanel.removeAll();
				plyPanel.ButtonMove1(setNum1, buttons1);
				setVisible(true);
				LeftshowMessage(setNum1, 3);
				break;
			}
			case KeyEvent.VK_RIGHT: {
				Move.moveDir(setNum1, 2, 3);
				plyPanel.removeAll();
				plyPanel.ButtonMove1(setNum1, buttons1);
				setVisible(true);
				LeftshowMessage(setNum1, 3);
				break;
			}

			}
		}
	}


	public void StartCom() {
		End = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (End[i][j] == 9) {
					End[i][j] = 0;
				} else {
					End[i][j] = count;
					count += 1;
				}
			}
		}
		setNum2 = comPanel.GetMoveNum2();
		Astar.Astarsolve(setNum2, End,multiList,3);
		move = new ComMove(multiList,comPanel,this);
		th = new Thread(move);
		th.start();
	}

	public void LeftshowMessage(int[][] puzzleNum, int puzNum) {
		int num = 1;
		for (int i = 0; i < puzNum; i++) {
			for (int j = 0; j < puzNum; j++) {
				if (num != puzzleNum[i][j]) {
					return;
				}
				if (num == puzNum * puzNum - 1) {
					num = 0;
				} else {
					num++;
				}
			}
		}
		th.interrupt();
		JOptionPane.showMessageDialog(null, "사용자 승리!", "Congraturations", JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}
	
}

class panelDownSetPl extends JPanel {

	private int[][] moveNum = new int[3][3];
	private int[][] Num1 = new int[3][3];
	private JButton[][] buttons;

	panelDownSetPl(int[][] puzzleNum) {
		setLayout(new GridLayout(3, 3, 2, 2));
		buttons = new JButton[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Num1[i][j] = puzzleNum[i][j];
			}
		}
		moveNum = Num1;
		ButtonMove1(Num1, buttons);
		setVisible(true);
	}

	public void ButtonMove1(int[][] puzzleNum, JButton[][] buttons) {  //플레이어가 움직일 때
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (puzzleNum[i][j] == 0) {
					buttons[i][j] = new JButton("");
					buttons[i][j].setPreferredSize(new Dimension(200,200));
					buttons[i][j].setEnabled(false);
					add(buttons[i][j]);
				} else {
					String text = Integer.toString(puzzleNum[i][j]);
					buttons[i][j] = new JButton(text);
					buttons[i][j].setPreferredSize(new Dimension(200,200));
					buttons[i][j].setFont(new Font("맑은 고딕", Font.BOLD, 90));
					buttons[i][j].setEnabled(false);
					buttons[i][j].setBackground(Color.BLACK);
					add(buttons[i][j]);
				}
			}
		}
	}


	public int[][] GetMoveNum1() {
		return moveNum;
	}

	public JButton[][] GetButton1() {
		return buttons;
	}
}

class panelDownSetCom extends JPanel {

	private int[][] moveNum = new int[3][3];
	private int[][] Num2 = new int[3][3];
	private JButton[][] buttons;

	panelDownSetCom(int[][] puzzleNum) {
		setLayout(new GridLayout(3, 3, 2, 2));
		buttons = new JButton[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Num2[i][j] = puzzleNum[i][j];
			}
		}
		moveNum = Num2;
		ButtonMove2(moveNum, buttons);
		setVisible(true);
		
		
	}
	
	public void Move(int[][] puzzleNum) { //컴퓨터가 움직일때
		removeAll();
		setLayout(new GridLayout(3, 3, 2, 2));
		JButton[][] buttons = new JButton[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (puzzleNum[i][j] == 0) {
					buttons[i][j] = new JButton("");
					buttons[i][j].setPreferredSize(new Dimension(200,200));
					buttons[i][j].setEnabled(false);
					add(buttons[i][j]);
				} else {
					String text = Integer.toString(puzzleNum[i][j]);
					buttons[i][j] = new JButton(text);
					buttons[i][j].setFont(new Font("맑은 고딕", Font.BOLD, 90));
					buttons[i][j].setPreferredSize(new Dimension(200,200));
					buttons[i][j].setEnabled(false);
					buttons[i][j].setBackground(Color.BLACK);
					add(buttons[i][j]);
				}
			}
		}
	}

	public void ButtonMove2(int[][] puzzleNum, JButton[][] buttons) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (puzzleNum[i][j] == 0) {
					buttons[i][j] = new JButton("");
					buttons[i][j].setEnabled(false);
					buttons[i][j].setPreferredSize(new Dimension(200,200));
					add(buttons[i][j]);
				} else {
					String text = Integer.toString(puzzleNum[i][j]);
					buttons[i][j] = new JButton(text);
					buttons[i][j].setFont(new Font("맑은 고딕", Font.BOLD, 90));
					buttons[i][j].setPreferredSize(new Dimension(200,200));
					buttons[i][j].setEnabled(false);
					buttons[i][j].setBackground(Color.BLACK);
					add(buttons[i][j]);
				}
			}
		}
		setVisible(true);
	}

	public int[][] GetMoveNum2() {
		return moveNum;
	}

	public JButton[][] GetButton2() {
		return buttons;
	}
	
	
}

class ComMove implements Runnable { //컴퓨터가 움직이는 쓰레드

	private ArrayList<int[][]> multiList = new ArrayList<int[][]>(); 
	private int[][] puz;
	private panelDownSetCom comPanel;
	private JFrame fr;

	ComMove(ArrayList<int[][]> multiList,panelDownSetCom comPanel,JFrame fr)
	{
		this.multiList = multiList;
		this.comPanel = comPanel;
		this.fr = fr;
		
	}
	
	@Override
	public void run() {
			try {
				for(int i = 0; i< multiList.size();i++) {
					Thread.sleep(500);
					puz = new int[3][3];
					puz = multiList.get(i);
					comPanel.Move(puz);
					comPanel.revalidate();
					comPanel.repaint();
				}
				JOptionPane.showMessageDialog(null, "컴퓨터 승리!", "Congraturations", JOptionPane.INFORMATION_MESSAGE);
				fr.dispose();
			}catch(InterruptedException e) { }
		
		
	}
	
}