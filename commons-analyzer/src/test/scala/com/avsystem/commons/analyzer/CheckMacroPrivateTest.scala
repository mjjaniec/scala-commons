package com.avsystem.commons
package analyzer

import org.scalatest.FunSuite

/**
  * Author: ghik
  * Created: 18/02/16.
  */
class CheckMacroPrivateTest extends FunSuite with AnalyzerTest {
  test("macro private method invoked directly should be rejected") {
    assertErrors(
      """
        |import com.avsystem.commons.analyzer.TestUtils
        |
        |object test {
        |  TestUtils.macroPrivateMethod
        |}
      """.stripMargin
    )
  }

  test("macro private method invoked by macro-generated code should not be rejected") {
    assertNoErrors(
      """
        |import com.avsystem.commons.analyzer.TestUtils
        |
        |object test {
        |  TestUtils.invokeMacroPrivateMethod
        |}
      """.stripMargin
    )
  }

  test("definitions of macro private symbols themselves should not be rejected") {
    assertNoErrors(
      """
        |import com.avsystem.commons.annotation.macroPrivate
        |
        |object test {
        |  @macroPrivate def macroPrivateMethod = { println("whatever"); 5 }
        |  @macroPrivate object macroPrivateObject {
        |    val x = 42
        |  }
        |}
      """.stripMargin
    )
  }
}