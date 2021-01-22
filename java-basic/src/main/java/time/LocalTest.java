package time;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * LocalDateTime
 */
@Slf4j
public class LocalTest {
    public static void main(String[] args) {
        long expireTime = 15;

        LocalDateTime localDateTime = LocalDateTime.now();
        //往前推多少小时
        log.info("localDateTime：{}", DateUtil.localDateTimeToString(localDateTime.minusHours(expireTime-1)));
        //往前推多少分钟
        log.info("localDateTime：{}", DateUtil.localDateTimeToString(localDateTime.minusMinutes(expireTime-1)));
    }
}