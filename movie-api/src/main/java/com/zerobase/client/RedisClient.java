package com.zerobase.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zerobase.domain.MovieDetailDto;
import com.zerobase.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.zerobase.exception.ErrorCode.CANT_PUT_TO_REDIS;
import static com.zerobase.exception.ErrorCode.NOT_FOUND_FROM_REDIS;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisClient {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String movieCd, Class<T> classType) {
        String redisValue = (String) redisTemplate.opsForValue().get(movieCd);
        if (ObjectUtils.isEmpty(redisValue)) {
            return null;
        } else {
            try {
                mapper.registerModule(new JavaTimeModule());
                return mapper.disable(WRITE_DATES_AS_TIMESTAMPS)
                        .readValue(redisValue, classType);
            } catch (JsonProcessingException e) {
                log.error("Parsing error", e);
                throw new CustomException(NOT_FOUND_FROM_REDIS);
            }
        }
    }

    public void put(String movieCd, MovieDetailDto movieDetailDto) {
        try {
            mapper.registerModule(new JavaTimeModule());
            redisTemplate.
                    opsForValue().set(movieCd,
                            mapper.disable(WRITE_DATES_AS_TIMESTAMPS)
                                    .writeValueAsString(movieDetailDto)
                    );
        } catch (JsonProcessingException e) {
            log.error("Parsing error", e);
            throw new CustomException(CANT_PUT_TO_REDIS);
        }
    }

    public void delete(String movieCd) {
        redisTemplate.delete(movieCd);
    }
}
