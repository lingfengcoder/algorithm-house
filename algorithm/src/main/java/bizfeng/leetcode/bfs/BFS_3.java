package bizfeng.leetcode.bfs;

import bizfeng.leetcode.base.BinaryNode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 在一个 2 x 3 的板上（board）有 5 块砖瓦，用数字 1~5 来表示, 以及一块空缺用 0 来表示。一次 移动 定义为选择 0 与一个相邻的数字（上下左右）进行交换.
 * <p>
 * 最终当板 board 的结果是 [[1,2,3],[4,5,0]] 谜板被解开。
 * <p>
 * 给出一个谜板的初始状态 board ，返回最少可以通过多少次移动解开谜板，如果不能解开谜板，则返回 -1 。
 * <p>
 * #########################################
 * <p>
 * 输入：board = [[1,2,3],[4,0,5]]
 * 输出：1
 * 解释：交换 0 和 5 ，1 步完成
 * <p>
 * ############################################
 * 输入：board = [[1,2,3],[5,4,0]]
 * 输出：-1
 * 解释：没有办法完成谜板
 * <p>
 * #############################################
 * <p>
 * 输入：board = [[4,1,2],[5,0,3]]
 * 输出：5
 * 解释：
 * 最少完成谜板的最少移动次数是 5 ，
 * 一种移动路径:
 * 尚未移动: [[4,1,2],[5,0,3]]
 * 移动 1 次: [[4,1,2],[0,5,3]]
 * 移动 2 次: [[0,1,2],[4,5,3]]
 * 移动 3 次: [[1,0,2],[4,5,3]]
 * 移动 4 次: [[1,2,0],[4,5,3]]
 * 移动 5 次: [[1,2,3],[4,5,0]]
 */
@Slf4j
public class BFS_3 {

    public static void main(String[] args) {
        // int[][] array = new int[3][2];
        int[][] array = transArr("123|540");
        String str = transStr(array);
        log.info("{}", str);
        StepMap play = play(array, "123|450");
        if (play == null) {
            log.info("无解");
            return;
        }
        int count = 0;
        StringBuilder builder = new StringBuilder();
        do {
            builder.insert(0, play.data + "  ");
            play = play.father;
            ++count;
        } while (play.father != null);
        log.info("走法： {}", builder.toString());
        log.info("需要步数:{}", count);

    }


    static
    class StepMap {
        StepMap father;
        String data;
    }

    private static StepMap play(int[][] array, String target) {
        //存放图
        Queue<StepMap> stepLink = new LinkedList<>();
        //存放已经走过的路，防止重复
        Set<String> used = new HashSet<>();
        //设置边界
        int borderX = array.length - 1;
        int borderY = array[0].length - 1;
        //步数
        int step = 0;
        String data = transStr(array);
        StepMap root = new StepMap();
        root.data = data;
        stepLink.add(root);
        while (!stepLink.isEmpty()) {
            int size = stepLink.size();
            for (int i = 0; i < size; i++) {
                StepMap tmp = stepLink.poll();
                if (target.equals(tmp.data)) {
                    return tmp;
                }
                used.add(tmp.data);
                //做选择 上下左右
                for (int k = 0; k < 4; k++) {
                    int[][] tmpArr = transArr(tmp.data);
                    String moveData = move(tmpArr, borderX, borderY, k);
                    if (!used.contains(moveData)) {
                        StepMap child = new StepMap();
                        child.father = tmp;
                        child.data = moveData;
                        stepLink.add(child);
                    }
                }
            }
            log.info("steplink={}", stepLink.size());
            log.info("used size:{}", used.size());
            ++step;
        }
        return null;
    }

    private static int[][] transArr(String str) {
        String[] split = str.split("\\|");
        int y = split.length;
        int x = split[0].toCharArray().length;
        int[][] array = new int[x][y];
        for (int i = 0; i < y; i++) {
            char[] lineY = split[i].toCharArray();
            for (int j = 0; j < lineY.length; j++) {
                array[j][y - i - 1] = lineY[j] - '0';
            }
        }
        return array;
    }

    private static String transStr(int[][] array) {
        StringBuilder builder = new StringBuilder();
        int x = array.length;
        int y = array[0].length;
        for (int i = y - 1; i >= 0; i--) {
            for (int j = 0; j < x; j++) {
                builder.append(array[j][i]);
            }
            if (i != 0)
                builder.append("|");
        }
        return builder.toString();
    }

    /**
     * 模拟棋盘
     * #=-1
     * # # # # #
     * # 1 2 3 #
     * # 4 5 0 #
     * # # # # #
     */

    private static String move(int[][] array, int borderX, int borderY, int cmd) {
        int[] zeroPosition = findZero(array);
        int x = zeroPosition[0];
        int y = zeroPosition[1];

        //小于上边界可以向上移动
        switch (cmd) {
            case 0:
                //上移
                if (y < borderY) {
                    swap(array, x, y, x, y + 1);
                }
                break;
            case 1:
                //下移
                if (y > 0) {
                    swap(array, x, y, x, y - 1);
                }
                break;
            case 2:

                //左移
                if (x > 0) {
                    swap(array, x, y, x - 1, y);
                }
                break;
            case 3:
                //右移
                if (x < borderX) {
                    swap(array, x, y, x + 1, y);
                }
                break;
        }
        return transStr(array);
    }

    //找到0的位置
    private static int[] findZero(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            int[] d = data[i];
            for (int j = 0; j < d.length; j++) {
                if (d[j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }


    /**
     * 模拟棋盘 移动交换位置
     * #=-1
     * # # # # #
     * # 1 2 3 #
     * # 4 5 0 #
     * # # # # #
     */
    private static void swap(int[][] array, int oldX, int oldY, int newX, int newY) {
        int tmp = array[oldX][oldY];
        array[oldX][oldY] = array[newX][newY];
        array[newX][newY] = tmp;
    }

}
