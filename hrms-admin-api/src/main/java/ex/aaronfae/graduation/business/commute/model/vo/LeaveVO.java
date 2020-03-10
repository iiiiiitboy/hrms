package ex.aaronfae.graduation.business.commute.model.vo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import ex.aaronfae.graduation.base.enumerate.TypeOfLeaveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveVO implements Serializable {

	private static final long serialVersionUID = 4463493449567694867L;

	private Long id;
	private Long employeeId;
	private String employeeName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date dateFrom;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date dateTo;
	private Integer hoursOfLeave;
	private TypeOfLeaveEnum typeOfLeave;
	private String reason;
	@TableField(value = "is_reviewd")
	private Boolean reviewed;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date createDate;

}
