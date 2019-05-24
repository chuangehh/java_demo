package com.lcc

object DoubleLinked {

  val head = new Hero2("", 0)

  /**
    * 按 rank排序
    * 如果存在,报错并返回
    *
    * @param hero
    */
  def addHero(hero: Hero2): Unit = {
    var temp = head.next
    if (temp == null) {
      head.next = hero
      hero.prev = head
      return
    }

    while (temp != null) {
      if (temp.rank == hero.rank) {
        printf("排名:%d 已经存在\n", hero.rank)
        return
      }
      // 当前节点比 加入节点大
      if (temp.rank > hero.rank) {
        hero.prev = temp.prev
        hero.next = temp

        temp.prev.next = hero
        temp.prev = hero
        return
      }
      temp = temp.next
    }
  }

  def show(): Unit = {
    var temp = head
    while (temp.next != null) {
      temp = temp.next
      printf("Hero name:%s rank:%d\n", temp.name, temp.rank)
    }
  }

  def selectByRank(rank: Int): Hero2 = {
    var temp = head
    while (temp.next != null) {
      temp = temp.next
      if (temp.rank == rank) {
        printf("Hero name:%s rank:%d\n", temp.name, temp.rank)
        return temp
      }
    }

    printf("未找到")
    null
  }

  def updateByRank(hero: Hero2): Unit = {
    var temp = head
    while (temp.next != null) {
      if (temp.next.rank == hero.rank) {
        hero.next = temp.next.next
        temp.next = hero
        return
      }
      temp = temp.next
    }
  }

  def deleteByRank(rank: Int): Unit = {
    var temp = head
    while (temp.next != null) {
      //      if (temp.next.rank == rank) {
      //        temp.next = temp.next.next
      //        return
      //      }
      temp = temp.next
      if (temp.rank == rank) {
        temp.prev.next = temp.next

        if (temp.next != null) {
          temp.next.prev = temp.prev
        }
        return
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val doubleLinked = DoubleLinked

    // 增加 水浒英雄 (如果排名相同打出错误信息)
    val hero3 = new Hero2("武松222222", 3)
    doubleLinked.addHero(hero3)
    val hero1 = new Hero2("宋江", 1)
    doubleLinked.addHero(hero1)
    val hero4 = new Hero2("武松", 4)
    doubleLinked.addHero(hero4)
    val hero2 = new Hero2("李逵", 2)
    doubleLinked.addHero(hero2)

    // 查询 水浒英雄
    doubleLinked.show()

    // 根据排名查询某个 水浒英雄
    println("-------------根据排名查询某个 水浒英雄")
    doubleLinked.selectByRank(3)

    // 根据排名修改 水浒英雄
    println("-------------根据排名修改 水浒英雄")
    doubleLinked.updateByRank(new Hero2("李逵大黑", 2))

    doubleLinked.show()

    // 根据排名删除 水浒英雄
    println("-------------根据排名删除 水浒英雄")
    doubleLinked.deleteByRank(3)
    doubleLinked.deleteByRank(1)
    doubleLinked.deleteByRank(2)
    doubleLinked.show()
    println("-------------根据排名删除 水浒英雄\n\n")


    doubleLinked.addHero(hero1)
    doubleLinked.show()
  }


}


class Hero2(var name: String, var rank: Int) {
  var prev: Hero2 = _
  var next: Hero2 = _
}