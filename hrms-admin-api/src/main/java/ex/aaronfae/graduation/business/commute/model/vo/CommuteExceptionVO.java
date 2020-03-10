package ex.aaronfae.graduation.business.commute.model.vo;

import java.io.Serializable;
import java.sql.Time;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import ex.aaronfae.graduation.base.enumerate.CommuteExceptionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommuteExceptionVO implements Serializable {

	private static final long serialVersionUID = 8705831320001973999L;

	private Long id;
	private Long employeeId;
	private Long commuteId;
	private Long witnessId;
	private String employeeName;
	private String today;
	@JsonFormat(pattern = "HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Time toWorkTime;
	@TableField(value = "is_to_Work")
	private Boolean toWork;
	@JsonFormat(pattern = "HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Time offWorkTime;
	@TableField(value = "is_off_Work")
	private Boolean offWork;
	private Integer deductedWorkingHours;
	private CommuteExceptionTypeEnum commuteExceptionType;
	private String remarks;
	private Boolean done;

}
