package ex.aaronfae.graduation.business.hr.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ex.aaronfae.graduation.base.enumerate.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ApplicantVO implements Serializable {

	private static final long serialVersionUID = -2109673430846144208L;

	private Long id;
	private String name;
	private Integer age;
	private GenderEnum gender;
	private String tel;
	private String email;
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	private Date dateOfApplication;
	private Boolean interviewing;
	private Boolean interviewed;
	private Boolean passed;
	private Boolean employed;
	private String resumePath;
	private Long recruitmentId;
	private Long deptId;
	private String deptName;
	private Long jobId;
	private String jobName;
	private BigDecimal salary;
	private String remarks;

}
