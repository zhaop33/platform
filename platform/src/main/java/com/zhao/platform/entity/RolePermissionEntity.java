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
@TableName("t_pf_role_permission_")
public class RolePermissionEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @TableId("id_")
    private String id;
    @TableField("role_id_")
    private String roleId;
    @TableField("permission_id_")
    private String permissionId;


}
