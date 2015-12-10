package com.liyu.bookmarksys.util;

public class FileUtil {
	public static String path(){
		String real = FileUtil.class.getClassLoader().getResource("").getPath();
		return real.substring(1, real.length()).replace("/", "\\\\")+"bookmarks.json";
	}
	
	public static void main(String[] args) {
		System.out.println(FileUtil.path());
	}
}
