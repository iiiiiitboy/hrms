package ex.aaronfae.graduation.business.hr.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ex.aaronfae.graduation.base.entity.BaseDO;
import ex.aaronfae.graduation.base.enumerate.EducationEnum;
import ex.aaronfae.graduation.base.enumerate.TypeOfWorkingEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_recruitment")
@JsonInclude(Include.NON_NULL)
public class RecruitmentDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 28687426818965409L;

	private Long deptId;
	private Long jobId;
	private TypeOfWorkingEnum typeOfWorking;
	private Integer yearsOfWorking;
	private Integer numberOf;
	private String addressOfWorking;
	private EducationEnum education;
	private String requirement;
	@TableField(value = "is_reviewed")
	private Boolean reviewed;
	@TableField(value = "is_finished")
	private Boolean finished;

}
