package com.user.center.config.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.user.center.dto.res.ErrorResBody;
import com.user.center.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * https://blog.csdn.net/y_k_j_c/article/details/145834679
 */
@Slf4j
@Configuration
public class SentinelBlockExceptionHandlerConfig implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws Exception {
        log.info("Web 接口自定义返回");
        log.error(ex.getMessage(), ex);
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        ErrorResBody errorResBody = new ErrorResBody();
        if (ex instanceof FlowException) {
            // Return 429 (Too Many Requests) by default.
            response.setStatus(429);
            errorResBody.setCode("流控规则被触发");
            errorResBody.setMessage("流控规则被触发");
        } else if (ex instanceof DegradeException) {
            errorResBody.setCode("降级规则被触发");
            errorResBody.setMessage("降级规则被触发");
        } else if (ex instanceof AuthorityException) {
            errorResBody.setCode("授权规则被触发");
            errorResBody.setMessage("授权规则被触发");
        } else if (ex instanceof ParamFlowException) {
            errorResBody.setCode("热点规则被触发");
            errorResBody.setMessage("热点规则被触发");
        } else if (ex instanceof SystemBlockException) {
            errorResBody.setCode("系统规则被触发");
            errorResBody.setMessage("系统规则被触发");
        }
        PrintWriter out = response.getWriter();
        out.print(JacksonUtils.objectToJsonCamel(errorResBody));
        out.flush();
        out.close();
    }

}
