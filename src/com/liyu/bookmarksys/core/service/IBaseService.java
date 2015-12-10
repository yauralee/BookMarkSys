package com.liyu.bookmarksys.core.service;


import com.liyu.bookmarksys.util.BookMarkResult;
import com.liyu.bookmarksys.util.Page;

public interface IBaseService<T> {

	public BookMarkResult save(T t) throws Exception;
	public BookMarkResult delete(T t) throws Exception;
	public BookMarkResult find(T t);
	public BookMarkResult findByPage(Page page,T t);
}
