package com.zhao.platform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhao.platform.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

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
@TableName("t_pf_user_")
public class UserEntity extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUID = 1L;
    @TableId("id_")
    private String id;
    @TableField("name_")
    private String name;
    @TableField("nike_name_")
    private String nikeName;
    @TableField("sex_")
    private Integer sex;
    @TableField("province_")
    private String province;
    @TableField("city_")
    private String city;
    @TableField("address_")
    private String address;
    @TableField("tel_phone_")
    private String telPhone;
    @TableField("email_")
    private String email;
    @TableField("avatar_url_")
    private String avatarUrl;
    @TableField("description_")
    private String description;


    @Override
    public String getAuthority() {
        return null;
    }
}
