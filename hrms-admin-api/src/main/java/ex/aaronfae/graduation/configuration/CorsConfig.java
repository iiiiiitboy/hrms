package ex.aaronfae.graduation.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 跨域配置
 * 
 * @author AaronFae
 *
 */
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 允许跨域访问的路径
				.allowedOrigins("*") // 允许跨域访问的源
				.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE") // 允许请求方法
				.maxAge(14400) // 预检间隔时间
				.allowedHeaders("*") // 允许头部设置
				.allowCredentials(true); // 是否发送cookie
	}

}