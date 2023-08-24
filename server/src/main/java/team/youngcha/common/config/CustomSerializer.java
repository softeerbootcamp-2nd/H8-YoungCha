package team.youngcha.common.config;

import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.Map;

public class CustomSerializer extends Jackson2JsonRedisSerializer {

    public CustomSerializer(Class<?> key, Class<?> value) {
        super(TypeFactory.defaultInstance()
                .constructMapType(Map.class, key, value));
    }
}
