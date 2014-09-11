package org.androidpn.server.model;


import java.util.ArrayList;
import java.util.List;

public class Page<T> {

    public static final String KEY = "page";

    private static final long TOTAL_COUNT_UNKNOW = -1L;

    protected int pageNo = 1;//
    protected int pageSize = -1;//
    protected boolean autoCount = true;
    protected List<String> orderList = new ArrayList<String>();
    protected List<String> ascOrderList = new ArrayList<String>();
    protected List<String> descOrderList = new ArrayList<String>();

    protected List<T> results = new ArrayList<T>();//
    protected long totalCount = -1L;//
    
    private int startIndex;
    private String htmlPage;
    

    

    public int getStartIndex() {
    	this.startIndex = (this.pageNo-1) * this.pageSize;
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = (this.pageNo-1) * this.pageSize;
	}

	public Page() {
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public Page<T> setPageNo(int pageNo) {
        this.pageNo = pageNo;
        if (pageNo < 1) {
            this.pageNo = 1;
        }
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Page<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getFirst() {
        return ((pageNo - 1) * pageSize) + 1;
    }

    public boolean isAutoCount() {
        return autoCount && totalCount == TOTAL_COUNT_UNKNOW;
    }

    public Page<T> setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
        return this;
    }

    public List<T> getResults() {
        return results;
    }

    public Page<T> setResults(List<T> results) {
        this.results = results;
        return this;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public Page<T> setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    
    
    public long getTotalPages() {
        if (totalCount < 0) {
            return -1L;
        }

        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    public boolean hasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    public int getNextPage() {
        if (hasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    public boolean hasPre() {
        return (pageNo - 1 >= 1);
    }

    public int getPrePage() {
        if (hasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }

    //
    public List<String> getOrderList() {
        return orderList;
    }

    public List<String> getAscOrderList() {
        return ascOrderList;
    }

    public List<String> getDescOrderList() {
        return descOrderList;
    }

    public Page<T> asc(String column) {
        orderList.add(column + " ASC");
        ascOrderList.add(column);
        return this;
    }

    public Page<T> desc(String column) {
        orderList.add(column + " DESC");
        descOrderList.add(column);
        return this;
    }

    public String getHtmlPage(){
    	return this.htmlPage;
    }
    public void setHtmlPage(Page<T> page,String url){
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append("<!-- 分页begin -->\r\n");
    	stringBuilder.append("<div class=\"page\">\r\n");
    	/*stringBuilder.append("			<a href='"+url+"currentPage=1'>首页</a>\r\n");*/
    	if(!url.endsWith("?")){
    		url = url + "&";
    	}
    	if (pageNo > 1)
        {
    		stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-1)+"'>《上一页</a>\r\n");
        }
    	
    	if(page.getTotalPages()<=5&&page.getTotalPages()>1){
    		
    		for(int i = 1 ;i<=page.getTotalPages();i++){
    			if(page.pageNo == i){
    				stringBuilder.append("			<a href=\""+url+"currentPage="+i+"\" class=\"v\">"+i+"</a>\r\n");
    			}else{
    				stringBuilder.append("			<a href='"+url+"currentPage="+i+"'>"+i+"</a>\r\n");
    			}
    		}
    	}else if(page.getTotalPages()>5){
    		if((page.pageNo < (page.getTotalPages()-2))&&(page.pageNo >3)){
    			stringBuilder.append("			..");
    			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-2)+"'>"+(page.pageNo-2)+"</a>\r\n");
    			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-1)+"'>"+(page.pageNo-1)+"</a>\r\n");
    			stringBuilder.append("			<a href=\""+url+"currentPage="+(page.pageNo)+"\" class=\"v\">"+(page.pageNo)+"</a>\r\n");
    			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+1)+"'>"+(page.pageNo+1)+"</a>\r\n");
    			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+2)+"'>"+(page.pageNo+2)+"</a>\r\n");
    			stringBuilder.append("			..");
    		}else if(page.pageNo <=3){
    			if(page.pageNo == 1){
    				stringBuilder.append("			<a href=\""+url+"currentPage="+(page.pageNo)+"\" class=\"v\">"+(page.pageNo)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+1)+"'>"+(page.pageNo+1)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+2)+"'>"+(page.pageNo+2)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+3)+"'>"+(page.pageNo+3)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+4)+"'>"+(page.pageNo+4)+"</a>\r\n");
        			stringBuilder.append("			..");
    			}else if(page.pageNo == 2){
    				stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-1)+"'>"+(page.pageNo-1)+"</a>\r\n");
    				stringBuilder.append("			<a href=\""+url+"currentPage="+(page.pageNo)+"\" class=\"v\">"+(page.pageNo)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+1)+"'>"+(page.pageNo+1)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+2)+"'>"+(page.pageNo+2)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+3)+"'>"+(page.pageNo+3)+"</a>\r\n");
        			stringBuilder.append("			..");
    			}else if(page.pageNo == 3){
    				stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-2)+"'>"+(page.pageNo-2)+"</a>\r\n");
    				stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-1)+"'>"+(page.pageNo-1)+"</a>\r\n");
    				stringBuilder.append("			<a href=\""+url+"currentPage="+(page.pageNo)+"\" class=\"v\">"+(page.pageNo)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+1)+"'>"+(page.pageNo+1)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+2)+"'>"+(page.pageNo+2)+"</a>\r\n");
        			stringBuilder.append("			..");
    			}
    		}else{
    			if(page.pageNo == (page.getTotalPages()-1)){
    				stringBuilder.append("			..");
    				stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-3)+"'>"+(page.pageNo-3)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-2)+"'>"+(page.pageNo-2)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-1)+"'>"+(page.pageNo-1)+"</a>\r\n");
        			stringBuilder.append("			<a href=\""+url+"currentPage="+(page.pageNo)+"\" class=\"v\">"+(page.pageNo)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+1)+"'>"+(page.pageNo+1)+"</a>\r\n");
    			}else if(page.pageNo == (page.getTotalPages())){
    				stringBuilder.append("			..");
    				stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-4)+"'>"+(page.pageNo-4)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-3)+"'>"+(page.pageNo-3)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-2)+"'>"+(page.pageNo-2)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-1)+"'>"+(page.pageNo-1)+"</a>\r\n");
        			stringBuilder.append("			<a href=\""+url+"currentPage="+(page.pageNo)+"\" class=\"v\">"+(page.pageNo)+"</a>\r\n");
    			}else if(page.pageNo == (page.getTotalPages()-2)){
    				stringBuilder.append("			..");
    				stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-2)+"'>"+(page.pageNo-2)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo-1)+"'>"+(page.pageNo-1)+"</a>\r\n");
        			stringBuilder.append("			<a href=\""+url+"currentPage="+(page.pageNo)+"\" class=\"v\">"+(page.pageNo)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+1)+"'>"+(page.pageNo+1)+"</a>\r\n");
        			stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+2)+"'>"+(page.pageNo+2)+"</a>\r\n");
        			
    			}
    		}
    	}else{
    		stringBuilder.append("");    		
    	}
    	if (pageNo < page.getTotalPages())
        {
        	stringBuilder.append("			<a href='"+url+"currentPage="+(page.pageNo+1)+"'>下一页》</a>&nbsp;&nbsp;\r\n");
        }
    	if (page.getTotalPages()>5) {
    		/*stringBuilder.append("			<a href='"+url+"currentPage="+page.getTotalPages()+"'>末页</a>&nbsp;&nbsp;\r\n");*/
        	stringBuilder.append("			共"+page.getTotalPages()+"页&nbsp;&nbsp;&nbsp;&nbsp;\r\n");
            String formUrl=url.substring(0, url.indexOf("?"));
            stringBuilder.append("			<form action='"+formUrl+"'  method=\"post\" style=\"display: inline;\">\r\n");
            if(!url.endsWith("?")){
            	for(int j = 0; j< url.substring(url.indexOf("?")).split("&").length; j++){
            		stringBuilder.append("		<input type=\"hidden\" name=\""+url.substring(url.indexOf("?")+1).split("&")[j].split("=")[0]+"\" value=\""+url.substring(url.indexOf("?")).split("&")[j].split("=")[1]+"\" />\r\n");
            	}
            }
            stringBuilder.append("				去<input type=\"text\" name=\"currentPage\" class=\"page-num\" />页\r\n"
            		+ "　<input type=\"submit\" value=\"确定\" style=\"display: inline;\" class=\"btnInput\" />\r\n");
		}
        stringBuilder.append("			</form>\r\n");
        stringBuilder.append(" </div>\r\n");
        stringBuilder.append("<!-- 分页end -->");
        this.htmlPage = stringBuilder.toString();
    }
}
