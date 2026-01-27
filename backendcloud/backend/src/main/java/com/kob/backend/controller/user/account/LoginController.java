package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;


    // 登录一般用POST请求
    @PostMapping("/user/account/token/") // 这个url要公开化
    public Map<String, String> getToken(@RequestBody Map<String, String> map) { // 把POST请求里的参数放到map里面
        // 取出用户名和密码
        String username = map.get("username");
        String password = map.get("password");
        // 调接口
        return loginService.getToken(username, password);
    }
}
