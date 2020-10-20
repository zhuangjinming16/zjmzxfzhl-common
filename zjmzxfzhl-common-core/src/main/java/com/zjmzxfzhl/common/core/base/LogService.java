package com.zjmzxfzhl.common.core.base;

import com.zjmzxfzhl.common.core.Result;

public interface LogService {
    Result save(LogInfo logInfo, String inner);
}
