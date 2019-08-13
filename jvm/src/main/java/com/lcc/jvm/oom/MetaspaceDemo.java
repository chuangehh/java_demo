package com.lcc.jvm.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * java -XX:+PrintFlagsFinal
 * MaxMetaspaceSize = 4294901760
 * <p>
 * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 *
 * @author liangchuanchuan
 */
public class MetaspaceDemo {

	public void test(int num) {
		System.out.println("hello cglib: " + num);
	}

	public static void main(String[] args) {

		try {
			for (int i = 0; ; i++) {
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(MetaspaceDemo.class);
				// 得加这句
				enhancer.setUseCache(false);
				enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> proxy.invokeSuper(obj, args1));

				MetaspaceDemo sample = (MetaspaceDemo) enhancer.create();
				sample.test(i);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}


	}

}
