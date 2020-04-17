package com.lcc.algorithm.sort;

/**
 * 选择排序
 *
 * @author liangchuanchuan
 */
public class SelectSort extends AbstractSort {

	static int bound = 10 * 10000;

	@Override
	void sort(int[] randomNumList) {
		// 1.轮数
		for (int i = 0; i < randomNumList.length - 1; i++) {
			// 2.交换
			int min = randomNumList[i];
			int swapIndex = 0;
			for (int j = i + 1; j <= randomNumList.length - 1; j++) {
				if (min > randomNumList[j]) {
					min = randomNumList[j];
					swapIndex = j;
				}
			}
			if (swapIndex != 0) {
				randomNumList[swapIndex] = randomNumList[i];
				randomNumList[i] = min;
			}
		}
	}

	public static void main(String[] args) {
		SelectSort selectSort = new SelectSort();
		int[] sortNumber = selectSort.sortAndPrintTime(bound);

//		System.out.println(Arrays.toString(sortNumber));
	}

}
