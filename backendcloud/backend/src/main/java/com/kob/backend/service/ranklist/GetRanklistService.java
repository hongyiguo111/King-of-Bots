package com.kob.backend.service.ranklist;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

public interface GetRanklistService {
    JSONObject getList(Integer page);
}
