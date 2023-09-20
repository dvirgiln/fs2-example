import scala.annotation.tailrec

object BasicScalaApp extends App {

  //Scala Basics: Collections + Pattern Matching

  //Fold Left vs Fold Right vs Fold https://medium.com/@juntomioka/why-foldright-is-beautiful-7854ede3e133

  val scalaList = scala.collection.immutable.List(1, 2, 3)

  //def foldLeft[B](z: B)(op: (B, A) => B): B
  println(scalaList.foldLeft(0){case (acumulated, item) => item + acumulated})
  println(scalaList.foldLeft(0)(_ + _))

  //def foldRight[B](z: B)(op: (A, B) => B): B
  println(scalaList.foldRight(0){case (item, acumulated) => item + acumulated})

  //def reduceLeft[B >: A](op: (B, A) => B): B
  println(scalaList.reduceLeft(_+_)) //Deprecated, use reduceLeftOption
  println(scalaList.reduceLeftOption(_+_))

  //def reduceRight[B >: A](op: (A, B) => B): B
  println(scalaList.reduceRight(_+_)) //Deprecated, use reduceRightOption
  println(scalaList.reduceRightOption(_+_))

  val customList = List(1,2,3)
  println(customList)
  println(customList.map(_ * 2))
  println(customList.mapUsingFoldLeft(_ * 2))
  println(customList.mapUsingFoldLeftFixed(_ * 2))
  println(customList.reduceRight(_ +_))

}

sealed trait List[+A] {

  final def foldLeft[B](z: B)(f: (B, A) => B): B = this match {
    case Nil => z
    case Cons(x, xs) => xs.foldLeft(f(z,x))(f)
  }


  final def foldRight[B](z: B)(f: (A, B) => B): B = this match {
    case Nil => z
    case Cons(x, xs) => f(x, xs.foldRight(z)(f))
  }

  def map[B](f: A => B): List[B] = this.foldRight(Nil: List[B])((a, acum) => Cons(f(a), acum))

  def mapUsingFoldLeft[B](f: A => B): List[B] = this.foldLeft(Nil: List[B])((acum, a) => Cons(f(a), acum))

  def mapUsingFoldLeftFixed[B](f: A => B): List[B] = this.foldLeft(Nil: List[B])((acum, a)=> acum :+ f(a))

  def :+[B >: A](a: B): List[B] = foldRight(List(a))((a, acum) => Cons(a, acum))

  def reduceRight[B >: A](f: (A, B) => B): B = ???

  def reduceLeft[B >: A](f: (B, A) => B): B = ???
}
case object Nil extends List[Nothing]
//Cons(1,Cons(2,Cons(3,Nil)))
case class Cons[A](head: A, tail: List[A]) extends List[A]

object List {
  def apply[A](as: A*): List[A] = as.foldRight(Nil:List[A])((a, acum) => Cons(a, acum))
}


