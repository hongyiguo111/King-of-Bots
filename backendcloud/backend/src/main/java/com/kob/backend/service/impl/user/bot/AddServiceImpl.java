package com.kob.backend.service.impl.user.bot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddServiceImpl implements AddService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        // 从安全上下文中获取当前认证信息
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        // 获取用户详情对象
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        // 获取当前登录用户实体
        User user = loginUser.getUser();

        // 取出需要加入的信息
        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        // 定义返回结果
        Map<String,String> map = new HashMap<>();

        // title
        if (title == null || title == "") {
            map.put("error_message", "标题不能为空");
            return map;
        }
        if(title.length() > 100) {
            map.put("error_message", "标题长度不能大于100");
            return map;
        }

        // description
        if (description == null || description == "") {
            description = "这个用户很懒，什么也没留下";
        }
        if(description.length() > 300) {
            map.put("error_message", "Bot描述文字的长度不能大于300");
            return map;
        }

        // content
        if(content == null || content == "") {
            map.put("error_message", "代码不能为空");
            return map;
        }
        if(content.length() > 10000) {
            map.put("error_message", "代码长度不能大于10000");
            return map;
        }

        QueryWrapper<Bot> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", user.getId());
        if(botMapper.selectCount(queryWrapper) >= 10) {
            map.put("error_message", "每个用户最多只能创建10个Bot!");
            return map;
        }

        // 合法后就创建bot
        Date now = new Date();
        Bot bot = new Bot(null, user.getId(), title, description, content, now, now);

        botMapper.insert(bot);
        map.put("error_message", "success");

        return map;
    }
}
