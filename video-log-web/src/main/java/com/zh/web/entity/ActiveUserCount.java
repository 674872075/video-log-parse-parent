package com.zh.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zhouhao
 * @version 1.0
 * @date 2020/4/18 14:47
 * @Description
 */
@Getter
@Setter
@Table(name = "dw_active_user_count")
public class ActiveUserCount implements Serializable {

    private static final long serialVersionUID = -907772445885741627L;

    @Id
    private String publishDate;

    private Long activeUserCount;
}
