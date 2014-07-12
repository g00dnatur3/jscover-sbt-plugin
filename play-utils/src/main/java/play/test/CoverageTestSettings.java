package play.test;

import java.lang.reflect.Method;

import play.GlobalSettings;
import play.api.mvc.Handler;
import play.mvc.Http.RequestHeader;

public class CoverageTestSettings extends GlobalSettings {
	
	public static final String DESTINATION_PATH = invokeUsingReflection("sbt.BuildInfo", "jscoverDestinationPath");
	
	public static final String SOURCE_PATH = invokeUsingReflection("sbt.BuildInfo", "jscoverSourcePath");
	
	public static final String REPORTS_DIR = invokeUsingReflection("sbt.BuildInfo", "jscoverReportsDir");
	
	protected final boolean isJsCoverRoutingEnabled;
	
	public CoverageTestSettings(boolean isJsCoverRoutingEnabled) {
		 this.isJsCoverRoutingEnabled = isJsCoverRoutingEnabled;
	}

    @Override
    public Handler onRouteRequest(RequestHeader req) {
    	String sourcePath = SOURCE_PATH.replace("public", "");    	
    	String destinationPath = DESTINATION_PATH;
    	if (isJsCoverRoutingEnabled && req.method().equals("GET") && req.path().startsWith(sourcePath)) {
    		String file = req.path().replace(sourcePath + "/", "");
    		return controllers.Assets.at("/" + destinationPath, file);
    	}
        return super.onRouteRequest(req);
    }
    
    private static String invokeUsingReflection(String className, String methodName) {
    	try {
	    	Class clazz = Class.forName(className);
	    	Method method = clazz.getMethod(methodName);
	    	Object o = method.invoke(null);
	    	return (String) o;
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
}