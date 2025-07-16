package bronya.admin.base.autoconfig;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

@JacksonStdImpl
public class MyBigDecimalToStringSerializer extends ToStringSerializer {
    public final static MyBigDecimalToStringSerializer instance = new MyBigDecimalToStringSerializer();

    public MyBigDecimalToStringSerializer() {
        super(Object.class);
    }

    public MyBigDecimalToStringSerializer(Class<?> handledType) {
        super(handledType);
    }

    @Override
    public boolean isEmpty(SerializerProvider prov, Object value) {
        if (value == null) {
            return true;
        }
        String str = ((BigDecimal)value).stripTrailingZeros().toPlainString();
        return str.isEmpty();
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(((BigDecimal)value).stripTrailingZeros().toPlainString());
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return createSchemaNode("string", true);
    }

    @Override
    public void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer)
        throws IOException {
        // no type info, just regular serialization
        serialize(value, gen, provider);
    }
}