package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        // 从安全上下文中获取当前认证信息
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        // 获取用户详情对象
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        // 获取当前登录用户实体
        User user = loginUser.getUser();

        int bot_id = Integer.parseInt(data.get("bot_id"));
        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String,String> map = new HashMap<>();

        // title
        if (title == null || title.equals("")) {
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

        Bot bot = botMapper.selectById(bot_id);
        if (bot == null) {
            map.put("error_message", "Bot不存在或已被删除");
            return map;
        }

        if(bot.getUserId() != user.getId()) {
            map.put("error_message", "无权限修改此Bot");
            return map;
        }

        Bot newBot = new Bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getCreatetime(),
                new Date()
        );

        botMapper.updateById(newBot);
        map.put("error_message", "success");

        return map;
    }
}
