package ex.aaronfae.graduation.business.salary.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySalaryVO implements Serializable {

	private static final long serialVersionUID = -5301865659612770100L;

	private Long id;
	private Long employeeId;
	private String employeeName;
	private String deptName;
	private String jobName;
	private String month;
	private String daysOfWorking;
	private Integer hoursOfWorking;
	private String daysOfAttendance;
	private String daysOfAbsence;
	private Integer hoursOfWorked;
	private BigDecimal hoursOfOvertime;
	private BigDecimal basicSalary;
	private BigDecimal salary;
	private Boolean reviewed;

}
