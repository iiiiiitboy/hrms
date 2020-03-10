package ex.aaronfae.graduation.base.model.vo;

public class ResponseVO {

	private Integer code;
	private String message;
	private Object data;

	private ResponseVO() {
	}

	public static ResponseVO build() {
		return new ResponseVO();
	}

	public static ResponseVO ok(String message, Object data) {
		return new ResponseVO(200, message, data);
	}

	public static ResponseVO ok(String message) {
		return new ResponseVO(200, message, null);
	}

	public static ResponseVO error(String message, Object data) {
		return new ResponseVO(500, message, data);
	}

	public static ResponseVO error(String msg) {
		return new ResponseVO(500, msg, null);
	}

	public ResponseVO(Integer code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public ResponseVO setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getData() {
		return data;
	}

	public ResponseVO setData(Object data) {
		this.data = data;
		return this;
	}

}
