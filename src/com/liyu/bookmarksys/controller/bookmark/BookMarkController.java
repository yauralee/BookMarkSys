package com.liyu.bookmarksys.controller.bookmark;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyu.bookmarksys.core.controller.BaseController;
import com.liyu.bookmarksys.service.IBookMarkService;
import com.liyu.bookmarksys.util.BookMarkResult;
import com.liyu.bookmarksys.util.Page;
import com.liyu.bookmarksys.vo.BookMarkVo;

/**
 * @ClassName: BookMarkController
 * @Description: TODO
 * @author: Administrator
 * @date: 2015年12月3日 下午11:50:00
 */
@Controller
@RequestMapping("bookMark")
public class BookMarkController extends BaseController{
	@Resource
	IBookMarkService bookService;
	@RequestMapping("getBookMarks")
	@ResponseBody
	public BookMarkResult getBookMarks(BookMarkVo bookMark,Page page){
		BookMarkResult result = bookService.findByPage(page,bookMark);
		return result;
	}
	@RequestMapping("deleteBookMark")
	@ResponseBody
	public BookMarkResult deleteBookMark(BookMarkVo bookMark){
		BookMarkResult result = new BookMarkResult();
		try {
			result = bookService.delete(bookMark);
		} catch (Exception e) {
			result.setData(2);
			result.setMsg("文件写入出错！");
			e.printStackTrace();
		}
		return result;
	}
	@RequestMapping("saveBookMark")
	@ResponseBody
	public BookMarkResult BookMarkResult(BookMarkVo bookMark){
		BookMarkResult result = new BookMarkResult();
		try {
			result = bookService.save(bookMark);
		} catch (Exception e) {
			result.setData(2);
			result.setMsg("文件写入出错！");
			e.printStackTrace();
		}
		return result;
	}
}
