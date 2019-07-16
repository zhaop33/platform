package com.zhao.platform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhao.platform.modal.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

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
public class UserEntity extends BaseEntity implements UserDetails {

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
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Collection<GrantedAuthority> authorities;
//    @TableField(exist = false)
//    private Boolean accountNonExpired = true;
//    @TableField(exist = false)
//    private Boolean accountNonLocked = true;
//    @TableField(exist = false)
//    private Boolean credentialsNonExpired = true;
//    @TableField(exist = false)
//    private Boolean enable = true;
    @TableField(exist = true,value = "password_")
    private String password = "";

    @Override
    @ApiModelProperty(hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public String getUsername() {
        return this.name;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isEnabled() {
        return true;
    }


}
