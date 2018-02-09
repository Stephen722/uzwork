package com.light.utils;

/**
 * Pagination 公共分页类
 * 
 * 根据提供的总记录数，页面大小，当前页码和页面链接信息来构建分页相关信息。
 * 当前页对应在URL中的参数名为“page”。
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-10-24
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class Pagination {
	
	private final String parameter = "page="; 
	private int numOfRecord;
	private int numOfPage;
	private int curPage;
	private String pageLink;
	private String pageInfo;
	
	/**
	 * 构造分页类
	 * 
	 * @param numOfRecord 记录总数
	 * @param pageSize 页面大小
	 * @param parameter 查询参数
	 * @param pageLink 页面链接
	 * @return 
	 */
	public Pagination(int numOfRecord, int pageSize, int currentPage, String pageLink) {
		initialization(numOfRecord, pageSize, currentPage, pageLink);
		buildPagination();
	}

	/**
	 * 根据传入参数初始化和计算出相应数据
	 * 
	 * @param numOfRecord 记录总数
	 * @param pageSize 页面大小
	 * @param curPage 当前页
	 * @param pageLink 页面链接
	 */
	private void initialization(int numOfRecord, int pageSize, int curPage, String pageLink) {
		this.numOfRecord = numOfRecord;
		this.curPage = curPage;
		this.pageLink = pageLink;
		if(numOfRecord % pageSize == 0) {
			numOfPage = numOfRecord / pageSize;
		}
		else {
			numOfPage = numOfRecord / pageSize + 1;
		}
		
		if (numOfPage == 0) {
			numOfPage = 1;
		}
		
		if (curPage < 1) {
			curPage = 1;
		}
		else if (curPage > numOfPage) {
			curPage = numOfPage;
		}
	}

	/**
	 * 构建分页信息
	 */
	private void buildPagination() {
		if(numOfRecord <= 0) {
			pageInfo = "";
			return;
		}
		StringBuffer sb = new StringBuffer();
		if(curPage > 1) {
			sb.append("<span><a href='").append(getPageLink(curPage - 1));
			sb.append("'>上一页</a></span>");
		}else{
			sb.append("<span>上一页</span>");
		}
		
		int begin = 1;
		int end = numOfPage;
		if (curPage <= 4 && numOfPage - curPage > 4) {
			end = numOfPage < 8 ? numOfPage : 7;
		}
		else if (curPage > 4 && numOfPage - curPage > 4) {
			begin = curPage - 3;
			end = curPage + 3;
		}
		else if (curPage > 4 && numOfPage - curPage <= 4) {
			begin = numOfPage < 8 ? 1 : numOfPage - 6;
		}
		if(begin >= 2) {
			sb.append("<a href='").append(getPageLink(1)).append("'>");
			sb.append(1).append("</a>");
			if(curPage > 4 && begin > 2) {
				sb.append("<i>...</i>");
			}
		}
		
		for (int i = begin; i <= end; i++) {
			if (i == curPage) {
				sb.append("<b>").append(i).append("</b>");
			}	
			else {
				sb.append("<a href='").append(getPageLink(i)).append("'>");
				sb.append(i).append("</a>");
			}
		}
		if(numOfPage >= 8 && numOfPage > curPage + 4){
			if(curPage + 4 < numOfPage && numOfPage > end + 1) {
				sb.append("<i>...</i>");
			}
			sb.append("<a href='").append(getPageLink(numOfPage)).append("'>");
			sb.append(numOfPage).append("</a>");
		}
		
		
		if(curPage < numOfPage){
			if(curPage <= numOfPage - 1) {
				curPage = curPage + 1;
			}
			else {
				curPage = numOfPage;
			}
			sb.append("<span><a href='").append(getPageLink(curPage));
			sb.append("'>下一页</a></span>");
		}else{
			sb.append("<span>下一页</span>");
		}
		
		pageInfo = sb.toString();
	}
	
	private String getPageLink(int curPage) {
		String pgLink = "";
		// 如果页面URL中含有page，则判断是否含有多个参数
		int pagePos = pageLink.indexOf(this.parameter);
		if(pagePos > 2) {
			// 参数前后链接信息
			String firstLink = pageLink.substring(0, pagePos);
			String lastLink = pageLink.substring(pagePos);
			// 如果page后面没有其他参数，则直接追加page参数
			if(pagePos > pageLink.lastIndexOf("&")) {
				pgLink = firstLink + parameter + curPage;
			}
			else {
				// 如果page后面还有其他参数，则替换原page的值
				int pos = lastLink.indexOf("&");
				String lastString = lastLink.substring(pos);
				pgLink = firstLink + parameter + curPage + lastString;
			}
		}
		else if(pageLink.indexOf("?") > 0) {
			// 如果URL中没有？,则追加"?page"，否则追加"&page"
			pgLink = this.pageLink + "&" + parameter + curPage;
		}
		else {
			pgLink = this.pageLink + "?" + parameter + curPage;
		}
		return pgLink;
		
	}
	public String getPaginationInfo() {
		return pageInfo;
	}

	public int getNumOfRecord() {
		return numOfRecord;
	}

	public int getCurPage() {
		return curPage;
	}

	public int getNumOfPage() {
		return numOfPage;
	}
	
}
