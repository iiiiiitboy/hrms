package ex.aaronfae.graduation.business.trip.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTripConfirmDTO implements Serializable {

	private static final long serialVersionUID = 2810969404199764034L;
	
	private Long id;
	private Long employeeId;

}
