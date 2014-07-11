package sbt

import sbt._
import Keys._
import scala.collection.mutable.ArrayBuffer
import java.io.{FileInputStream, FileOutputStream}


object JSCoverPlugin extends Plugin {

    val generateInstrumentedApp = taskKey[Unit]("A new task.")
    
    val jscoverSettings = Seq(
        generateInstrumentedApp := {
        	CoverageUtils.jscoverGenerateInstrumentedApp();
        },
		test in Test <<= (test in Test) map{
          x =>
            CoverageUtils.mergeJsonReports();
		  x
		} dependsOn generateInstrumentedApp,
		testOnly in Test <<= (testOnly in Test) map{
          x =>
            CoverageUtils.mergeJsonReports();
		  x
		} dependsOn generateInstrumentedApp
    )

}