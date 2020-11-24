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
      System.out.println(Number +" by "+Number+" 통계 입니다.");
      System.out.println("--초기 퍼즐--");
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
      System.out.println("초기 가중치 : "+root.cost);
      queue.offer(root);

      while (!queue.isEmpty()) { // 큐가 빌 때까지
         node current = queue.poll(); // 현재 값을 꺼냄(우선순위 큐이므로 가중치가 가장 작은 부분이 나옴)
         if (current.cost == 0) { // 자리가 다른 숫자가 없으면
            path(current, multiList);
            afterTime = System.nanoTime();
            secDiffTime = (afterTime - beforeTime);
            System.out.println(Number + " by " + Number + " 퍼즐의 ns : " + secDiffTime); // 나노세컨드 단위
            System.out.println();
            return;
         }
         for (int[] dir : direct) { // 위 아래 오른쪽 왼쪽으로 움직임
            newX = current.x + dir[0];
            newY = current.y + dir[1];
            if (isSafe(newX, newY, Number)) {
               node child = new node(current.puzzleNum, current.x, current.y, newX, newY, current.step + 1,
                     current); // 공백이 한칸 이동
               if (current.parent == null
                     || !getString(current.parent.puzzleNum, Number).equals(getString(child.puzzleNum, Number)))
               // 부모 노드가 없거나 부모노드와 아이노드가 같지 않을때
               {
                  child.cost = calCost(child.puzzleNum, end, Number); // 아이노드는 h의 값을 계산한다.
                  queue.offer(child); // 큐에 아이노드 저장
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
   } // 숫자들을 string에 넣는 메소드 ex > "1,2,3 ..." -> 비교

   public static boolean isSafe(int x, int y, int Number) {
      return (x >= 0 && x < Number && y >= 0 && y < Number);
   } // 배열의 행열 안에서 존재하는가?

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
   } // F = G + h 의 h에 해당 -> h는 최종 배열과 다른 숫자의 개수를 세아리는 메소드

   public static void path(node root, ArrayList<int[][]> multiList) { // 최단경로를 찾는 과정을 띄어줌
      if (root == null) { // root의 값이 없으면 반환.
         return;
      }
      path(root.parent, multiList); //부모를 꺼내면서 root까지 다가감 
      multiList.add(root.puzzleNum);

   }

}

class node implements Comparable<node> {

   node parent;
   int[][] puzzleNum;
   int x, y;
   int cost; // F = G + h 의 h에 해당 -> h는 최종 배열과 다른 숫자의 개수를 세아림
   int step; // F = G + h 의 G에 해당 -> Start이후로의 이동 횟수

   @Override
   public int compareTo(node o) {  //숫자가 더 큰게 우선순위가 커짐 (가중치가 작아야 최단경로이기 때문)
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

      int temp = this.puzzleNum[x][y]; //새로운 방향으로 가는 배열을 넣음 
      this.puzzleNum[x][y] = this.puzzleNum[newX][newY];
      this.puzzleNum[newX][newY] = temp;

   }

}