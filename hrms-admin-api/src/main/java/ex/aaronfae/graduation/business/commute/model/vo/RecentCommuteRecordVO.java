package ex.aaronfae.graduation.business.commute.model.vo;

import java.io.Serializable;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentCommuteRecordVO implements Serializable {

	private static final long serialVersionUID = -424155164954182445L;

	private String employeeName;
	private String today;
	@JsonFormat(pattern = "HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Time toWorkTime;
	private Boolean toWork;
	@JsonFormat(pattern = "HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Time offWorkTime;
	private Boolean offWork;

}
