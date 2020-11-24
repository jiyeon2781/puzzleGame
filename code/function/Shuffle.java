package function;

import java.util.Random;

public class Shuffle { // 판을 섞는 클래스

	private static int shufNum;
	private static int[][] puzzleNum;
	private static int count;
	private static int x, y;
	private static int dir;

	public static int[][] PuzShuffle(int Number) {
		puzzleNum = new int[Number][Number];
		count = 1;
		for (int i = 0; i < Number; i++) {
			for (int j = 0; j < Number; j++) {
				if (count == Number * Number) {
					puzzleNum[i][j] = 0;
				} else {
					puzzleNum[i][j] = count;
					count++;
				}
			}
		}
		Random ran = new Random(); // 최종상태에서 마구잡이로 섞을 랜덤함수
		int temp;
		x = 0;
		y = 0;
		shufNum = 1;
		while (shufNum <= 70) {

			for (int i = 0; i < Number; i++) {
				for (int j = 0; j < Number; j++) {
					if (puzzleNum[i][j] == 0) { // 공백을 찾아 x,y에 대입
						x = i;
						y = j;
					}
				}
			}

			dir = ran.nextInt(4) + 1;
			if (dir == 1) { // 왼쪽
				if (y == 0) {
					continue;
				} else {
					temp = puzzleNum[x][y];
					puzzleNum[x][y] = puzzleNum[x][y - 1];
					puzzleNum[x][y - 1] = temp;
					shufNum++;
				}
			} else if (dir == 2) { // 오른쪽
				if (y == Number - 1) {
					continue;
				} else {
					temp = puzzleNum[x][y];
					puzzleNum[x][y] = puzzleNum[x][y + 1];
					puzzleNum[x][y + 1] = temp;
					shufNum++;
				}
			} else if (dir == 3) { // 아래
				if (x == Number - 1) {
					continue;
				} else {
					temp = puzzleNum[x][y];
					puzzleNum[x][y] = puzzleNum[x + 1][y];
					puzzleNum[x + 1][y] = temp;
					shufNum++;
				}
			} else if (dir == 4) { // 위
				if (x == 0) {
					continue;
				} else {
					temp = puzzleNum[x][y];
					puzzleNum[x][y] = puzzleNum[x - 1][y];
					puzzleNum[x - 1][y] = temp;
					shufNum++;
				}
			}
		}
		return puzzleNum;
	}

}
