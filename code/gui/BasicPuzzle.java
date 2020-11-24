package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import function.Astar;
import function.Move;
import function.Open;
import function.Save;
import function.Shuffle;
import function.Timer;

public class BasicPuzzle extends JFrame implements ActionListener {
	private JPanel Upanel;
	private int[][] puzzleNum;
	private JMenuBar gameMb;
	private JMenu game,solve;
	private JMenuItem open, save, exit,bfs,astar;
	private JButton newButton;
	private JLabel timerL;
	private newButtonPush nbp;
	private MoveButtonPush mbp;
	private SolveButtonPush sbp;
	private Timer run;
	private Thread th,th2;
	private int Number;
	private int time_count;
	private int exitCount;
	private int completeCount;
	private JButton[][] buttons;
	private int[][] end;
	private ArrayList<int[][]> multiList = new ArrayList<int[][]>();
	private solveA mov;
	private boolean buttonEnable;

	public BasicPuzzle(int puzNum) {
		Number = puzNum;
		setTitle(Number + "*" + Number + " Puzzle");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(buttonEnable == true) {
					dispose();
					th.interrupt();
				}
				else {
					dispose();
				}
			}
		});
		setBounds(100, 100, 200 * Number, 200 * Number);
		
		gameMb = new JMenuBar();
		setJMenuBar(gameMb);

		game = new JMenu("GAME");
		gameMb.add(game);

		open = new JMenuItem("OPEN");
		open.addActionListener(this);
		game.add(open);
		game.addSeparator();
		save = new JMenuItem("SAVE");
		save.addActionListener(this);
		game.add(save);
		game.addSeparator();
		exit = new JMenuItem("EXIT");
		exit.addActionListener(this);
		game.add(exit);
		
		solve = new JMenu("SOLVE");
		gameMb.add(solve);
		buttonEnable = false;
		astar = new JMenuItem("Auto Solve");
		astar.addActionListener(this);
		solve.add(astar);
		

		setJMenuBar(gameMb);
		
		

		Upanel = new JPanel();
		Upanel.setLayout(new BorderLayout(300, 20));
		Upanel.setBackground(Color.LIGHT_GRAY);
		timerL = new JLabel();
		timerL.setFont(new Font("Gothic", Font.ITALIC, 15));
		Upanel.add(timerL, BorderLayout.WEST);
		newButton = new JButton("NEW");
		newButton.addActionListener(this);
		Upanel.add(newButton, BorderLayout.EAST);

		add(Upanel, BorderLayout.NORTH);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == newButton) { // 시작버튼
			nbp = new newButtonPush(Number);
			time_count = 0;
			run = new Timer(timerL, time_count);
			th = new Thread(run);
			th.start();
			setFocusable(true);
			requestFocus();
			addKeyListener(new KeyMove());
			add(nbp, null);
			newButton.setEnabled(false);
			open.setEnabled(false);
			buttonEnable = true;
			puzzleNum = nbp.getPuzzleNum();
			setVisible(true);
		}

		else if (o == open) { // 저장한 게임을 불러올때
			Open.openFile(Number);
			puzzleNum = Open.setPuzNum();
			time_count = Open.setTimerCount();
			if (time_count == 0) {
				return;
			}
			else {
				buttonEnable = true;
				run = new Timer(timerL, time_count);
				th = new Thread(run);
				th.start();
				mbp = new MoveButtonPush(puzzleNum, Number);
				setFocusable(true);
				requestFocus();
				addKeyListener(new openKeyMove());
				add(mbp, null);
				setVisible(true);
				newButton.setEnabled(false);
				open.setEnabled(false);
			}

		}

		else if (o == save) { // 게임을 저장할떄
			exitCount = Timer.setTime();
			if(buttonEnable == false) {
				JOptionPane.showMessageDialog(this, "게임 실행부터 해주세요!", "ERROR!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				th.interrupt();
				Save.SaveFile(Number, puzzleNum, exitCount);
				JOptionPane.showMessageDialog(this, "저장되었습니다.", "Save complete!", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			
		}


		else if (o == exit) { // 나갈때
			int ans = JOptionPane.showConfirmDialog(this, "저장하시겠습니까?", "Do you want Save?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(ans == 0) {
				exitCount = Timer.setTime();
				Save.SaveFile(Number, puzzleNum, exitCount);
				th.interrupt();
				JOptionPane.showMessageDialog(this, "저장되었습니다.", "Save complete!", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			else if(ans == 1) {
				dispose();
				th.interrupt();
			}
			

		}
		
		else if (o == astar) {
			if(buttonEnable == true) {
				astarsolve();
			}
			else {
				JOptionPane.showMessageDialog(this, "게임 실행부터 해주세요!", "ERROR!", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	

	
	
	public void astarsolve() {
		end = new int[Number][Number];
		int count = 1;
		for(int i = 0; i < Number;i++) {
			for(int j = 0; j < Number;j++) {
				end[i][j] = count;
				if(i == Number-1 && j == Number-1) {
					end[i][j] = 0;
				}
				count++;
			}
		}
		th.interrupt();
		Astar.Astarsolve(puzzleNum, end,multiList,Number);
		sbp = new SolveButtonPush(puzzleNum, Number);
		add(sbp,null);
		mov = new solveA(multiList,Number,sbp,this);
		th2 = new Thread(mov);
		th2.setDaemon(true);
		th2.start();
	}

	class openKeyMove extends KeyAdapter { // open을 눌렀을때 받는 키 입력
		public void keyPressed(KeyEvent e) {
			puzzleNum = Open.setPuzNum();
			mbp = new MoveButtonPush(puzzleNum, Number);
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP: {
				setEnabled(false);
				Move.moveDir(puzzleNum, 4, Number);
				remove(mbp);
				mbp = new MoveButtonPush(puzzleNum, Number);
				add(mbp, null);
				setVisible(true);
				setEnabled(true);
				showMessage(puzzleNum, Number);
				break;
			}
			case KeyEvent.VK_DOWN: {
				setEnabled(false);
				Move.moveDir(puzzleNum, 3, Number);
				remove(mbp);
				mbp = new MoveButtonPush(puzzleNum, Number);
				add(mbp, null);
				setVisible(true);
				setEnabled(true);
				showMessage(puzzleNum, Number);
				break;
			}
			case KeyEvent.VK_LEFT: {
				setEnabled(false);
				Move.moveDir(puzzleNum, 1, Number);
				remove(mbp);
				mbp = new MoveButtonPush(puzzleNum, Number);
				add(mbp, null);
				setVisible(true);
				setEnabled(true);
				showMessage(puzzleNum, Number);
				break;
			}
			case KeyEvent.VK_RIGHT: {
				setEnabled(false);
				Move.moveDir(puzzleNum, 2, Number);
				remove(mbp);
				mbp = new MoveButtonPush(puzzleNum, Number);
				add(mbp, null);
				setVisible(true);
				setEnabled(true);
				showMessage(puzzleNum, Number);
				break;
			}

			}
		}
	}
	

	class KeyMove extends KeyAdapter { // 키입력 받을떄
		public void keyPressed(KeyEvent e) {
			puzzleNum = nbp.getMoveNum();
			buttons = nbp.getButton();
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP: {
				Move.moveDir(puzzleNum, 4, Number);
				nbp.removeAll();
				nbp.ButtonMove(puzzleNum, buttons, Number);
				setVisible(true);
				showMessage(puzzleNum, Number);
				break;
			}
			case KeyEvent.VK_DOWN: {
				Move.moveDir(puzzleNum, 3, Number);
				nbp.removeAll();
				nbp.ButtonMove(puzzleNum, buttons, Number);
				setVisible(true);
				showMessage(puzzleNum, Number);
				break;
			}
			case KeyEvent.VK_LEFT: {
				Move.moveDir(puzzleNum, 1, Number);
				nbp.removeAll();
				nbp.ButtonMove(puzzleNum, buttons, Number);
				setVisible(true);
				showMessage(puzzleNum, Number);
				break;
			}
			case KeyEvent.VK_RIGHT: {
				Move.moveDir(puzzleNum, 2, Number);
				nbp.removeAll();
				nbp.ButtonMove(puzzleNum, buttons, Number);
				setVisible(true);
				showMessage(puzzleNum, Number);
				break;
			}

			}
		}
	}

	public void showMessage(int[][] puzzleNum, int puzNum) { // 퍼즐 다 맞췄을 때
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
		completeCount = Timer.setTime();
		JOptionPane.showMessageDialog(null, "이겼습니다! 시간은 " + (completeCount - 1) + "초 걸렸습니다!", "Congraturations",
				JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}

}

class SolveButtonPush extends JPanel { // 퍼즐 버튼 갱신
	private JButton[][] buttons;

	SolveButtonPush(int[][] moveNum, int puzNum) {
		setLayout(new GridLayout(puzNum, puzNum, 2, 2));
		buttons = new JButton[puzNum][puzNum];
		MoveButton(moveNum, puzNum);
		setVisible(true);
	}

	public void MoveButton(int[][] moveNum, int puzNum) {
		removeAll();
		setLayout(new GridLayout(puzNum, puzNum, 2, 2));
		JButton[][] buttons = new JButton[puzNum][puzNum];
		for (int i = 0; i <puzNum; i++) {
			for (int j = 0; j <puzNum; j++) {
				if (moveNum[i][j] == 0) {
					buttons[i][j] = new JButton("");
					buttons[i][j].setPreferredSize(new Dimension(200,200));
					buttons[i][j].setEnabled(false);
					add(buttons[i][j]);
				} else {
					String text = Integer.toString(moveNum[i][j]);
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
}

class MoveButtonPush extends JPanel { // 퍼즐 버튼 갱신
	private JButton[][] buttons;

	MoveButtonPush(int[][] moveNum, int puzNum) {
		setLayout(new GridLayout(puzNum, puzNum, 2, 2));
		buttons = new JButton[puzNum][puzNum];
		MoveButton(moveNum, puzNum);
		setVisible(true);
	}

	public void MoveButton(int[][] moveNum, int puzNum) {
		for (int i = 0; i < puzNum; i++) {
			for (int j = 0; j < puzNum; j++) {
				if (moveNum[i][j] == 0) {
					buttons[i][j] = new JButton("");
					buttons[i][j].setEnabled(false);
					add(buttons[i][j]);
				} else {
					String text = Integer.toString(moveNum[i][j]);
					buttons[i][j] = new JButton(text);
					buttons[i][j].setFont(new Font("맑은 고딕", Font.BOLD, 90));
					buttons[i][j].setEnabled(false);
					buttons[i][j].setBackground(Color.BLACK);
					add(buttons[i][j]);
				}
			}
		}
	}
}

class newButtonPush extends JPanel { // 퍼즐 버튼 생성
	private int[][] puzzleNum;
	private JButton[][] buttons;
	private int[][] moveNum;

	newButtonPush(int puzNum) {
		setLayout(new GridLayout(puzNum, puzNum, 2, 2));
		puzzleNum = new int[puzNum][puzNum];
		buttons = new JButton[puzNum][puzNum];
		puzzleNum = Shuffle.PuzShuffle(puzNum);
		moveNum = puzzleNum;
		ButtonMove(puzzleNum, buttons, puzNum);
		setVisible(true);

	}

	public void ButtonMove(int[][] puzzleNum, JButton[][] buttons, int puzNum) {
		for (int i = 0; i < puzNum; i++) {
			for (int j = 0; j < puzNum; j++) {
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
	public int[][] getPuzzleNum(){
		return this.puzzleNum;
	}


	public int[][] getMoveNum() {
		return this.moveNum;
	}

	public JButton[][] getButton() {
		return this.buttons;
	}
	
}

class solveA implements Runnable { //컴퓨터가 움직이는 쓰레드

	private ArrayList<int[][]> multiList = new ArrayList<int[][]>(); 
	private int[][] puz;
	private int Number;
	private SolveButtonPush sbp;
	private JFrame fr;
	private boolean hand;

	solveA(ArrayList<int[][]> multiList,int Number,SolveButtonPush sbp,JFrame fr)
	{
		this.multiList = multiList;
		this.Number = Number;
		this.sbp =sbp;
		this.fr = fr;
		
	}
	
	@Override
	public void run() {
		fr.setFocusable(false);
			try {
				for(int i = 0; i< multiList.size();i++) {
					Thread.sleep(200);
					puz = new int[Number][Number];
					puz = multiList.get(i);
					sbp.MoveButton(puz,Number);
					sbp.revalidate();
					sbp.repaint(); //새로고침
				}
				JOptionPane.showMessageDialog(null, "풀이가 완료되었습니다!!", "The End", JOptionPane.INFORMATION_MESSAGE);
				fr.dispose();
			}catch(InterruptedException e) { }
	}
	
}