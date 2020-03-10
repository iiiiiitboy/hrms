package ex.aaronfae.graduation.base.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public abstract class BaseDO {

	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date gmtCreate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private Date gmtModified;
	@TableLogic
	private Boolean logicDeleted;

}
