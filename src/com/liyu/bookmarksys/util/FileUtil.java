package com.liyu.bookmarksys.util;



public class FileUtil {
	public static String path(){
		String real = FileUtil.class.getClassLoader().getResource("./bookmarks.json").getPath();
		return real;
	}
	
	public static void main(String[] args) {
		System.out.println(FileUtil.path());
	}
}
