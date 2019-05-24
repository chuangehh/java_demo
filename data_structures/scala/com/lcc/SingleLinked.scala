package com.lcc

object SingleLinked {

  val head = new Hero("", 0)

  /**
    * 按 rank排序
    * 如果存在,报错并返回
    *
    * @param hero
    */
  def addHero(hero: Hero): Unit = {
    var temp = head
    while (temp.next != null) {
      if (temp.next.rank == hero.rank) {
        printf("该排名英雄已经存在...")
        return
      }
      if (temp.next.rank > hero.rank) {
        hero.next = temp.next
        temp.next = hero
        return
      }
      temp = temp.next
    }
    temp.next = hero
  }

  def show(): Unit = {
    var temp = head
    while (temp.next != null) {
      temp = temp.next
      printf("Hero name:%s rank:%d\n", temp.name, temp.rank)
    }
  }

  def selectByRank(rank: Int): Hero = {
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

  def updateByRank(hero: Hero): Unit = {
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
      if (temp.next.rank == rank) {
        temp.next = temp.next.next
        return
      }
      temp = temp.next
    }
  }

  def main(args: Array[String]): Unit = {

    "梁川川\t28\t未婚".split("\t").toList
      .foreach(println(_))
//
//    val singleLinked = SingleLinked
//
//    // 增加 水浒英雄 (如果排名相同打出错误信息)
//    val hero3 = new Hero("武松222222", 3)
//    singleLinked.addHero(hero3)
//    val hero1 = new Hero("宋江", 1)
//    singleLinked.addHero(hero1)
//    val hero4 = new Hero("武松", 4)
//    singleLinked.addHero(hero4)
//    val hero2 = new Hero("李逵", 2)
//    singleLinked.addHero(hero2)
//
//    // 查询 水浒英雄
//    singleLinked.show()
//
//    // 根据排名查询某个 水浒英雄
//    println("-------------根据排名查询某个 水浒英雄")
//    singleLinked.selectByRank(3)
//
//    // 根据排名修改 水浒英雄
//    println("-------------根据排名修改 水浒英雄")
//    singleLinked.updateByRank(new Hero("李逵大黑", 2))
//
//    singleLinked.show()
//
//    // 根据排名删除 水浒英雄
//    println("-------------根据排名删除 水浒英雄")
//    singleLinked.deleteByRank(3)
//    singleLinked.deleteByRank(1)
//    singleLinked.deleteByRank(2)
//    singleLinked.show()
//    println("-------------根据排名删除 水浒英雄\n\n")
//
//
//    singleLinked.addHero(hero1)
//    singleLinked.show()
  }


}


class Hero(var name: String, var rank: Int) {
  var next: Hero = _
}