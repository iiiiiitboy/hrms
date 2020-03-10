package ex.aaronfae.graduation.base.enumerate;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderEnum implements IEnum<Integer> {

	FEMALE(0, "女"), MALE(1, "男");

	private int code;
	private String descp;

	GenderEnum(int code) {
		this.code = code;
	}

	GenderEnum(int code, String descp) {
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
