package ex.aaronfae.graduation.business.hr.model.vo;

import java.io.Serializable;

import ex.aaronfae.graduation.base.enumerate.EducationEnum;
import ex.aaronfae.graduation.base.enumerate.TypeOfWorkingEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentVO implements Serializable {

	private static final long serialVersionUID = 311903674833270983L;
	
	private Long id;
	private String deptName;
	private String jobName;
	private TypeOfWorkingEnum typeOfWorking;
	private Integer yearsOfWorking;
	private Integer numberOf;
	private String addressOfWorking;
	private EducationEnum education;
	private String requirement;
	private Boolean reviewed;
	private Boolean finished;

}
