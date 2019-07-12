package com.zhao.platform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhao.platform.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaoyj
 * @since 2019-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_pf_user_auths_")
public class UserAuthsEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @TableId("id_")
    private String id;
    @TableField("user_id_")
    private String userId;
    @TableField("identity_type_")
    /**
     * 登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；
     */
    private String identityType;
    @TableField("identifier_")
    /**
     * 身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号；
     */
    private String identifier;
    @TableField("credential_")
    /**
     * 站内账号是密码、第三方登录是Token；
     */
    private String credential;
    @TableField("varified")
    /**
     * 是否验证
     */
    private Integer varified;
    @TableField("description_")
    /**
     * 描述
     */
    private String description;


}
