package current;

import com.chinanetcenter.api.domain.HttpClientResult;
import lombok.extern.slf4j.Slf4j;
import util.HttpClientUtils;
import util.ThreadPoolUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 模拟并发
 */
@Slf4j
public class SimulationCurrentTest {
    public static void main(String[] args) {
        SimulationCurrentTest test = new SimulationCurrentTest();

        ExecutorService executorService = ThreadPoolUtil.getExecutorService();

//        final String url = "http://lol-mall-test.aixuexi.com/lol/remain/live/order/confirm/pay";
        final String url = "http://localhost:8080/lol/remain/live/order/confirm/pay";

        executorService.execute(() -> {
            test.http1(url);
        });
        /*executorService.execute(() -> {
            test.http1(url);
        });*/
        /*executorService.execute(() -> {
            test.http2(url);
        });*/
        /*executorService.execute(() -> {
            test.http2(url);
        });*/

        final String rechargeUrl = "http://lol-mall-test.aixuexi.com/lol/remain/app/live/currency/recharge";
        /*executorService.execute(() -> {
            test.http1Recharge(rechargeUrl);
        });
        executorService.execute(() -> {
            test.http2Recharge(rechargeUrl);
        });
        executorService.execute(() -> {
            test.http1Recharge(rechargeUrl);
        });
        executorService.execute(() -> {
            test.http2Recharge(rechargeUrl);
        });*/

        executorService.shutdown();
    }

    private void http1(String url) {
        Map<String, String> headers = new HashMap<>();
        headers.put("ptaxxzxh5", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzODE2NDIxNjIwOTYwMzc4OTMiLCJidXNpbmVzc1BsYXRmb3JtIjoiYWl4dWV4aV9zdHVkZW50IiwiaXNzIjoicGFzc3BvcnRTZXJ2aWNlIiwiand0X3JlZl90b2tlbl9leHBpcmUiOjE2MTU3MTE0OTk4NDUsImV4cCI6MTYxNTY0MzA5OSwiaWF0IjoxNjE1NjI1MDk5LCJsb2dpblN5c3RlbSI6InB0YXh4enhoNSIsImp0aSI6ImNhNzJhNjY0MTkzYzQ4MDg4OWU3OGIxNjBlNmQ0NzBkIiwic0lkIjoiZDQ0NTA4ZWU4ZDcyNGE3ZTg2OTJkZjlmNGExOTBlYmMifQ.3a6h6UZH7bkM7LDiFysoWnFKNnuKYULFKaiQMvWEejI");
        headers.put("ptaxxzxh5UserId", "2717061");  //18191630900

        String json = "{\n" +
                "    \"channel\": 16,\n" +
                "    \"code\": \"sdafdasf\",\n" +
                "    \"axgStudentId\": \"4405976\",\n" +
                "    \"couponId\": \"\",\n" +
                "    \"courseIds\": [\n" +
                "        117370\n" +
                "    ],\n" +
                "    \"deliveryAddressId\": 714,\n" +
                "    \"payTool\": \"2501\"\n" +
                "}";

        try {
            HttpClientResult result = HttpClientUtils.doPostJson(url, headers, json);
            log.info("http1 result：{}, innerResponse：{}", result, result.getInnerResponse());
        } catch (Exception e) {
            log.error("http1异常", e);
        }
    }

    private void http2(String url) {
        Map<String, String> headers = new HashMap<>();
        headers.put("ptaxxzxh5", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzODcwNzY1MjQxNzQzMDczMzAiLCJidXNpbmVzc1BsYXRmb3JtIjoiYWl4dWV4aV9zdHVkZW50IiwiaXNzIjoicGFzc3BvcnRTZXJ2aWNlIiwiand0X3JlZl90b2tlbl9leHBpcmUiOjE2MTU2OTE5MTU3MjksImV4cCI6MTYxNTYyMzUxNSwiaWF0IjoxNjE1NjA1NTE1LCJsb2dpblN5c3RlbSI6InB0YXh4enhoNSIsImp0aSI6ImI4MDM4NWU0MWZlNTRiYWI4YjAyMGQ5NmE4YTc0MWM3Iiwic0lkIjoiM2UxYTNlYmNlZmIwNDJiMTllOTkyNWY4MzMxZDUxYTcifQ.O10-0h1ypquDbFm5z7vA_hiW_clhTEwOqpPEM3DEHUE");
        headers.put("ptaxxzxh5UserId", "2717953");  //18191630901

        String json = "{\n" +
                "    \"channel\": 16,\n" +
                "    \"code\": \"sdafdasf\",\n" +
                "    \"axgStudentId\": \"4405976\",\n" +
                "    \"couponId\": \"\",\n" +
                "    \"courseIds\": [\n" +
                "        117370\n" +
                "    ],\n" +
                "    \"deliveryAddressId\": 714,\n" +
                "    \"payTool\": \"2501\"\n" +
                "}";

        try {
            HttpClientResult result = HttpClientUtils.doPostJson(url, headers, json);
            log.info("http2 result：{}, innerResponse：{}", result, result.getInnerResponse());
        } catch (Exception e) {
            log.error("http2异常", e);
        }
    }

    private void http1Recharge(String url) {
        Map<String, String> headers = new HashMap<>();
        headers.put("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzODE2NDIxNjIwOTYwMzc4OTMiLCJidXNpbmVzc1BsYXRmb3JtIjoiYWl4dWV4aV9zdHVkZW50IiwiaXNzIjoicGFzc3BvcnRTZXJ2aWNlIiwiand0X3JlZl90b2tlbl9leHBpcmUiOjE2MTU2OTE3Mzk2NDQsImV4cCI6MTYxNTYyMzMzOSwiaWF0IjoxNjE1NjA1MzM5LCJsb2dpblN5c3RlbSI6InB0YXh4enhoNSIsImp0aSI6IjQxOTI3YWUxMTExYjRlNzQ5MDYyZmJkZjIzMmZmYmU1Iiwic0lkIjoiYzg3OTQyNTQ3ZjlhNGI3NmI2OWJhOGFiNDNjOGIwZjcifQ.BA-fa_leDfrKYXkbM02HwpWSsZgzgEAuQxL1yBY8rNI");
        headers.put("userId", "2717061");  //18191630900

        String json = "{\n" +
                "    \"axgStudentId\": \"4405976\",\n" +
                "    \"rechargeAmount\": 100\n" +
                "}";

        try {
            HttpClientResult result = HttpClientUtils.doPostJson(url, headers, json);
            log.info("http1 result：{}, innerResponse：{}", result, result.getInnerResponse());
        } catch (Exception e) {
            log.error("http1异常", e);
        }
    }

    private void http2Recharge(String url) {
        Map<String, String> headers = new HashMap<>();
        headers.put("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzODcwNzY1MjQxNzQzMDczMzAiLCJidXNpbmVzc1BsYXRmb3JtIjoiYWl4dWV4aV9zdHVkZW50IiwiaXNzIjoicGFzc3BvcnRTZXJ2aWNlIiwiand0X3JlZl90b2tlbl9leHBpcmUiOjE2MTU2OTE5MTU3MjksImV4cCI6MTYxNTYyMzUxNSwiaWF0IjoxNjE1NjA1NTE1LCJsb2dpblN5c3RlbSI6InB0YXh4enhoNSIsImp0aSI6ImI4MDM4NWU0MWZlNTRiYWI4YjAyMGQ5NmE4YTc0MWM3Iiwic0lkIjoiM2UxYTNlYmNlZmIwNDJiMTllOTkyNWY4MzMxZDUxYTcifQ.O10-0h1ypquDbFm5z7vA_hiW_clhTEwOqpPEM3DEHUE");
        headers.put("userId", "2717953");  //18191630901

        String json = "{\n" +
                "    \"axgStudentId\": \"857403\",\n" +
                "    \"rechargeAmount\": 100\n" +
                "}";

        try {
            HttpClientResult result = HttpClientUtils.doPostJson(url, headers, json);
            log.info("http2 result：{}, innerResponse：{}", result, result.getInnerResponse());
        } catch (Exception e) {
            log.error("http2异常", e);
        }
    }
}
