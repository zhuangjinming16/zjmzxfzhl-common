package com.zjmzxfzhl.common.remote.feign.fallback;

import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.base.LogInfo;
import com.zjmzxfzhl.common.remote.feign.RemoteLogService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 庄金明
 * @date
 */
@Slf4j
@Component
public class RemoteLogServiceFallbackImpl implements RemoteLogService {

    @Setter
    private Throwable cause;

    /**
     * 保存日志
     *
     * @param logInfo
     * @return Result
     */
    @Override
    public Result save(LogInfo logInfo, String inner) {
        log.error("feign 插入日志失败", cause);
        return null;
    }

}
