package interview;

/**
 * 树算法
 */
public class Tree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 二叉树深度
     */
    static class Solution1 {
        public int maxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            } else {
                int left_height = maxDepth(root.left);
                int right_height = maxDepth(root.right);
                return java.lang.Math.max(left_height, right_height) + 1;
            }
        }
    }

    static class Solution2 {
        public static int binarySearch(Integer[] srcArray, int des) {
            //定义初始最小、最大索引
            int low = 0;
            int high = srcArray.length - 1;
            //确保不会出现重复查找，越界
            while (low <= high) {
                //计算出中间索引值
                int middle = (high + low)>>>1 ;//防止溢出
                if (des == srcArray[middle]) {
                    return middle;
                    //判断下限
                } else if (des < srcArray[middle]) {
                    high = middle - 1;
                    //判断上限
                } else {
                    low = middle + 1;
                }
            }
            //若没有，则返回-1
            return -1;
        }
    }
}
