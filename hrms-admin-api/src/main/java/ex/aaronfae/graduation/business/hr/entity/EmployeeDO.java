package ex.aaronfae.graduation.business.hr.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ex.aaronfae.graduation.base.entity.BaseDO;
import ex.aaronfae.graduation.base.enumerate.EducationEnum;
import ex.aaronfae.graduation.base.enumerate.GenderEnum;
import ex.aaronfae.graduation.base.enumerate.WorkingStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_employee")
@JsonInclude(Include.NON_NULL)
public class EmployeeDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 6206499883711516936L;

	private String name;
	private String tel;
	private Integer age;
	private GenderEnum gender;
	private String email;
	private EducationEnum education;
	private Long deptId;
	private Long jobId;
	private Long applicantId;
	private BigDecimal salary;
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	private Date entryDate;
	private WorkingStatusEnum workingStatus;

}
