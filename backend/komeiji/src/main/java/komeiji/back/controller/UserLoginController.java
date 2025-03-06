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
    public Result<String> loginController(@RequestBody User loginUser, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Boolean loginResult = userLoginService.loginService(loginUser.getUserName(), loginUser.getPassword());

        if(loginResult){
            HttpSession session = request.getSession();
            session.setAttribute("LoginUser", loginUser.getUserName());
            return Result.success(loginUser.getUserName(), "登录成功");
        }
        else{
            return Result.error(402,"账号或密码错误", response);
        }
    }

    @PostMapping("/register")
    public Result<String> registerController(@RequestBody User newUser, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean registerResult = userLoginService.registerService(newUser);
        if (registerResult) {
            HttpSession session = request.getSession();
            session.setAttribute("LoginUser", newUser.getUserName());
            return Result.success(newUser.getUserName(), "注册成功");
        } else {
            return Result.error(456, "注册失败", response);
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
                : Result.success(user.getUserName(), "成功");
    }
}
