package sbt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jscover.Main;
import jscover.util.IoUtils;

public class CoverageUtils {

	public static void jscoverGenerateInstrumentedApp() {
		File f = new File("public/jscover");
		if (f.exists()) {
			f.delete();
		}
		jscoverGenerateInstrumentedApp("public/apps/advisor", "public/jscover/advisor");
		new File("public/jscover/reports").mkdir();
	}
	
	public static void jscoverGenerateInstrumentedApp(String srcPath, String dstPath) {
		String[] args = new String[]{
	        "-fs",
	        srcPath,
	        dstPath
	    };
		try {
			Main.main(args);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void mergeJsonReports() {
		File reportsDir = new File("public/jscover/reports");
		if (!reportsDir.exists()) {
			throw new RuntimeException("Reports directory does not exist");
		} else {
			if (reportsDir.listFiles().length == 1) {
				File src = new File(reportsDir.listFiles()[0].getAbsolutePath(), "jscoverage.json");
				File dst = new File("public/jscover/advisor", "jscoverage.json");
				IoUtils ioUtils = new IoUtils();
				ioUtils.copy(src, dst);
			} else if (reportsDir.listFiles().length > 1) {
				List<String> args = new ArrayList<String>();
				args.add("--merge");
				for (File file : reportsDir.listFiles()) {
					if (file.isDirectory()) {
						args.add(file.getAbsolutePath());
					}
				}
				//the last arg is the destination
				args.add(new File("public/jscover/advisor").getAbsolutePath());
				try {
					jscover.report.Main.main(args.toArray(new String[args.size()]));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		try(PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter("public/jscover/advisor/jscoverage.js", true)))) {
		    out.println("jscoverage_isReport = true;");
		}catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
