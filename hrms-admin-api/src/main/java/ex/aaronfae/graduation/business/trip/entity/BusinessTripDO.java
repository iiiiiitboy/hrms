package ex.aaronfae.graduation.business.trip.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@TableName("t_business_trip")
public class BusinessTripDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = -2767885287641148627L;

	private Long employeeId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date endDate;
	private BigDecimal expenses;
	private String reason;
	@TableField(value = "is_reviewed")
	private Boolean reviewed;
	@TableField(value = "is_holding")
	private Boolean holding;
	@TableField(value = "is_finished")
	private Boolean finished;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date checkInTime;
	@TableField(value = "is_reimbursed")
	private Boolean reimbursed;

}
