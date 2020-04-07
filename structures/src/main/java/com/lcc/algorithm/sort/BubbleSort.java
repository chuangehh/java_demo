package com.lcc.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {


	public static void main(String[] args) {
		int[] num = new int[]{20, 5, 7, 88, 66, 55};

		// 1.轮数
		for (int i = 0; i < num.length - 1; i++) {
			// 2.交换
			for (int j = i; j < num.length - 1; j++) {
				if (num[j] > num[j + 1]) {
					int current = num[j];
					num[j] = num[j + 1];
					num[j + 1] = current;
				}
			}
		}

		System.out.println(Arrays.toString(num));
	}

}
