package bronya.admin.base.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.dromara.hutool.core.convert.ConvertUtil;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.math.NumberUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.alibaba.cola.exception.SysException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpMessageConverterUtil {
    public MappingJackson2HttpMessageConverter toStringConverter() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        // 序列换成json时,将所有的long变成string 因为js中得数字类型不能包含所有的java long值
        // simpleModule.addSerializer(BigDecimal.class, MyBigDecimalToStringSerializer.instance);
        simpleModule.addSerializer(BigDecimal.class, new JsonSerializer<>() {
            @Override
            public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
                if (value != null) {
                    gen.writeString(value.stripTrailingZeros().toPlainString());
                }
            }
        });
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Date.class, MyDateSerializer.instance);
        simpleModule.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        simpleModule.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        // 10位数转Date
        simpleModule.addDeserializer(Long.class, new CustomLongDeserializer());
        simpleModule.addDeserializer(Date.class, new CustomDateDeserializer()); // 使用自定义的反序列化器
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return jackson2HttpMessageConverter;
    }

    public static class StringToDateConverter implements Converter<String, Date> {
        @Override
        public Date convert(String source) {
            try {
                // 假设时间戳是秒级时间戳，需要乘以1000转换为毫秒
                long timestamp = Long.parseLong(source) * 1000L;
                return new Date(timestamp);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("无效的时间戳格式");
            }
        }
    }

    public static class CustomLongDeserializer extends JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
            String str = jsonParser.getValueAsString();
            Assert.isTrue(NumberUtil.isLong(str),
                () -> new SysException(STR."字段非预期Long类型:\{jsonParser.getParsingContext()} = \{str}"));
            return ConvertUtil.toLong(str);
        }
    }

    public static class CustomDateDeserializer extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
            String timeStr = jsonParser.getValueAsString();
            if (NumberUtil.isLong(timeStr)) {
                if (timeStr.length() == 10) {
                    // 兼容10位数
                    return new Date(jsonParser.getLongValue() * 1000); // 将10位时间戳转换为毫秒级时间戳
                }
                return new Date(jsonParser.getLongValue());
            }
            // 字符串的,例如前端验证码会传: 2025-02-25T07:06:19.004Z
            return DateUtil.parse("2025-02-25T07:06:19.004Z").toJdkDate();
        }
    }

    @JacksonStdImpl
    public static class MyDateSerializer extends DateTimeSerializerBase<Date> {
        public static final MyDateSerializer instance = new MyDateSerializer();

        public MyDateSerializer() {
            this((Boolean)null, (DateFormat)null);
        }

        public MyDateSerializer(Boolean useTimestamp, DateFormat customFormat) {
            super(Date.class, useTimestamp, customFormat);
        }

        public MyDateSerializer withFormat(Boolean timestamp, DateFormat customFormat) {
            return new MyDateSerializer(timestamp, customFormat);
        }

        protected long _timestamp(Date value) {
            return value == null ? 0L : value.getTime() / 1000;
        }

        public void serialize(Date value, JsonGenerator g, SerializerProvider provider) throws IOException {
            if (this._asTimestamp(provider)) {
                g.writeNumber(this._timestamp(value));
            } else {
                this._serializeAsString(value, g, provider);
            }
        }
    }

}
