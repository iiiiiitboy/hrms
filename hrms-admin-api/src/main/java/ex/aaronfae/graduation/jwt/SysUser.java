package ex.aaronfae.graduation.jwt;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ex.aaronfae.graduation.base.entity.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_sys_user")
public class SysUser extends BaseDO implements Serializable {

	private static final long serialVersionUID = 3352243038878296176L;

	private String name;
	private Long employeeId;
	private String username;
	@JsonIgnore
	private String password;
	private String remarks;
	private Boolean enabled;
	@TableField(exist = false)
	private List<SysRole> roles;

}
