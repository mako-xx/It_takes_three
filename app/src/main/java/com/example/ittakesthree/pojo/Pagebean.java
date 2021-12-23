/**
  * Copyright 2021 bejson.com 
  */
package com.example.ittakesthree.pojo;
import java.util.List;

/**
 * Auto-generated: 2021-12-22 15:30:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Pagebean {

    private int allNum;
    private int allPages;
    private List<Contentlist> contentlist;
    private int currentPage;
    private int maxResult;
    public void setAllNum(int allNum) {
         this.allNum = allNum;
     }
    public int getAllNum() {
         return allNum;
     }

    public void setAllPages(int allPages) {
         this.allPages = allPages;
     }
    public int getAllPages() {
         return allPages;
     }

    public void setContentlist(List<Contentlist> contentlist) {
         this.contentlist = contentlist;
     }
    public List<Contentlist> getContentlist() {
         return contentlist;
     }

    public void setCurrentPage(int currentPage) {
         this.currentPage = currentPage;
     }
    public int getCurrentPage() {
         return currentPage;
     }

    public void setMaxResult(int maxResult) {
         this.maxResult = maxResult;
     }
     public int getMaxResult() {
         return maxResult;
     }

}