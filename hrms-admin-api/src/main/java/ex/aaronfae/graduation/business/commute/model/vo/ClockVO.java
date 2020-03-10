package ex.aaronfae.graduation.business.commute.model.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClockVO implements Serializable {

	private static final long serialVersionUID = 3175805722773595589L;

	private Boolean isToWork;
	private Boolean isOffWork;
	private List<RecentCommuteRecordVO> record;

}
