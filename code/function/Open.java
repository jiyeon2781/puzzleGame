package function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Open { //저장한 파일을 오픈할때
	private static int[][] puzzleNum;
	private static int TimerCount;
	private static int count;

	public static void openFile(int Number)  {
		String[] token = new String[Number*Number +1];
		puzzleNum = new int[Number][Number];
		count = 0;
		File file = new File("puzzleSave" + Number + ".txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = br.readLine())!=null) {
				token = line.split("\t"); //save에서 탭으로 저장하였으므로 탭단위로 자름
			}
			if(token[0] == null) {
				JOptionPane.showMessageDialog(null, "파일이 없습니다! 세이브 해주세요.", "ERROR!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			for(int i = 0; i < Number ; i++) {
				for(int j = 0; j < Number;j++) {
					puzzleNum[i][j] = Integer.parseInt(token[count]); //이를 배열에 넣음
					count+=1;
				}
			}
			TimerCount = Integer.parseInt(token[Number*Number]); //초를 넣음
			
			
		} catch (IOException e) {
			
			return;
		}
	
	}
	
	public static int[][] setPuzNum(){
		return puzzleNum;
	}
	public static int setTimerCount() {
		return TimerCount;
	}
}
