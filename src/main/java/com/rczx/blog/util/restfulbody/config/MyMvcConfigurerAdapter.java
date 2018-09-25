
package com.rczx.blog.util.restfulbody.config;

import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class MyMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MyMvcConfigurerAdapter.class);
    private static ValueFilter dateFilter = new ValueFilter() {
        public Object process(Object object, String name, Object value) {
            if(value instanceof Date) {
                if(!name.endsWith("At") && !name.endsWith("_at")) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    return format.format(value);
                } else {
                    return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format((Date)value);
                }
            } else {
                return value;
            }
        }
    };
    private static SerializeFilter nullToEmptyFilter = new ValueFilter() {
        public Object process(Object object, String name, Object value) {
            return value == null?"":value;
        }
    };

    public MyMvcConfigurerAdapter() {
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.info("--->CustomMvcConfig configureMessageConverters");
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd");
        fastJsonConfig.setSerializeFilters(new SerializeFilter[]{dateFilter, nullToEmptyFilter});
        fastJsonConfig.setSerializerFeatures(new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
        converter.setFastJsonConfig(fastJsonConfig);
        converters.clear();
        converters.add(converter);
    }
}
