package interview;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 算法
 */
public class Algorithm {

    /**
     * 最长公共前缀
     * <p>
     * 输入: ["flower","flow","flight"]
     * 输出: "fl"
     */
    static class Solution1 {
        public static String replaceSpace(String[] strs) {

            // 如果检查值不合法及就返回空串
            if (!checkStrs(strs)) {
                return "";
            }
            // 数组长度
            int len = strs.length;
            // 用于保存结果
            StringBuilder res = new StringBuilder();
            // 给字符串数组的元素按照升序排序(包含数字的话，数字会排在前面)
            Arrays.sort(strs);
            int m = strs[0].length();
            int n = strs[len - 1].length();
            int num = Math.min(m, n);
            for (int i = 0; i < num; i++) {
                if (strs[0].charAt(i) == strs[len - 1].charAt(i)) {
                    res.append(strs[0].charAt(i));
                } else {
                    break;
                }

            }
            return res.toString();

        }

        private static boolean checkStrs(String[] strs) {
            boolean flag = false;
            if (strs != null) {
                // 遍历strs检查元素值
                for (int i = 0; i < strs.length; i++) {
                    if (strs[i] != null && strs[i].length() != 0) {
                        flag = true;
                    } else {
                        flag = false;
                        break;
                    }
                }
            }
            return flag;
        }

        // 测试
        public static void main(String[] args) {
            String[] strs = {"bcustomer", "bcar", "bcat"};
            // String[] strs = { "customer", "car", null };//空串
            // String[] strs = {};//空串
            // String[] strs = null;//空串
            System.out.println(Solution1.replaceSpace(strs));// c
        }
    }

    /**
     * 最长回文串
     * 输入:
     * "abccccdd"
     * <p>
     * 输出:
     * 7
     * <p>
     * 解释:
     * 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
     */
    static class Solution2 {
        public int longestPalindrome(String s) {
            if (s.length() == 0)
                return 0;
            // 用于存放字符
            HashSet<Character> hashset = new HashSet<Character>();
            char[] chars = s.toCharArray();
            int count = 0;
            for (int i = 0; i < chars.length; i++) {
                if (!hashset.contains(chars[i])) {// 如果hashset没有该字符就保存进去
                    hashset.add(chars[i]);
                } else {// 如果有,就让count++（说明找到了一个成对的字符），然后把该字符移除
                    hashset.remove(chars[i]);
                    count++;
                }
            }
            return hashset.isEmpty() ? count * 2 : count * 2 + 1;
        }
    }

    /**
     * 验证回文串
     * <p>
     * 输入: "A man, a plan, a canal: Panama"
     * 输出: true
     */
    static class Solution3 {
        public boolean isPalindrome(String s) {
            if (s.length() == 0)
                return true;
            int l = 0, r = s.length() - 1;
            while (l < r) {
                // 从头和尾开始向中间遍历
                if (!Character.isLetterOrDigit(s.charAt(l))) {// 字符不是字母和数字的情况
                    l++;
                } else if (!Character.isLetterOrDigit(s.charAt(r))) {// 字符不是字母和数字的情况
                    r--;
                } else {
                    // 判断二者是否相等
                    if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r)))
                        return false;
                    l++;
                    r--;
                }
            }
            return true;
        }
    }

    static class Solution4 {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            int cnt = 0, max = 0, i;
            for (i = 0; i < s.length(); ++i) {
                if (s.charAt(i) == '(') {
                    cnt++;
                } else {
                    cnt--;
                }
                max = Math.max(max, cnt);
            }
            sc.close();
            System.out.println(max);
        }
    }

    /**
     * 把字符串转换成整数
     */
    static class Solution5 {
        public static int StrToInt(String str) {
            if (str.length() == 0)
                return 0;
            char[] chars = str.toCharArray();
            // 判断是否存在符号位
            int flag = 0;
            if (chars[0] == '+') {
                flag = 1;
            } else if (chars[0] == '-') {
                flag = 2;
            }
            int start = flag > 0 ? 1 : 0;
            int res = 0;// 保存结果
            for (int i = start; i < chars.length; i++) {
                if (Character.isDigit(chars[i])) {// 调用Character.isDigit(char)方法判断是否是数字，是返回True，否则False
                    int temp = chars[i] - '0';
                    res = res * 10 + temp;
                } else {
                    return 0;
                }
            }
            return flag != 2 ? res : -res;

        }

        public static void main(String[] args) {
            String s = "-12312312";
            System.out.println("使用库函数转换：" + Integer.valueOf(s));
            int res = Solution5.StrToInt(s);
            System.out.println("使用自己写的方法转换：" + res);
        }
    }
}
