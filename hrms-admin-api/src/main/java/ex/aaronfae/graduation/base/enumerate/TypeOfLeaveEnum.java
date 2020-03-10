package ex.aaronfae.graduation.base.enumerate;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeOfLeaveEnum implements IEnum<Integer> {

	PERSONAL_LEAVE(0, "事假"), SICK_LEAVE1(1, "病假"), MENSTRUATION_LEAVE(2, "生理假"), MARRIAGE_LEAVE(3, "婚假"),
	MATERNITY_LEAVE(4, "产假"), ANNUAL_LEAVE(5, "年假");

	private int code;
	private String descp;

	TypeOfLeaveEnum(int code) {
		this.code = code;
	}

	TypeOfLeaveEnum(int code, String descp) {
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
