package com.wenqicode.community.dto;

import com.wenqicode.community.exception.CustomizeException;
import com.wenqicode.community.exception.ICustomizeErrorCode;
import lombok.Data;

/**
 * @author Wenqi Liang
 * @date 2020/10/6
 */
@Data
public class ResultDTO {

    private String message;
    private Integer code;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(ICustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(), ex.getMessage());
    }
}
