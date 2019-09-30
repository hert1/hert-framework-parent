package com.hert.core.boot.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hert
 * @create 2019/8/27
 * @since 1.0.0
 */
@Component
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class SentinelGlobalHandle implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        WebCallbackManager.setUrlBlockHandler(new UrlBlockHandler() {
            @Override
            public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException e)
                    throws IOException {
                // request 里包含了此次请求所有的信息，可以从其中解析出URL、请求参数等。
                log.info("blocked: " + request.getPathInfo());
                // response 表示响应对象，直接向其中写 fallback 结果即可。
                response.setStatus(555);
                response.getWriter().println("flow limit!");
            }
        });
    }
}
