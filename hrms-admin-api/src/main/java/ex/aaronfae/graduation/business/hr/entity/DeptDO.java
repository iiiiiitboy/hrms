package ex.aaronfae.graduation.business.hr.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ex.aaronfae.graduation.base.entity.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_dept")
@JsonInclude(Include.NON_NULL)
public class DeptDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = -2764592783277991021L;
	
	private String name;

}
