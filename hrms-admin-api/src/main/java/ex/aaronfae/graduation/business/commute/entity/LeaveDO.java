package ex.aaronfae.graduation.business.commute.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import ex.aaronfae.graduation.base.entity.BaseDO;
import ex.aaronfae.graduation.base.enumerate.TypeOfLeaveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_leave")
public class LeaveDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = -6817457401369830157L;

	private Long employeeId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date dateFrom;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date dateTo;
	private Integer hoursOfLeave;
	private TypeOfLeaveEnum typeOfLeave;
	private String reason;
	@TableField(value = "is_reviewed")
	private Boolean reviewed;

}
