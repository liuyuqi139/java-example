package time;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式化辅助类
 */
@Slf4j
public final class DateUtil {

    public static final String PATTERN_MM_DD = "MM月dd日";
    public static final String PATTERN_MM_EN = "MM月";

    public static final String PATTERN_MM_DD_EN = "MM-dd";

    public static final String PATTERN_YYYY_MM_DD = "yyyy年MM月dd日";

    public static final String PATTERN_YYYY_MM_DD_EN = "yyyy-MM-dd";

    public static final String PATTERN_YYYY_MM_DD_HH_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_YYYYMMDD = "yyyyMMdd";

    public static final String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String PATTERN_M_D_EN = "M-d";

    public static final String PATTERN_M_DD_HH_MM_CH = "M月dd日HH:mm";

    public static final String[] weekWords = {"", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    private DateUtil() {

    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD_HH_SS);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 日期上加小时
     */
    public static Date addDateHour(Date date, Integer hours){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hours);
        return cal.getTime();
    }
}
