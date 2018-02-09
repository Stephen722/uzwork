package com.light.utils;

import java.io.File;

/**
 * Path Utils.
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class PathUtils {
	
    public static String getWebClassesPath() {   
        String path = PathUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath(); 
        if(path.startsWith(File.separator)){
        	path = path.substring(1);
        }
        return path;
    }   
  
    public static String getWebInfPath() {   
        String path = getWebClassesPath();   
        if (path.indexOf("WEB-INF") > 0) {   
            path = path.substring(0, path.indexOf("WEB-INF") + 8);   
        } 
        return path;   
    }   
  
    public static String getWebRoot() {   
        String path = getWebClassesPath();   
        if (path.indexOf("WEB-INF") > 0) {   
            path = path.substring(0, path.indexOf("WEB-INF/classes"));   
        }  
        return path;   
    }   

//    public static void main(String[] args) throws Exception {   
//        System.out.println("Web Class Path = " + getWebClassesPath());   
//        System.out.println("WEB-INF Path = " + getWebInfPath());   
//        System.out.println("WebRoot Path = " + getWebRoot());   
//        System.out.println("Get Resource = " + PathUtils.class.getClassLoader().getResource(""));   
//    }   
    
	static {};
}
