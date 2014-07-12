package sbt

import sbt._
import Keys._
import scala.collection.mutable.ArrayBuffer
import java.io.{FileInputStream, FileOutputStream}


object JSCoverPlugin extends Plugin {
    
	val coverageUtils = new CoverageUtils();
  
    lazy val jscoverSourcePath = SettingKey[String]("jscoverSourcePath", "The javascript source path")
    
    lazy val jscoverDestinationPath = SettingKey[String]("jscoverDestinationPath", "The destination path for instrumented javascript")
    
    lazy val jscoverReportsDir = SettingKey[String]("jscoverReportsDir", "The location of the json coverage reports")
    
    lazy val initCoverageUtils = TaskKey[Unit]("initCoverageUtils")
    
    lazy val generateInstrumentedCode = TaskKey[Unit]("generateInstrumentedCode")
    
    lazy val generateRouteForDestination = TaskKey[Unit]("generateRouteForDestination")
    
    lazy val jscoverSettings = Seq(
        
        jscoverSourcePath := "public/javascripts",
        
        jscoverDestinationPath := "public/jscover/javascripts",
        
        jscoverReportsDir := "public/jscover/reports",
        
        initCoverageUtils <<= initCoverageUtilsTask,
        
        generateRouteForDestination <<= generateRouteForDestinationTask,
        
        compile in Compile <<= (compile in Compile) dependsOn generateRouteForDestination,
        
        generateInstrumentedCode <<= generateInstrumentedCodeTask,

		test in Test <<= (test in Test) map { 
          (x) => 
            coverageUtils.mergeJsonReports()
       	} dependsOn generateInstrumentedCode,
        
		testOnly in Test <<= (testOnly in Test) map { 
          (x) => 
            coverageUtils.mergeJsonReports()
       	} dependsOn generateInstrumentedCode
    )
    
    def initCoverageUtilsTask = (jscoverSourcePath, jscoverDestinationPath, jscoverReportsDir) map { 
        (source, destination, reportsDir) =>
          
        coverageUtils.init(source, destination, reportsDir)
    }
    
    def generateInstrumentedCodeTask = (initCoverageUtils) map { (x) =>
      	coverageUtils.generateInstrumentedCode()
    }

    def generateRouteForDestinationTask = (initCoverageUtils) map { (x) =>
      	coverageUtils.generateRouteForDestination();
    }
    
}