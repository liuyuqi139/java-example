package collection;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Java8 中 Stream
 */
@Slf4j
public class StreamTest {
    @Data
    static class SubjectTeachingTime {
        private Long subjectId;
        /**
         * 学科组合下可售卖期次
         */
        private Integer composeSaleTeachingTime;
    }
}

@Slf4j
class ToMapTest {
    public static void main(String[] args) {
        List<StreamTest.SubjectTeachingTime> subjectTeachingTimeList = new ArrayList<>();

        StreamTest.SubjectTeachingTime subjectTeachingTime = new StreamTest.SubjectTeachingTime();
        subjectTeachingTime.setSubjectId(1L);
        subjectTeachingTime.setComposeSaleTeachingTime(1);
        subjectTeachingTimeList.add(subjectTeachingTime);

        subjectTeachingTime = new StreamTest.SubjectTeachingTime();
        subjectTeachingTime.setSubjectId(2L);
        subjectTeachingTime.setComposeSaleTeachingTime(1);
        subjectTeachingTimeList.add(subjectTeachingTime);

        subjectTeachingTime = new StreamTest.SubjectTeachingTime();
        subjectTeachingTime.setSubjectId(1L);
        subjectTeachingTime.setComposeSaleTeachingTime(2);
        subjectTeachingTimeList.add(subjectTeachingTime);

        //重复key报错，Duplicate key StreamTest.SubjectTeachingTime(subjectId=1, composeSaleTeachingTime=2)
//        Map<Long, StreamTest.SubjectTeachingTime> map = subjectTeachingTimeList.stream().collect(Collectors.toMap(StreamTest.SubjectTeachingTime::getSubjectId, Function.identity()));

        Map<Long, StreamTest.SubjectTeachingTime> map = subjectTeachingTimeList.stream().collect(Collectors.toMap(StreamTest.SubjectTeachingTime::getSubjectId, Function.identity(), (k1, k2)->k1));

        //Multiple entries with same key: 1=StreamTest.SubjectTeachingTime(subjectId=1, composeSaleTeachingTime=2) and 1=StreamTest.SubjectTeachingTime(subjectId=1, composeSaleTeachingTime=1). To index multiple values under a key, use Multimaps.index
//        Map<Long, StreamTest.SubjectTeachingTime> map = Maps.uniqueIndex(subjectTeachingTimeList, StreamTest.SubjectTeachingTime::getSubjectId);
        map.forEach((subjectId, subjectTeachingTimeValue) -> {
            log.info("subjectId：{}, subjectTeachingTimeValue：{}", subjectId, subjectTeachingTimeValue);
        });
    }
}