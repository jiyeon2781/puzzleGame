package function;

import javax.swing.JLabel;

public class Timer implements Runnable { //퍼즐 게임을 할때 시간을 흐르게하는 쓰레드
	JLabel timerL;
	private static int count;
	public Timer(JLabel timerL,int count) {
		this.timerL = timerL;
		Timer.count = count;
	}
	
	public void run() {
		boolean end = true;
		while(end) {
			timerL.setText(Integer.toString(count));
			count++;
			try {
				Thread.sleep(1000);
			}catch (InterruptedException e) {
				return;
			}
		}
	}
	public static int setTime() {
		return count;
	}
}
