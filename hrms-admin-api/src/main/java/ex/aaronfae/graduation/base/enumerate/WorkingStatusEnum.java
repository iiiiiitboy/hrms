package ex.aaronfae.graduation.base.enumerate;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkingStatusEnum implements IEnum<Integer> {

	PROBATION(0, "试用期"), ONLINE(1, "在职"), BUSINESS_TRIP(2, "出差"), VACATION(3, "休假"), OFFLINE(4, "离职"), RETIRED(5, "退休");

	private int code;
	private String descp;

	WorkingStatusEnum(int code) {
		this.code = code;
	}

	WorkingStatusEnum(int code, String descp) {
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
