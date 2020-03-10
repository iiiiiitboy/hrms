package ex.aaronfae.graduation.business.hr.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LookApplicantsFilterDTO {

	private Long dept;
	private Long job;
	private Boolean interviewed;
	private Boolean employed;

}
