package ex.aaronfae.graduation.business.salary.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import ex.aaronfae.graduation.base.entity.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_monthly_salary")
public class MonthlySalaryDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = -152016603567095270L;

	private Long employeeId;
	private String year;
	private String month;
	private String daysOfWorking;
	private Integer hoursOfWorking;
	private String daysOfAttendance;
	private String daysOfAbsence;
	private Integer hoursOfWorked;
	private BigDecimal hoursOfOvertime;
	private BigDecimal salary;
	@TableField(value = "is_reviewed")
	private Boolean reviewed;

}
