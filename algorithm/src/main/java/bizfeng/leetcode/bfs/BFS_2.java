package bizfeng.leetcode.bfs;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * *******************************************排列问题**********************************************
 * <p>
 * BFS的特点：寻找目标值或者最值，不用遍历所有的情况（与DFS不同），每一步都把所有的 可能性都列出来，然后分别对每种可能性进行下一步的选择
 * <p>
 * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。
 * 每个拨轮可以自由旋转：例如把 '9' 变为 '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
 * <p>
 * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
 * <p>
 * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
 * <p>
 * 字符串 target 代表可以解锁的数字，你需要给出解锁需要的最小旋转次数，如果无论如何不能解锁，返回 -1 。
 */
@Slf4j
public class BFS_2 {

    public static void main(String[] args) {
        char c = '0';
        System.out.println(++c);
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        int[] used = new int[array.length];
        LinkedList<Integer> routeLink = new LinkedList<>();
        List<List<Integer>> result = new ArrayList<>();
        dfs_way(array, routeLink, result, used);
        log.info("result:{}", result.size());

        String[] ignore = new String[]{"0201", "0101", "0102", "1212", "2002"};
        int i = transTarWithMinStep("0000", ignore, "0202");
        log.info("最小步数：{}", i);
    }

    private static String up(String data, int n) {
        char[] ch = data.toCharArray();
        if (ch[n] == '9') {
            ch[n] = '0';
        } else {
            ch[n] = ++ch[n];
        }
        return new String(ch);
    }

    private static String down(String data, int n) {
        char[] ch = data.toCharArray();
        if (ch[n] == '0') {
            ch[n] = '9';
        } else {
            ch[n] = --ch[n];
        }
        return new String(ch);
    }

    private static int transTarWithMinStep(String begin, String[] ignore, String target) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(begin);
        visited.add(begin);
        int step = 0;
        while (!queue.isEmpty()) {
            //size要独立出来，否则可能死循环
            int size = queue.size();
            log.info("queue size:{}", size);
            for (int i = 0; i < size; i++) {
                String item = queue.poll();
                visited.add(item);
                if (target.equals(item)) {
                    return step;
                }
                for (int j = 0; j < 4; j++) {
                    String upResult = up(item, j);
                    if (!arrayContains(ignore, item) && !visited.contains(upResult)) {
                        queue.offer(upResult);
                    }
                    String downResult = down(item, j);
                    if (!arrayContains(ignore, item) && !visited.contains(downResult)) {
                        queue.offer(downResult);
                    }
                }
            }
            ++step;
        }
        return -1;
    }

    private static boolean arrayContains(Object[] array, Object tar) {
        for (Object o : array) {
            if (tar.equals(o)) {
                return true;
            }
        }
        return false;
    }


    private static void dfs_way(int[] array, LinkedList<Integer> track, List<List<Integer>> result, int[] used) {
        //结束条件
        if (track.size() == 4) {
            result.add(new ArrayList<>(track));
            return;
        }
        for (int i = 0; i < array.length; i++) {
            Integer item = array[i];
            //排除不合法的数据
            if (used[i] == 1) {
                continue;
            }
            used[i] = 1;
            //做选择
            track.add(item);
            //进入下一层决策树
            dfs_way(array, track, result, used);
            used[i] = 0;
            //取消选择;
            track.removeLast();
        }
    }
}
