package function;

import javax.swing.JLabel;

public class Timer implements Runnable { //���� ������ �Ҷ� �ð��� �帣���ϴ� ������
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
