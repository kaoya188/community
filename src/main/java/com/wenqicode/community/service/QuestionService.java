package com.wenqicode.community.service;

import com.wenqicode.community.dto.PaginationDTO;
import com.wenqicode.community.dto.QuestionDTO;
import com.wenqicode.community.mapper.QuestionMapper;
import com.wenqicode.community.mapper.UserMapper;
import com.wenqicode.community.model.Question;
import com.wenqicode.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wenqi Liang
 * @date 2020/10/1
 */
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer totalCount = questionMapper.countAll();
        // 数据封装到分页对象中
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount, page, size);
        // 上一页和下一页越界的问题
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        // offset = (page - 1) * size
        // 分页查询数据
        Integer offset = (page - 1) * size;
        List<Question> questions = questionMapper.list(size, offset);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            User user = userMapper.findById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer totalCount = questionMapper.countByUserId(userId);
        // 数据封装到分页对象中
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount, page, size);
        // 上一页和下一页越界的问题
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        // offset = (page - 1) * size
        // 分页查询数据
        Integer offset = (page - 1) * size;
        List<Question> questions = questionMapper.listByUserId(userId, size, offset);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            User user = userMapper.findById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }


    public QuestionDTO getById(Integer id) {
        Question question= questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            // 更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
