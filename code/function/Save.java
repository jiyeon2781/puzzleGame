package function;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Save {  //������ �����Ҷ�

	public static void SaveFile(int Number, int[][] puzzleNum, int timeCount) {

		File file = new File("puzzleSave" + Number + ".txt");
		try {
			if(puzzleNum == null) {
				JOptionPane.showMessageDialog(null, "����ǰ� �ִ� ������ �����ϴ�!", "ERROR!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			BufferedWriter fw = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < Number; i++) {
				for (int j = 0; j < Number; j++) {
					fw.write(puzzleNum[i][j] + "\t"); 
				}
			}
			fw.write(Integer.toString(timeCount));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
