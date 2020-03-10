package ex.aaronfae.graduation.business.reimbursement.entity;

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
@TableName("t_reimbursement")
public class ReimbursementDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 5760563365661658734L;

	private Long employeeId;
	private Long businessTripId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date time;
	private BigDecimal loan;
	private BigDecimal expenses;
	private BigDecimal debt;
	private BigDecimal reimbursements;
	private String description;
	@TableField(value = "is_reviewed")
	private Boolean reviewed;
	@TableField(value = "is_verified")
	private Boolean verified;
	@TableField(value = "is_finished")
	private Boolean finished;

}
