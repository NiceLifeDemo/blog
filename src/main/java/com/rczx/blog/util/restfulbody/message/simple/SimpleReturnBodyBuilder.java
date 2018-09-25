package com.rczx.blog.util.restfulbody.message.simple;


import com.google.common.collect.Lists;
import com.rczx.blog.util.MapFactory;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @deprecated */
@Deprecated
public class SimpleReturnBodyBuilder {
    private Map<String, Object> map = new HashMap();

    public SimpleReturnBodyBuilder() {
        this.map.put("success", Boolean.valueOf(true));
    }

    public SimpleReturnBodyBuilder rows(Object resource) {
        HashMap resourceMap = MapFactory.of("rows", Lists.newArrayList(new Object[]{resource}), "total", Integer.valueOf(1));
        this.map.put("R", resourceMap);
        return this;
    }

    public SimpleReturnBodyBuilder rows(List resourceList) {
        HashMap resourceMap = MapFactory.of("rows", resourceList, "total", Integer.valueOf(resourceList.size()));
        this.map.put("R", resourceMap);
        return this;
    }

    public SimpleReturnBodyBuilder total(int total) {
        if(this.map.get("R") == null) {
            throw new RuntimeException("必须先设置rows");
        } else {
            ((Map)this.map.get("R")).put("total", Integer.valueOf(total));
            return this;
        }
    }

    public SimpleReturnBodyBuilder total(long total) {
        return this.total((int)total);
    }

    public Map<String, Object> build() {
        return this.map;
    }
}
