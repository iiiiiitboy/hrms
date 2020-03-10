package ex.aaronfae.graduation.business.salary.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import ex.aaronfae.graduation.base.entity.BaseDO;
import ex.aaronfae.graduation.base.enumerate.CalendarTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_work_calendar")
public class WorkCalendarDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 6206499883711516936L;

	private Date days;
	private CalendarTypeEnum calendarType;

}
