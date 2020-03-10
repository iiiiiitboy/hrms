package ex.aaronfae.graduation.business.reimbursement.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ex.aaronfae.graduation.base.serializer.CustomBigDecimalSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReimbursementVO implements Serializable {

	private static final long serialVersionUID = 2421377278646313128L;

	private Long id;
	private Long employeeId;
	private String employeeName;
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal loan;
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal expenses;
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal debt;
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal reimbursements;
	private String description;
	private Boolean reviewed;
	private Boolean verified;
	private Boolean finished;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date createDate;

}
