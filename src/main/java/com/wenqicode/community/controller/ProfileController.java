package com.wenqicode.community.controller;

import com.wenqicode.community.dto.PaginationDTO;
import com.wenqicode.community.mapper.UserMapper;
import com.wenqicode.community.model.Notification;
import com.wenqicode.community.model.User;
import com.wenqicode.community.service.NotificationService;
import com.wenqicode.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wenqi Liang
 * @date 2020/10/3
 */
@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "3") Integer size) {
        // 用户没有登录, 返回主页
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        // 回复问题or回复评论
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            // 返回主页, 并带上列表展示
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("paginationDTO", paginationDTO);
        }else if ("replies".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("paginationDTO", paginationDTO);
            model.addAttribute("unreadCount", unreadCount);
        }

        return "profile";
    }
}
