package com.rczx.blog.util.restfulbody.message.simple;

import java.io.Serializable;

/** @deprecated */
@Deprecated
public class SimpleException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -2417867819355182508L;
    private Integer code;
    private String debug;

    public SimpleException(String message) {
        super(message);
        this.code = Integer.valueOf(400);
    }

    public SimpleException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public SimpleException(String message, Integer code, String debug) {
        super(message);
        this.code = code;
        this.debug = debug;
    }

    public static long getSerialVersionUID() {
        return -2417867819355182508L;
    }

    public String getDebug() {
        return this.debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
