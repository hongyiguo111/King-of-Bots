package com.kob.backend.service.impl.user.account;

import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.account.LoginService;
import com.kob.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.net.Authenticator;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password) {
        // 把用户名和密码封装成一个类，里面存的是加密之后的字符串
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Map<String, String> map = new HashMap<>();

        try {
            // 验证是否能登录
            Authentication authenticate = authenticationManager.authenticate(authenticationToken); // 如果登录失败会自动处理

            // 取出用户
            UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
            User user = loginUser.getUser();

            // 将用户id封装成jwt
            String jwt = JwtUtil.createJWT(user.getId().toString());

            // 返回结果
            map.put("error_message", "success");
            map.put("token", jwt);

            return map;
        } catch (Exception e) {
            map.put("error_message", String.valueOf(e));
            return map;
        }
    }
}
