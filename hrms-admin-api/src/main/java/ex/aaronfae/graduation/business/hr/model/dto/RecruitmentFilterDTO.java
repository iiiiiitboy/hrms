package ex.aaronfae.graduation.business.hr.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitmentFilterDTO {

	private Long dept;
	private Long job;
	private Boolean reviewed;
	private Boolean finished;

}
