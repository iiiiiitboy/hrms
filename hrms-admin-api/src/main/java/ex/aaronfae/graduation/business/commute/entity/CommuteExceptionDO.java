package ex.aaronfae.graduation.business.commute.entity;

import java.io.Serializable;
import java.sql.Time;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import ex.aaronfae.graduation.base.entity.BaseDO;
import ex.aaronfae.graduation.base.enumerate.CommuteExceptionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_commute_exception")
public class CommuteExceptionDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = -6194141585020040035L;

	private Long employeeId;
	private Long commuteId;
	private Long witness;
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
