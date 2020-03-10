package ex.aaronfae.graduation.business.hr.model.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import ex.aaronfae.graduation.base.enumerate.EducationEnum;
import ex.aaronfae.graduation.base.enumerate.TypeOfWorkingEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentForApplicantVO implements Serializable {

	private static final long serialVersionUID = 7900717057787295955L;
	
	private Long id;
	private String deptName;
	private String jobName;
	private TypeOfWorkingEnum typeOfWorking;
	private Integer yearsOfWorking;
	private Integer numberOf;
	private String addressOfWorking;
	private EducationEnum education;
	private String requirement;
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	private Date releaseDate;

}
