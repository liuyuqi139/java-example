package feature;

/**
 * 枚举
 */
public enum DayEnumTest {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    enum EnumDemo {
        FIRST{
            @Override
            String getInfo() {
                return "FIRST TIME";
            }
        },
        SECOND{
            @Override
            public String getInfo() {
                return "SECOND TIME";
            }
        };
        abstract String getInfo();
    }
}

//    javap .\Day.class 反编译后
//public final class feature.Day extends java.lang.Enum<Day> {
//        public static final Day MONDAY;
//        public static final Day TUESDAY;
//        public static final Day WEDNESDAY;
//        public static final Day THURSDAY;
//        public static final Day FRIDAY;
//        public static final Day SATURDAY;
//        public static final Day SUNDAY;
//        public static Day[] values();
//        public static Day valueOf(java.lang.String);
//        static {};
//    }
