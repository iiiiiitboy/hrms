package ex.aaronfae.graduation.business.hr.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("t_job")
@JsonInclude(Include.NON_NULL)
public class JobDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 4502210534210361544L;

	private Long deptId;
	private String name;
	private BigDecimal basicSalary;
	private String level;

}
