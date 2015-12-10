package com.liyu.bookmarksys.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liyu.bookmarksys.vo.BookMarkVo;

public class JsonUtil {
	
	public static List jsonToList(String path,BookMarkVo bookMarkVo){
		Scanner scanner;
		try {
			scanner = new Scanner(new File(path),"utf-8");
			
			StringBuffer jsonStr = new StringBuffer();
			while (scanner.hasNextLine()) {
				jsonStr.append(scanner.nextLine());
            }
			Gson gson = new Gson();
			List list = gson.fromJson(jsonStr.toString(), new TypeToken<List>(){}.getType());  
			/*for (Object entity : list) {
				System.out.println(((BookMark) entity).getCreated());
			}*/
			
			//String json = gson.toJson(entityList);
			
			if (list != null) {
				if(bookMarkVo != null){
					
					//根据title模糊查询
					if(bookMarkVo.getTitle()!=null && !"".equals(bookMarkVo.getTitle().trim())){
						List searchList = new ArrayList();
						for (Object object : list) {
							if(((Map)object).get("title").toString().indexOf(bookMarkVo.getTitle().trim())>=0){
								searchList.add(object);
							}
						}
						list = searchList;
					}
				}
			}
			return list;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	//重新写入json文件
	public static void lsitToFile(List list,String path) throws Exception{
		Gson gson = new Gson();
		String jsonStr = gson.toJson(list);
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
	        out.write(jsonStr);  
	        out.flush();  
	        out.close();
	}
}
