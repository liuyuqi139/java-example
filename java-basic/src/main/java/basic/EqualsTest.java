package basic;

import lombok.Data;

/**
 * equalsTest
 */
public class EqualsTest {
    @Data
    private static class CustomTeachingTime {
        private String schoolYear;
        private Long periodId;
        private Integer teachingTime;
    }

    public static void main(String[] args) {
        CustomTeachingTime customTeachingTime1 = new CustomTeachingTime();
        customTeachingTime1.setSchoolYear("20-21");
        customTeachingTime1.setPeriodId(1L);
//        customTeachingTime1.setTeachingTime(2);
        customTeachingTime1.setTeachingTime(3);
        CustomTeachingTime customTeachingTime2 = new CustomTeachingTime();
        customTeachingTime2.setSchoolYear("20-21");
        customTeachingTime2.setPeriodId(1L);
        customTeachingTime2.setTeachingTime(2);
        System.out.println(customTeachingTime1.equals(customTeachingTime2));
    }
}
