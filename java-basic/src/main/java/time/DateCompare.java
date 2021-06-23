package time;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 时间比较
 */
@Slf4j
public class DateCompare {
    public static void main(String[] args) {
        Date date = new Date();
        log.info("date：{}", date);

        Date addDate = DateUtil.addDateHour(date, -1);
        log.info("addDate：{}", addDate);

        log.info("date.after(addDate)：{}", date.after(addDate));
    }
}
