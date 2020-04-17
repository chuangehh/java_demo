package com.lcc.algorithm.sort;

/**
 * 冒泡排序
 *
 * @author liangchuanchuan
 */
public class BubbleSort extends AbstractSort {

	static int bound = 10 * 10000;

	@Override
	void sort(int[] randomNumList) {
		// 1.轮数
		for (int i = 0; i < randomNumList.length - 1; i++) {
			// 2.交换
			for (int j = 0; j < randomNumList.length - 1 - i; j++) {
				if (randomNumList[j] > randomNumList[j + 1]) {
					int current = randomNumList[j];
					randomNumList[j] = randomNumList[j + 1];
					randomNumList[j + 1] = current;
				}
			}
		}
	}

	public static void main(String[] args) {
		BubbleSort bubbleSort = new BubbleSort();
		bubbleSort.sortAndPrintTime(bound);
	}

}
