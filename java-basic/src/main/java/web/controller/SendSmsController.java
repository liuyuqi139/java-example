package web.controller;

import com.chinanetcenter.api.domain.HttpClientResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import util.HttpClientUtils;
import util.ThreadPoolUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 *
 */
@Slf4j
@RestController
public class SendSmsController {
    @PostMapping("/send/sms")
    public String sendSms(@RequestBody SendSmsParam param) {
        if (CollectionUtils.isEmpty(param.getOrderNoList())) {
            return "订单号为空";
        }
        ExecutorService executorService = ThreadPoolUtil.getExecutorService();
        for (String orderNo : param.getOrderNoList()) {
            executorService.execute(() -> {
                try {
                    Map<String, String> map = new HashMap<>();
                    map.put("orderNo", orderNo);
//                    HttpClientResult result = HttpClientUtils.doPost("https://zx.aixuexi.com/lol/mall/trade/sendSms", map);
//                    log.info("orderNo：{}, result：{}, innerResponse：{}", orderNo, result, result.getInnerResponse());
                } catch (Exception e) {
                    log.error("sendSms异常, orderNo：{}", orderNo, e);
                }
            });
        }
        return "执行成功";
    }

    @Data
    private static class SendSmsParam {
        private List<String> orderNoList;
    }
}
