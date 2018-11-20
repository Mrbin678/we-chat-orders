package com.hxkj.wechatorders.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * Date转换为long的自定义工具类
 * Created by wangbin
 * 2018-07-18 15:28
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        //将date转成long,除以1000是不用精确到毫秒数与前端要求的位数相同
        jsonGenerator.writeNumber(date.getTime()/1000);
    }
}
