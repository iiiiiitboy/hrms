package ex.aaronfae.graduation.business.hr.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ex.aaronfae.graduation.base.entity.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_interview_result")
@JsonInclude(Include.NON_NULL)
public class InterviewResultDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = -8167297987510241414L;

	private Long applicantId;
	private Long recruitmentId;
	private Integer mark;
	private String appraisal;
	private Long interviewerId;

}
