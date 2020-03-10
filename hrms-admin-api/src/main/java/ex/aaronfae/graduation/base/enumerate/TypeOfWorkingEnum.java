package ex.aaronfae.graduation.base.enumerate;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeOfWorkingEnum implements IEnum<Integer> {

	FULL_TIME(0, "全职"), PART_TIME(1, "兼职"), OTHER(2, "其他");

	private int code;
	private String descp;

	TypeOfWorkingEnum(int code) {
		this.code = code;
	}

	TypeOfWorkingEnum(int code, String descp) {
		this.code = code;
		this.descp = descp;
	}

	@Override
	public Integer getValue() {
		return code;
	}

	@JsonValue
	public String getDescp() {
		return descp;
	}

}
