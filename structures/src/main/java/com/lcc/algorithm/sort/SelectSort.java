package com.lcc.algorithm.sort;

import java.util.Arrays;

/**
 * 选择排序
 */
public class SelectSort {

	public static void main(String[] args) {
		int[] num = new int[]{20, 5, 7, 88, 66, 55};

		// 1.轮数
		for (int i = 0; i < num.length - 1; i++) {
			// 2.交换
			int min = num[i];
			int swapIndex = 0;
			for (int j = i + 1; j <= num.length - 1; j++) {
				if (min > num[j]) {
					min = num[j];
					swapIndex = j;
				}
			}
			if (swapIndex != 0) {
				num[swapIndex] = num[i];
				num[i] = min;
			}
		}

		System.out.println(Arrays.toString(num));
	}
}
