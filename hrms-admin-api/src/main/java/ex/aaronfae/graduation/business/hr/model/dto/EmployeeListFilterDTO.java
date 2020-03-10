package ex.aaronfae.graduation.business.hr.model.dto;

import ex.aaronfae.graduation.base.enumerate.WorkingStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeListFilterDTO {

	private Long dept;
	private Long job;
	private WorkingStatusEnum workingStatus;

}
