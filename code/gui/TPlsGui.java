package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import function.*;

public class TPlsGui extends JFrame implements ActionListener{
	
	private JPanel Upanel;
	private JButton startButton;
	private JPanel Dpanel;
	private static int[][] puzzleNum = Shuffle.PuzShuffle(3);
	private JButton[][] buttons1,buttons2;
	private panelDownSet1 pl1Panel = new panelDownSet1(puzzleNum);
	private panelDownSet2 pl2Panel = new panelDownSet2(puzzleNum);
	private int[][] setNum1;
	private int[][] setNum2;
	
	
	TPlsGui(){
		setTitle("Player VS Player");
		setBounds(100, 100, 1200, 600);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		Upanel = new JPanel();
		Upanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
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
		if(o == startButton) {
			Dpanel = new JPanel();
			Dpanel.setLayout(new GridLayout(1,2,15,0));
			Dpanel.add(pl1Panel);
			Dpanel.add(pl2Panel);
			setFocusable(true);
			requestFocus();
			addKeyListener(new plyKeyMove());
			add(Dpanel, BorderLayout.CENTER);
			startButton.setEnabled(false);
			setVisible(true);
		}
	}
	
	
	
	
	class plyKeyMove extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			setNum1 = pl1Panel.GetMoveNum1();
			buttons1 = pl1Panel.GetButton1();
			setNum2 = pl2Panel.GetMoveNum2();
			buttons2 = pl2Panel.GetButton2();
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP: { //플레이어 1
				Move.moveDir(setNum1,4, 3);
				pl1Panel.removeAll();
				pl1Panel.ButtonMove1(setNum1,buttons1);
				setVisible(true);
				LeftshowMessage(setNum1,3);
				break;
			}
			case KeyEvent.VK_DOWN: {
				Move.moveDir(setNum1,3, 3);
				pl1Panel.removeAll();
				pl1Panel.ButtonMove1(setNum1,buttons1);
				setVisible(true);
				LeftshowMessage(setNum1,3);
				break;
			}
			case KeyEvent.VK_LEFT: {
				Move.moveDir(setNum1,1, 3);
				pl1Panel.removeAll();
				pl1Panel.ButtonMove1(setNum1,buttons1);
				setVisible(true);
				LeftshowMessage(setNum1,3);
				break;
			}
			case KeyEvent.VK_RIGHT: {
				Move.moveDir(setNum1,2, 3);
				pl1Panel.removeAll();
				pl1Panel.ButtonMove1(setNum1,buttons1);
				setVisible(true);
				LeftshowMessage(setNum1,3);
				break;
			}
			
			//플레이어 2
			case KeyEvent.VK_W: { //업
				Move.moveDir(setNum2, 4, 3);
				pl2Panel.removeAll();
				pl2Panel.ButtonMove2(setNum2, buttons2);
				setVisible(true);
				RightshowMessage(setNum2,3);
				break;
			}
			case KeyEvent.VK_S: { //다운
				Move.moveDir(setNum2, 3, 3);
				pl2Panel.removeAll();
				pl2Panel.ButtonMove2(setNum2, buttons2);
				setVisible(true);
				RightshowMessage(setNum2,3);
				break;
			}
			case KeyEvent.VK_A: { //레프트
				Move.moveDir(setNum2, 1, 3);
				pl2Panel.removeAll();
				pl2Panel.ButtonMove2(setNum2, buttons2);
				setVisible(true);
				RightshowMessage(setNum2,3);
				break;
			}
			case KeyEvent.VK_D: { //라이트
				Move.moveDir(setNum2, 2, 3);
				pl2Panel.removeAll();
				pl2Panel.ButtonMove2(setNum2, buttons2);
				setVisible(true);
				RightshowMessage(setNum2,3);
				break;
			}
			
			
			}

			
		}
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
		JOptionPane.showMessageDialog(null, "왼쪽 플레이어 승리!", "Congraturations", JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}
	public void RightshowMessage(int[][] puzzleNum, int puzNum) {
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
		JOptionPane.showMessageDialog(null, "오른쪽 플레이어 승리!", "Congraturations", JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}
}

class panelDownSet1 extends JPanel{
	
	private int[][] moveNum = new int[3][3];
	private int[][] Num1 = new int[3][3];
	private JButton[][] buttons;
	panelDownSet1(int[][] puzzleNum) {
		setLayout(new GridLayout(3,3,2,2));
		buttons = new JButton[3][3];
		for(int i = 0; i < 3 ; i++) {
			for(int j = 0; j < 3 ;j++) {
				Num1[i][j] = puzzleNum[i][j];
			}
		}
		moveNum = Num1;
		ButtonMove1(Num1, buttons);
		setVisible(true);
	}
	
	public void ButtonMove1(int[][] puzzleNum, JButton[][] buttons) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (puzzleNum[i][j] == 0) {
					buttons[i][j] = new JButton("");
					buttons[i][j].setEnabled(false);
					add(buttons[i][j]);
				} else {
					String text = Integer.toString(puzzleNum[i][j]);
					buttons[i][j] = new JButton(text);
					buttons[i][j].setFont(new Font("맑은 고딕", Font.BOLD, 90));
					buttons[i][j].setEnabled(false);
					buttons[i][j].setBackground(Color.BLACK);
					add(buttons[i][j]);
				}
			}
		}
	}
	public int[][] GetMoveNum1(){
		return moveNum;
	}
	public JButton[][] GetButton1(){
		return buttons;
	}
}

class panelDownSet2 extends JPanel{
	
	private int[][] moveNum = new int[3][3];
	private int[][] Num2 = new int[3][3];
	private JButton[][] buttons;
	panelDownSet2(int[][] puzzleNum) {
		setLayout(new GridLayout(3,3,2,2));
		buttons = new JButton[3][3];
		for(int i = 0; i < 3 ; i++) {
			for(int j = 0; j < 3 ;j++) {
				Num2[i][j] = puzzleNum[i][j];
			}
		}
		moveNum = Num2;
		ButtonMove2(Num2, buttons);
		setVisible(true);
	}
	
	public void ButtonMove2(int[][] puzzleNum, JButton[][] buttons) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (puzzleNum[i][j] == 0) {
					buttons[i][j] = new JButton("");
					buttons[i][j].setEnabled(false);
					add(buttons[i][j]);
				} else {
					String text = Integer.toString(puzzleNum[i][j]);
					buttons[i][j] = new JButton(text);
					buttons[i][j].setFont(new Font("맑은 고딕", Font.BOLD, 90));
					buttons[i][j].setEnabled(false);
					buttons[i][j].setBackground(Color.BLACK);
					add(buttons[i][j]);
				}
			}
		}
	}
	public int[][] GetMoveNum2(){
		return moveNum;
	}
	public JButton[][] GetButton2(){
		return buttons;
	}
}



