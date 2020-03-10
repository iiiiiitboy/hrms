package ex.aaronfae.graduation.jwt;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import ex.aaronfae.graduation.base.entity.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_sys_role")
public class SysRole extends BaseDO implements Serializable {

	private static final long serialVersionUID = 7199931389593716574L;

	private String name;
	private String nameZh;

}
