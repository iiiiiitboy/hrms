package ex.aaronfae.graduation.business.hr.model.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NamesAndIdsForQuickCommuteVO implements Serializable {

	private static final long serialVersionUID = 94375684589921797L;

	private String value;
	private Long id;

}
