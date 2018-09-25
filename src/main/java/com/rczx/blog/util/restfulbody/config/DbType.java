
package com.rczx.blog.util.restfulbody.config;


public enum DbType {
    MY_SQL("mysql"),
    POSTGRSQL("postgresql"),
    ORACLE("oracle");

    public String code;

    private DbType(String code) {
        this.code = code;
    }
}
