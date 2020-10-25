package com.wenqicode.community.controller;

import com.wenqicode.community.cache.TagCache;
import com.wenqicode.community.dto.QuestionDTO;
import com.wenqicode.community.mapper.UserMapper;
import com.wenqicode.community.model.Question;
import com.wenqicode.community.model.User;
import com.wenqicode.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wenqi Liang
 * @date 2020/9/28
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id,
                       Model model) {
        QuestionDTO question = questionService.getById(id);
        // 问题回显
        model.addAttribute("tag", question.getTag());
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("questionId", question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value = "questionId", required = false) Long questionId,
                            HttpServletRequest request,
                            Model model) {
        // 设置显示的值至model中
        model.addAttribute("tag", tag);
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tags", TagCache.get());
        // 输入端不能为空
        if (StringUtils.isEmpty(title)) {
            model.addAttribute("error", "标题不能为空!");
            return "publish";
        }
        if (StringUtils.isEmpty(description)) {
            model.addAttribute("error", "问题补充不能为空!");
            return "publish";
        }
        if (StringUtils.isEmpty(tag)) {
            model.addAttribute("error", "标签不能为空!");
            return "publish";
        }
        // 过滤非法标签
        String invalid = TagCache.filterInvalid(tag);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(invalid)){
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        // 用户没有登录, 返回主页
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户尚未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(questionId);

        questionService.createOrUpdate(question);

        return "redirect:/";
    }
}
