package ex.aaronfae.graduation.base.enumerate;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CalendarTypeEnum implements IEnum<Integer> {

	WORKING_DAY(0, "工作日"), ORDINARY_HOLIDAYS(1, "普通假日"), STATUTORY_HOLIDAYS(2, "法定假日"), NO_PAY_DAY(3, "无薪假日");

	private int code;
	private String descp;

	CalendarTypeEnum(int code) {
		this.code = code;
	}

	CalendarTypeEnum(int code, String descp) {
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
