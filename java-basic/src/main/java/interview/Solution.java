package interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 算法解决
 */
public class Solution {
    static class SymmetryNumber {
        static void output(int start, int end) {
            for (int i = start; i < end; i++) {
                if (i < 10 || i % 10 == 0) {
                    continue;
                }
                int reverse = reverse(i);
                if (i == reverse) {
                    System.out.print(i + " ");
                }
            }
        }

        static int reverse(int num) {
            char[] array = String.valueOf(num).toCharArray();
            StringBuilder reverse = new StringBuilder();
            for (int i = array.length - 1; i >= 0; i--) {
                reverse.append(array[i]);
            }
            return Integer.parseInt(reverse.toString());
        }

        public static void main(String[] args) {
            output(1, 100000);
        }
    }

    /**
     * 股票最大收益
     */
    static class StockProfit {
        static int maxProfit(int[] prices) {
            int minPrice = Integer.MAX_VALUE;
            int maxProfit = 0;
            for (int i = 0; i < prices.length; i++) {
                if (prices[i] < minPrice) {
                    minPrice = prices[i];
                }
                if (prices[i] - minPrice > maxProfit) {
                    maxProfit = prices[i] - minPrice;
                }
            }

            return maxProfit;
        }

        public static void main(String[] args) {
            System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        }
    }

    static class Reduplication {
        static List<String> outputReduplication(String str) {
            if (str == null || str.length() < 1) {
                return null;
            }
            List<String> reduplicateList = new ArrayList<>();
            char[] chars = str.toCharArray();
            char prev = 0;
            StringBuilder stringBuilder;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == prev) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(prev).append(chars[i]);
                    for (int j = i + 1; j < chars.length; j++) {
                        if (chars[j] == chars[i]) {
                            stringBuilder.append(chars[j]);
                        } else {
                            reduplicateList.add(stringBuilder.toString());
                            i = j;
                            break;
                        }
                    }
                } else {
                    prev = chars[i];
                }
            }
            return reduplicateList;
        }

        public static void main(String[] args) {
            List<String> list = outputReduplication("晴川历历历历汉阳树，芳草萋萋鹦鹉洲");
            Optional.ofNullable(list).ifPresent(System.out::println);
        }
    }
}
