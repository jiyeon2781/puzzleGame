package function;

public class Move { //���� ������ �� ���� Ŭ����
	public static void moveDir(int puzzleNum[][],int dir,int puzNum) {
		for(int i = 0; i <puzNum; i++) {
			for(int j = 0; j < puzNum; j++) {
				if(puzzleNum[i][j] == 0) {
					if (dir == 1) { //����
						if (j == 0) {
							return;
						} else {
							int temp = puzzleNum[i][j];
							puzzleNum[i][j] = puzzleNum[i][j-1];
							puzzleNum[i][j-1] = temp;
							return;
						}
					} else if (dir == 2) { //������
						if (j == puzNum-1) {
							return;
						} else {
							int temp = puzzleNum[i][j];
							puzzleNum[i][j] = puzzleNum[i][j + 1];
							puzzleNum[i][j + 1] = temp;
							return;
						}
					} else if (dir == 3) { //�Ʒ�
						if (i == puzNum-1) {
							return;
						} else {
							int temp = puzzleNum[i][j];
							puzzleNum[i][j] = puzzleNum[i + 1][j];
							puzzleNum[i+ 1][j] = temp;
							return;
						}
					} else if (dir == 4) { //��
						if (i == 0) {
							return;
						} else {
							int temp = puzzleNum[i][j];
							puzzleNum[i][j] = puzzleNum[i - 1][j];
							puzzleNum[i - 1][j] = temp;
							return;
						}
					}
				}
			}
		}

	}
}
