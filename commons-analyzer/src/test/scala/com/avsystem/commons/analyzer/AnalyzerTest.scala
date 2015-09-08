package com.avsystem.commons
package analyzer

import org.scalatest.{Assertions, FunSuite}

import scala.reflect.internal.util.BatchSourceFile
import scala.tools.nsc.plugins.Plugin
import scala.tools.nsc.{Global, Settings}

/**
 * Author: ghik
 * Created: 21/08/15.
 */
trait AnalyzerTest { this: Assertions =>
  val settings = new Settings
  settings.usejavacp.value = true
  val compiler = new Global(settings) {
    global =>

    override protected def loadRoughPluginsList(): List[Plugin] =
      new AnalyzerPlugin(global) :: super.loadRoughPluginsList()
  }

  def compile(source: String): Unit = {
    compiler.reporter.reset()
    val run = new compiler.Run
    run.compileSources(List(new BatchSourceFile("test.scala", source)))
  }

  def assertErrors(source: String): Unit = {
    compile(source)
    assert(compiler.reporter.hasErrors)
  }

  def assertNoErrors(source: String): Unit = {
    compile(source)
    assert(!compiler.reporter.hasErrors)
  }
}
