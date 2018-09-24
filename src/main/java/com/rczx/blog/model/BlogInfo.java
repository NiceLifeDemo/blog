package com.rczx.blog.model;

import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * Created by Administrator on 2018/9/23.
 */

public class BlogInfo {



    private String userID;

    private String blogLabel;

    private  String blogName;

    private String blogTitle;

    private String blogDescription;

    private String  blogContent;


    public void init(){
        System.out.println("===========初始化Blog=============");
    }



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBlogLabel() {
        return blogLabel;
    }

    public void setBlogLabel(String blogLabel) {
        this.blogLabel = blogLabel;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    public void setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    @Override
    public String toString() {
        return "BlogInfo{" +
                "userID='" + userID + '\'' +
                ", blogLabel='" + blogLabel + '\'' +
                ", blogName='" + blogName + '\'' +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogDescription='" + blogDescription + '\'' +
                ", blogContent='" + blogContent + '\'' +
                '}';
    }
}
