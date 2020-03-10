package ex.aaronfae.graduation.business.hr.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import ex.aaronfae.graduation.base.enumerate.EducationEnum;
import ex.aaronfae.graduation.base.enumerate.GenderEnum;
import ex.aaronfae.graduation.base.enumerate.WorkingStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVO implements Serializable {

	private static final long serialVersionUID = -5223428251594127291L;

	private Long id;
	private String name;
	private String tel;
	private Integer age;
	private GenderEnum gender;
	private String email;
	private EducationEnum education;
	private Long deptId;
	private String deptName;
	private Long jobId;
	private String jobName;
	private Long applicantId;
	private BigDecimal salary;
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	private Date entryDate;
	private WorkingStatusEnum workingStatus;
	private String resumePath;
	private Integer mark;
	private String appraisal;

}
