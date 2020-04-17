package com.lcc.algorithm.sort;

import java.util.Random;

public abstract class AbstractSort {


	int[] sortAndPrintTime(int bound) {
		//int[] randomNumList = new int[]{20, 5, -100, -99, -88, 0, 66};
		int[] randomNumList = new int[bound];
		Random random = new Random();
		for (int i = 0; i < bound; i++) {
			randomNumList[i] = random.nextInt(bound);
		}

		long startTime = System.currentTimeMillis();
		sort(randomNumList);
		long endTime = System.currentTimeMillis();
		System.out.println("endTime-startTime: " + (endTime - startTime));
		return randomNumList;
	}

	abstract void sort(int[] randomNumList);

}
