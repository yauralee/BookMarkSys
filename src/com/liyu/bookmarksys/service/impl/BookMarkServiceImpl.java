package com.liyu.bookmarksys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.liyu.bookmarksys.service.IBookMarkService;
import com.liyu.bookmarksys.util.BookMarkResult;
import com.liyu.bookmarksys.util.FileUtil;
import com.liyu.bookmarksys.util.JsonUtil;
import com.liyu.bookmarksys.util.Page;
import com.liyu.bookmarksys.vo.BookMarkVo;

@Service("bookService")
public class BookMarkServiceImpl implements IBookMarkService {
	@Override
	public BookMarkResult save(BookMarkVo bookMark) throws Exception {
		BookMarkResult result = new BookMarkResult();
		bookMark.setCreated(System.currentTimeMillis()+"");
		boolean isSave = true;
		List list = JsonUtil.jsonToList(FileUtil.path(), null);
		for (int i = 0; i < list.size(); i++) {
			if (((Map) list.get(i)).get("title").equals(bookMark.getTitle())) {
				isSave = false;
				break;
			}
		}
		if(isSave){
			boolean flag = false;
			flag = list.add(bookMark);
			if(flag){
				result.setStatus(1);
				result.setMsg("保存成功");
				JsonUtil.lsitToFile(list,FileUtil.path());
			}else{
				result.setStatus(0);
				result.setMsg("保存失败");
			}
			
		}else{
			result.setStatus(0);
			result.setMsg(bookMark.getTitle()+"已存在，保存失败");
		}
		return result;
	}
	@Override
	public BookMarkResult delete(BookMarkVo bookMarkVo) throws Exception {
		BookMarkResult result = new BookMarkResult();
		List list = JsonUtil.jsonToList(FileUtil.path(), null);
		Object used = null;
		for (int i = 0; i < list.size(); i++) {
			if (((Map) list.get(i)).get("title").equals(bookMarkVo.getTitle())) {
				used = list.remove(i);
			}
		}
		if (used == null) {
			result.setStatus(0);
			result.setMsg("无法删除，没有此书签");
		}else{
			result.setStatus(1);
			result.setMsg("删除成功");
			//将删除后新的list写入文件
			JsonUtil.lsitToFile(list,FileUtil.path());
		}
		return result;
	}
	@Override
	public BookMarkResult find(BookMarkVo bookMarkVo) {
		BookMarkResult result = new BookMarkResult();
		List list = null;
		if (bookMarkVo.getCreated() == null) {
			list = JsonUtil.jsonToList(FileUtil.path(), bookMarkVo);
		}
		if (list == null) {
			result.setStatus(0);
			result.setMsg("此文件没有数据");
			return result;
		}
		result.setStatus(1);
		result.setMsg("查询成功");
		result.setData(list);
		return result;
	}
	@Override
	public BookMarkResult findByPage(Page page, BookMarkVo bookMarkVo) {
		BookMarkResult result = new BookMarkResult();
		List list = JsonUtil.jsonToList(FileUtil.path(),bookMarkVo);
		page.setTotalRowNum(list.size());
		if (page.getPageNum() * page.getPageSize() > list.size()) {
			list = list.subList((page.getPageNum() - 1) * page.getPageSize(),list.size());
		} else {
			list = list.subList((page.getPageNum() - 1<=0?0:page.getPageNum() - 1) * page.getPageSize(), page.getPageNum() * page.getPageSize());
		}

		result.setStatus(1);
		result.setMsg("查询成功");
		result.setData(list);
		result.setPage(page);
		return result;
	}
}
