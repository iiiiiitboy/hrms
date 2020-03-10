package ex.aaronfae.graduation.business.hr.model.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class InterviewResultVO implements Serializable {

	private static final long serialVersionUID = 7137411076262266394L;
	
	private Long id;
	private Integer mark;
	private String appraisal;
	private String interviewer;

}
