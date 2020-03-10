package ex.aaronfae.graduation.business.hr.model.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdsAndNamesForQuickCommuteVO implements Serializable {

	private static final long serialVersionUID = -275817863963232414L;

	private String value;
	private String name;
}
