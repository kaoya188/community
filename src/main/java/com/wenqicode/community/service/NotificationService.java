package com.wenqicode.community.service;

import com.sun.org.apache.xpath.internal.operations.String;
import com.wenqicode.community.dto.NotificationDTO;
import com.wenqicode.community.dto.PaginationDTO;
import com.wenqicode.community.enums.NotificationTypeEnum;
import com.wenqicode.community.mapper.NotificationMapper;
import com.wenqicode.community.mapper.UserMapper;
import com.wenqicode.community.model.Notification;
import com.wenqicode.community.model.NotificationExample;
import com.wenqicode.community.model.User;
import com.wenqicode.community.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wenqi Liang
 * @date 2020/10/27
 */
@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        Integer totalPage;
        // 数据封装到分页对象中
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        // 上一页和下一页越界的问题
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

        // offset = (page - 1) * size
        // 分页查询数据
        Integer offset = (page - 1) * size;
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example,
                new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setType(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOList);

        return paginationDTO;
    }

    public long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        return notificationMapper.countByExample(notificationExample);
    }
}
