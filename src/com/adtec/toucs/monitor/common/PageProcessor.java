package com.adtec.toucs.monitor.common;

import java.util.*;

/**
 * <p>Title:ҳ���ҳ������ </p>
 * <p>Description: ʵ�ֲ�ѯ����ķ�ҳ������</p>
 * <p>Copyright:Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PageProcessor {
	//ÿҳ��Ŀ��
	private int pageSize;
	//��ҳ��
	private int pageCount;
	//��ǰҳ��
	private int currPage;
	//��Ŀ�б�
	private Vector items;
	//ҳ��Ŀ�б�
	private Vector pageItems;

  /**
   * ���췽��
   */
	public PageProcessor() {
		pageSize=20;
		items=new Vector(10);
		pageItems=new Vector(10);
	}

  /**
   * ������Ŀ
   * @param source Դ��Ŀ
   */
	public void setItems(Collection source){
		//�����Ŀ�б�
		items.clear();
		pageItems.clear();
		//��Դ��Ŀ������䵽��Ŀ�б�
		items.addAll(source);
		//������ҳ��
		pageCount=items.size()/pageSize;
		if(items.size()%pageSize!=0)
			pageCount=pageCount+1;
		currPage=0;
	}

  /**
   * ȡ��ҳ��
   * @return ��ҳ��
   */
	public int getPageCount(){
		return pageCount;
	}

  /**
   * ȡ��ǰҳ��
   * @return ��ǰҳ��
   */
	public int getCurrPage(){
		return currPage;
	}

  /**
   * ȡָ��ҳ��Ŀ
   * @param i ҳ��
   * @return ��ҳ��Ŀ
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
   * ����ÿҳ��Ŀ����
   * @param size ÿҳ��Ŀ����
   */
	public void setPageSize(int size){
		if(size<=0)
			pageSize=10;
		else
			pageSize=size;
		//������ҳ��
		pageCount=items.size()/pageSize;
		if(items.size()%pageSize!=0)
			pageCount=pageCount+1;
		currPage=0;
	}

  /**
   * ȡÿҳ��Ŀ��
   * @return ÿҳ��Ŀ��
   */
	public int getPageSize(){
		return pageSize;
	}

  /**
   * ����ָ��ҳ����Ŀ
   * @param i ҳ��
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