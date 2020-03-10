package ex.aaronfae.graduation.business.commute.model.vo;

import java.io.Serializable;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommuteVO implements Serializable {

	private static final long serialVersionUID = -202176219923277730L;

	private Long id;
	private Long employeeId;
	private String employeeName;
	private String deptName;
	private String jobName;
	private String today;
	@JsonFormat(pattern = "HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Time toWorkTime;
	private Boolean toWork;
	@JsonFormat(pattern = "HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Time offWorkTime;
	private Boolean offWork;
	private Boolean morningCalled;
	private Boolean afternoonCalled;

}
