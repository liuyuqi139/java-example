package web.controller.flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Flux测试
 */
@RestController
public class FluxControllerTest {
    @GetMapping("/helloFlux")
    public Mono<String> helloFlux() {   // 【改】返回类型为Mono<String>
        return Mono.just("Welcome to reactive world ~").delayElement(Duration.ofMillis(2000));     // 【改】使用Mono.just生成响应式数据
    }

    @GetMapping("/hello")
    public String hello() {
        return "Welcome to world ~";
    }
}
