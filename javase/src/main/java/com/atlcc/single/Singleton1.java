package com.atlcc.single;


/**
 * 饿汉式
 * <p>
 * 1.私有化构造器
 * 2.初始化实例
 * 3.提供公共访问方法
 * 4.如果强调是单例可以添加final修饰
 */
public class Singleton1 {

	public static final Singleton1 INSTANCE = new Singleton1();

	private Singleton1() {
	}

}
