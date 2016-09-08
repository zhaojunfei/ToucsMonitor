package com.adtec.toucs.monitor.common;

import java.util.*;

/**
 * <p>Title:页面分页处理类 </p>
 * <p>Description: 实现查询结果的分页处理功能</p>
 * <p>Copyright:Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PageProcessor {
	//每页项目数
	private int pageSize;
	//总页数
	private int pageCount;
	//当前页号
	private int currPage;
	//项目列表
	private Vector items;
	//页项目列表
	private Vector pageItems;

  /**
   * 构造方法
   */
	public PageProcessor() {
		pageSize=20;
		items=new Vector(10);
		pageItems=new Vector(10);
	}

  /**
   * 设置项目
   * @param source 源项目
   */
	public void setItems(Collection source){
		//清空项目列表
		items.clear();
		pageItems.clear();
		//将源项目集合填充到项目列表
		items.addAll(source);
		//计算总页数
		pageCount=items.size()/pageSize;
		if(items.size()%pageSize!=0)
			pageCount=pageCount+1;
		currPage=0;
	}

  /**
   * 取总页数
   * @return 总页数
   */
	public int getPageCount(){
		return pageCount;
	}

  /**
   * 取当前页号
   * @return 当前页号
   */
	public int getCurrPage(){
		return currPage;
	}

  /**
   * 取指定页项目
   * @param i 页号
   * @return 该页项目
   */
	public Vector getPage(int i){
		if(i<1)
			i=1;
		if(i>pageCount)
			i=pageCount;
		if(currPage!=i)
			setPageItem(i);
		return pageItems;
	}

  /**
   * 设置每页项目个数
   * @param size 每页项目个数
   */
	public void setPageSize(int size){
		if(size<=0)
			pageSize=10;
		else
			pageSize=size;
		//计算总页数
		pageCount=items.size()/pageSize;
		if(items.size()%pageSize!=0)
			pageCount=pageCount+1;
		currPage=0;
	}

  /**
   * 取每页项目数
   * @return 每页项目数
   */
	public int getPageSize(){
		return pageSize;
	}

  /**
   * 设置指定页的项目
   * @param i 页号
   */
	private void setPageItem(int i){
		pageItems.clear();
		int start=(i-1)*pageSize;
		int end=i*pageSize;
		int sz=items.size();
		if(start>sz)
			start=sz;
		if(end>sz)
			end=sz;
		for(int idx=start;idx<end;idx++)
			pageItems.add(items.get(idx));
		currPage=pageCount>0?i:0;
	}

	public static void main(String[] args) {
		PageProcessor ps = new PageProcessor();
		Vector items=new Vector();
		for(int i=0;i<21;i++)
			items.add("Hello!"+i);
		ps.setItems(items);
		Debug.println("PageCount:"+ps.getPageCount());
		Debug.println("CurrPage:"+ps.getCurrPage());

		Vector pitem=ps.getPage(-2);
		for(int i=0;i<pitem.size();i++)
			Debug.println(pitem.get(i).toString());
		Debug.println("CurrPage:"+ps.getCurrPage());

		ps.setPageSize(3);

		pitem=ps.getPage(4);
		for(int i=0;i<pitem.size();i++)
			Debug.println(pitem.get(i).toString());
		Debug.println("CurrPage:"+ps.getCurrPage());
	}
}