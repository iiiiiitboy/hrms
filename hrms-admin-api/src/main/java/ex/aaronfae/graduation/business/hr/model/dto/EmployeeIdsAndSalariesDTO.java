package ex.aaronfae.graduation.business.hr.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeIdsAndSalariesDTO implements Serializable {

	private static final long serialVersionUID = -123655345398284001L;

	private Long id;
	private BigDecimal salary;

}
