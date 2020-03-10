package ex.aaronfae.graduation.business.hr.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ex.aaronfae.graduation.base.entity.BaseDO;
import ex.aaronfae.graduation.base.enumerate.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_applicant")
@JsonInclude(Include.NON_NULL)
public class ApplicantDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 3115415268292525268L;

	private String name;
	private String tel;
	private Integer age;
	private GenderEnum gender;
	private String email;
	private String resumePath;
	private Long recruitmentId;
	@TableField(value = "is_interviewing")
	private Boolean interviewing;
	@TableField(value = "is_interviewed")
	private Boolean interviewed;
	@TableField(value = "is_passed")
	private Boolean passed;
	@TableField(value = "is_employed")
	private Boolean employed;

}
