package com.zjmzxfzhl.common.core.base;

import com.zjmzxfzhl.common.core.Result;

public interface UserInfoService {
    Result<UserInfo> info(String userId, String inner);
}
