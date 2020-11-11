package com.wenqicode.community.service;

import com.wenqicode.community.dto.CommentDTO;
import com.wenqicode.community.enums.CommentTypeEnum;
import com.wenqicode.community.enums.NotificationTypeEnum;
import com.wenqicode.community.enums.NotificationStatusEnum;
import com.wenqicode.community.exception.CustomizeErrorCode;
import com.wenqicode.community.exception.CustomizeException;
import com.wenqicode.community.mapper.*;
import com.wenqicode.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wenqi Liang
 * @date 2020/10/6
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    /**
     * 插入评论内容, 并更新评论数
     *
     * @param comment
     * @param commentator
     */
    @Transactional
    public void insert(Comment comment, User commentator) {

        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }else {
                commentMapper.insert(comment);
                // 增加评论数
                Comment parentComment = new Comment();
                parentComment.setId(comment.getParentId());
                parentComment.setCommentCount(1);
                commentExtMapper.incCommentCount(parentComment);

                // 回复问题
                Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
                if (question == null) {
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }

                // 增加通知内容
                createNotify(comment,
                                dbComment.getCommentator(),
                                commentator.getName(),
                                question.getTitle(),
                                NotificationTypeEnum.REPLY_COMMENT);
            }
        }else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);

            // 设置通知内容
            createNotify(comment,
                        question.getCreator(),
                        commentator.getName(),
                        question.getTitle(),
                        NotificationTypeEnum.REPLY_QUESTION);
        }
    }

    /**
     * 创建通知
     * @param comment 提交的评论内容
     * @param receiver
     * @param outerTitle
     * @param notifierName
     * @param notificationTypeEnum
     */
    private void createNotify(Comment comment,
                                Long receiver,
                                String outerTitle,
                                String notifierName,
                                NotificationTypeEnum notificationTypeEnum) {
        // 设置通知内容
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTypeEnum.getType());
        notification.setOuterid(comment.getParentId());
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotificationName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    /**
     * 查询评论内容
     * @param id
     * @param type
     * @return
     */
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取所有评论者id, 返回Set集合(保证id不重复)
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        // 根据userId来查找User对象, 并将user对象存入map中
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
