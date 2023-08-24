package team.youngcha.common.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.Map;

public class CustomSerializer extends Jackson2JsonRedisSerializer {
    public CustomSerializer(Class type) {
        super(type);
    }

    @Override
    protected JavaType getJavaType(Class clazz) {
        return TypeFactory.defaultInstance()
                .constructMapType(Map.class, Long.class, Number.class);
    }
}
