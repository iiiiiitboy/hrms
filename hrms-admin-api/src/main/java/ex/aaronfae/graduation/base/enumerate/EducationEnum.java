package ex.aaronfae.graduation.base.enumerate;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EducationEnum implements IEnum<Integer> {

	COLLEGA(0, "大专"), BACHELOR(1, "本科"), MASTER(2, "硕士"), DOCTOR(3, "博士");

	private int code;
	private String descp;

	EducationEnum(int code) {
		this.code = code;
	}

	EducationEnum(int code, String descp) {
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
