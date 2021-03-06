package com.avsystem.commons
package jiop

import com.avsystem.commons.jiop.JavaInterop._

/**
  * Author: ghik
  * Created: 01/12/15.
  */
trait Java8CollectionUtils extends JCollectionUtils {

  import Java8CollectionUtils._

  implicit def iteratorOps[A](it: JIterator[A]): iteratorOps[A] = new iteratorOps(it)
  implicit def iterableOps[A](it: JIterable[A]): iterableOps[A] = new iterableOps(it)
  implicit def collectionOps[A](it: JCollection[A]): collectionOps[A] = new collectionOps(it)
  implicit def intCollectionOps(it: JCollection[Int]): intCollectionOps = new intCollectionOps(it)
  implicit def longCollectionOps(it: JCollection[Long]): longCollectionOps = new longCollectionOps(it)
  implicit def doubleCollectionOps(it: JCollection[Double]): doubleCollectionOps = new doubleCollectionOps(it)
  implicit def mapOps[K, V](map: JMap[K, V]): mapOps[K, V] = new mapOps(map)
}

object Java8CollectionUtils {
  class iteratorOps[A](private val it: JIterator[A]) extends AnyVal {
    def forEachRemaining(code: A => Any): Unit =
      it.forEachRemaining(jConsumer(code))
  }

  class iterableOps[A](private val it: JIterable[A]) extends AnyVal {
    def forEach(code: A => Any): Unit =
      it.forEach(jConsumer(code))
  }

  class collectionOps[A](private val coll: JCollection[A]) extends AnyVal {
    def removeIf(pred: A => Boolean): Unit =
      coll.removeIf(jPredicate(pred))

    def scalaStream: ScalaJStream[A] =
      coll.stream.asScala
  }

  class intCollectionOps(private val coll: JCollection[Int]) extends AnyVal {
    def scalaIntStream: ScalaJIntStream =
      coll.stream.asScalaIntStream
  }

  class longCollectionOps(private val coll: JCollection[Long]) extends AnyVal {
    def scalaLongStream: ScalaJLongStream =
      coll.stream.asScalaLongStream
  }

  class doubleCollectionOps(private val coll: JCollection[Double]) extends AnyVal {
    def scalaDoubleStream: ScalaJDoubleStream =
      coll.stream.asScalaDoubleStream
  }

  class mapOps[K, V](private val map: JMap[K, V]) extends AnyVal {
    def compute(key: K, remappingFunction: (K, V) => V): V =
      map.compute(key, jBiFunction(remappingFunction))

    def computeIfAbsent(key: K, mappingFunction: K => V): V =
      map.computeIfAbsent(key, jFunction(mappingFunction))

    def computeIfPresent(key: K, remappingFunction: (K, V) => V): V =
      map.computeIfPresent(key, jBiFunction(remappingFunction))

    def forEach(action: (K, V) => Any): Unit =
      map.forEach(jBiConsumer(action))

    def merge(key: K, value: V, remappingFunction: (V, V) => V): V =
      map.merge(key, value, jBiFunction(remappingFunction))

    def replaceAll(function: (K, V) => V): Unit =
      map.replaceAll(jBiFunction(function))
  }
}
