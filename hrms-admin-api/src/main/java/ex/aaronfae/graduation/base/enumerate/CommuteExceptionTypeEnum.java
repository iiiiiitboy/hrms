package ex.aaronfae.graduation.base.enumerate;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CommuteExceptionTypeEnum implements IEnum<Integer> {

	NORMAL(0, "正常"), BE_LATE(1, "迟到"), LEAVE_EARLY(2, "早退"), NO_TO_WORK(3, "没打上班卡"), NO_TO_LEAVE(4, "没打下班卡");

	private int code;
	private String descp;

	CommuteExceptionTypeEnum(int code) {
		this.code = code;
	}

	CommuteExceptionTypeEnum(int code, String descp) {
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
