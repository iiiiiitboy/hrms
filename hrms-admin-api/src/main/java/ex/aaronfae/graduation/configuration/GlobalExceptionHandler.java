package ex.aaronfae.graduation.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import ex.aaronfae.graduation.base.model.vo.ResponseVO;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = HttpClientErrorException.class)
	public @ResponseBody ResponseVO exceptionHandler(HttpClientErrorException e) {
		if (HttpStatus.UNAUTHORIZED == e.getStatusCode()) {
			return new ResponseVO(401, "非法用户", null);
		}
		if (HttpStatus.FORBIDDEN == e.getStatusCode()) {
			return new ResponseVO(403, "权限不足", null);
		}
		return new ResponseVO(400, "错误", null);
	}

	@ExceptionHandler
	public @ResponseBody ResponseVO exceptionHandler(Exception e) {
		return ResponseVO.error("服务器错误，请联系系统管理员处理");
	}

}
