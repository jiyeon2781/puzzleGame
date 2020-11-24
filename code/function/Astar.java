package function;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Astar {

   public static final int[][] direct = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
   private static int x;
   private static int y;
   private static PriorityQueue<node> queue;
   static node root;
   private static int newX, newY;
   private static int cost;
   private static long beforeTime, afterTime;
   private static long secDiffTime;

   public static void Astarsolve(int[][] start, int[][] end, ArrayList<int[][]> multiList, int Number) {
      x = 0;
      y = 0; 
      beforeTime = System.nanoTime();
      System.out.println(Number +" by "+Number+" ��� �Դϴ�.");
      System.out.println("--�ʱ� ����--");
      for(int i = 0; i < Number ; i++) {
         for(int j = 0; j < Number; j++) {
            System.out.print(start[i][j] +" ");
         }
         System.out.println();
      }
      for (int i = 0; i < Number; i++) {
         for (int j = 0; j < Number; j++) {
            if (start[i][j] == 0) {
               x = i;
               y = j;
            }
         }
      }
      queue = new PriorityQueue<node>();
      root = new node(start, x, y, x, y, 0, null);

      root.cost = calCost(start, end, Number);
      System.out.println("�ʱ� ����ġ : "+root.cost);
      queue.offer(root);

      while (!queue.isEmpty()) { // ť�� �� ������
         node current = queue.poll(); // ���� ���� ����(�켱���� ť�̹Ƿ� ����ġ�� ���� ���� �κ��� ����)
         if (current.cost == 0) { // �ڸ��� �ٸ� ���ڰ� ������
            path(current, multiList);
            afterTime = System.nanoTime();
            secDiffTime = (afterTime - beforeTime);
            System.out.println(Number + " by " + Number + " ������ ns : " + secDiffTime); // ���뼼���� ����
            System.out.println();
            return;
         }
         for (int[] dir : direct) { // �� �Ʒ� ������ �������� ������
            newX = current.x + dir[0];
            newY = current.y + dir[1];
            if (isSafe(newX, newY, Number)) {
               node child = new node(current.puzzleNum, current.x, current.y, newX, newY, current.step + 1,
                     current); // ������ ��ĭ �̵�
               if (current.parent == null
                     || !getString(current.parent.puzzleNum, Number).equals(getString(child.puzzleNum, Number)))
               // �θ� ��尡 ���ų� �θ���� ���̳�尡 ���� ������
               {
                  child.cost = calCost(child.puzzleNum, end, Number); // ���̳��� h�� ���� ����Ѵ�.
                  queue.offer(child); // ť�� ���̳�� ����
               }
            }

         }
      }

   }

   public static String getString(int[][] puzzleNum, int Number) {
      String result = null;
      for (int i = 0; i < Number; i++) {
         for (int j = 0; j < Number; j++) {
            result += puzzleNum[i][j] + ",";
         }
      }
      return result;
   } // ���ڵ��� string�� �ִ� �޼ҵ� ex > "1,2,3 ..." -> ��

   public static boolean isSafe(int x, int y, int Number) {
      return (x >= 0 && x < Number && y >= 0 && y < Number);
   } // �迭�� �࿭ �ȿ��� �����ϴ°�?

   public static int calCost(int[][] currentNum, int[][] finalNum, int Number) {
      cost = 0;
      for (int i = 0; i < Number; i++) {
         for (int j = 0; j < Number; j++) {
            if (currentNum[i][j] != 0 && currentNum[i][j] != finalNum[i][j]) {
               cost++;
            }
         }
      }
      return cost;
   } // F = G + h �� h�� �ش� -> h�� ���� �迭�� �ٸ� ������ ������ ���Ƹ��� �޼ҵ�

   public static void path(node root, ArrayList<int[][]> multiList) { // �ִܰ�θ� ã�� ������ �����
      if (root == null) { // root�� ���� ������ ��ȯ.
         return;
      }
      path(root.parent, multiList); //�θ� �����鼭 root���� �ٰ��� 
      multiList.add(root.puzzleNum);

   }

}

class node implements Comparable<node> {

   node parent;
   int[][] puzzleNum;
   int x, y;
   int cost; // F = G + h �� h�� �ش� -> h�� ���� �迭�� �ٸ� ������ ������ ���Ƹ�
   int step; // F = G + h �� G�� �ش� -> Start���ķ��� �̵� Ƚ��

   @Override
   public int compareTo(node o) {  //���ڰ� �� ū�� �켱������ Ŀ�� (����ġ�� �۾ƾ� �ִܰ���̱� ����)
      return (this.cost + this.step) - (o.cost + o.step);
   }

   node(int[][] mat, int x, int y, int newX, int newY, int step, node parent) {

      this.parent = parent;
      this.cost = 9999;
      this.step = step;
      this.x = newX;
      this.y = newY;
      this.puzzleNum = new int[mat.length][mat.length];

      for (int i = 0; i < mat.length; i++) {
         this.puzzleNum[i] = mat[i].clone();
      }

      int temp = this.puzzleNum[x][y]; //���ο� �������� ���� �迭�� ���� 
      this.puzzleNum[x][y] = this.puzzleNum[newX][newY];
      this.puzzleNum[newX][newY] = temp;

   }

}