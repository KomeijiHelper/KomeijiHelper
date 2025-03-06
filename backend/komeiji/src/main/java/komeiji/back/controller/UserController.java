package komeiji.back.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import komeiji.back.entity.UserClass;
import komeiji.back.service.UserService;
import komeiji.back.entity.User;
import org.springframework.web.bind.annotation.*;
import komeiji.back.utils.Result;

import jakarta.annotation.Resource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<String> loginController(@RequestBody User loginUser, HttpSession session, HttpServletResponse response) throws IOException {

        Boolean loginResult = userService.loginService(loginUser.getUserName(), loginUser.getPassword());

        if(loginResult){
            session.setAttribute("LoginUser", loginUser.getUserName());
            return Result.success(loginUser.getUserName(), "登录成功");
        }
        else{
            return Result.error(402,"账号或密码错误", response);
        }
    }

    @PostMapping("/register")
    public Result<String> registerController(@RequestBody User newUser, HttpSession session, HttpServletResponse response) throws IOException {
        Boolean registerResult = userService.registerService(newUser);
        if (registerResult) {
            session.setAttribute("LoginUser", newUser.getUserName());
            return Result.success(newUser.getUserName(), "注册成功");
        } else {
            return Result.error(456, "注册失败", response);
        }
    }

    @GetMapping("/logout")
    public void loginOut(HttpSession session) {
        session.removeAttribute("LoginUser");
        session.invalidate();
    }

    @GetMapping("/test")
    public String test() { return "test"; }

    @GetMapping("/getUserName")
    public Result<String> getUserName(HttpSession session, HttpServletResponse response) throws IOException {
        Object userName = session.getAttribute("LoginUser");
        User user = userService.getUserByName(userName.toString());
        return user == null
                ? Result.error(401, "User Not Found", response)
                : Result.success(user.getUserName());
    }

    @PostMapping("/getUsersByClass")
    public Result<List<String>> getUsersByClass(@RequestBody UserClassRequest userClassRequest, HttpServletResponse response) throws IOException {
        UserClass userClass = UserClass.fromCode(userClassRequest.userClassCode);
        List<User> users = userService.getUsersByUserClass(userClass);
        List<String> names = users.stream()
                .map(User::getUserName)
                .toList();
        return users.isEmpty()
                ? Result.error(401, "User Not Found", response)
                : Result.success(names);
    }

    public static class UserClassRequest {
        private int userClassCode;
    }
}
