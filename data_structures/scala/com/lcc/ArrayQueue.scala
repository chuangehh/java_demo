package com.lcc

import scala.io.StdIn

object ArrayQueue {

  def main(args: Array[String]): Unit = {

    val queue = new ArrayQueue(4)

    while (true) {
      println("请输入参数")
      println("add: 添加队列")
      println("get: 获取队列")
      println("head: 获取队列第一个元素")
      println("show: 查看队列全部元素")

      val readline = StdIn.readLine()
      readline match {
        case "add" =>
          println("请输入一个数")
          val value = StdIn.readInt()
          queue.addQueue(value)
        case "get" =>
          val result = queue.getQueue()
          result match {
            case a: Int => printf("%d\n", a)
            case b: Exception => println(b.getMessage)
          }
        case "head" =>
          val result = queue.headQueue()
          result match {
            case a: Int => printf("%d\n", a)
            case b: Exception => println(b.getMessage)
          }
        case "show" =>
          queue.showQueue()
        case "exit" => System.exit(0)
        case _ =>
      }
    }
  }


}


class ArrayQueue(maxSize: Int) {

  private val arr = new Array[Int](maxSize)
  private var head = 0
  private var tail = 0

  def isFull(): Boolean = {
    if (tail == maxSize) {
      println("队列已经满..")
      return true
    }
    false
  }

  def addQueue(value: Int): Unit = {
    if (isFull()) {
      return
    }
    arr(tail) = value
    tail = tail + 1
  }

  def isEmpty(): Boolean = {
    if (head == tail) {
      return true
    }
    false
  }

  def headQueue(): Any = {
    if (isEmpty()) {
      return new Exception("队列为空..")
    }
    arr(head)
  }

  def getQueue(): Any = {
    if (isEmpty()) {
      return new Exception("队列为空..")
    }
    val value = arr(head)
    head = head + 1
    value
  }

  def showQueue(): Unit = {
    if (isEmpty()) {
      println("队列为空..")
    }
    for (i <- head until tail) {
      printf("arr[%d]: %d\n", i, arr(i))
    }


  }


}
