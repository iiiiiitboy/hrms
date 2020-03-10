package ex.aaronfae.graduation.business.organization.model.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobVO implements Serializable {

	private static final long serialVersionUID = -3838007520980931714L;

	private Long id;
	private String name;
	private String deptName;
	private String level;
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	private Date createDate;

}
