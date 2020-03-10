package ex.aaronfae.graduation.business.commute.entity;

import java.io.Serializable;
import java.sql.Time;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import ex.aaronfae.graduation.base.entity.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_commute")
public class CommuteDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = -4819209916343206004L;

	private Long employeeId;
	private String today;
	@JsonFormat(pattern = "HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Time toWorkTime;
	@TableField(value = "is_to_Work")
	private Boolean toWork;
	@JsonFormat(pattern = "HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Time offWorkTime;
	@TableField(value = "is_off_Work")
	private Boolean offWork;
	private Boolean morningCalled;
	private Boolean afternoonCalled;

}
