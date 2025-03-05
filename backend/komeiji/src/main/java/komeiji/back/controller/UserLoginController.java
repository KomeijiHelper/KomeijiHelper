package komeiji.back.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import komeiji.back.service.UserLoginService;
import komeiji.back.entity.User;
import org.springframework.web.bind.annotation.*;
import komeiji.back.utils.Result;

import jakarta.annotation.Resource;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserLoginController {
    @Resource
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public Result<User> loginController(@RequestBody User loginuser, HttpServletRequest request, HttpServletResponse response) {

        User user = userLoginService.loginService(loginuser.getUname(), loginuser.getPassword());

        if(user != null){
            HttpSession session = request.getSession();
            session.setAttribute("LoginUser", loginuser.getUname());
            return Result.success(user, "登录成功");
        }
        else{
            return Result.error("400","账号或密码错误");
        }
    }

    @PostMapping("/register")
    public Result<User> registerController(@RequestBody User newUser) {
        User user = userLoginService.registerService(newUser);
        if (user != null) {
            return Result.success(user, "注册成功");
        } else {
            return Result.error("456", "注册失败");
        }
    }

    @GetMapping("/logout")
    public void loginOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        session.removeAttribute("LoginUser");
        session.invalidate();
    }

    @GetMapping("/test")
    public String test() { return "test"; }

    @GetMapping("/getUserName")
    public Result<String> getUserName(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object userName = session.getAttribute("LoginUser");
        User user = userLoginService.getUserByName(userName.toString());
        return user == null
                ? Result.error(401, "User Not Found", response)
                : Result.success(user.getUname(), "成功");
    }
}
