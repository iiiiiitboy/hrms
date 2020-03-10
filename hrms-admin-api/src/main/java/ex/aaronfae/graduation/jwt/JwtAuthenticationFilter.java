package ex.aaronfae.graduation.jwt;

import static ex.aaronfae.graduation.jwt.JwtUtil.ROLE;
import static ex.aaronfae.graduation.jwt.JwtUtil.USERNAME;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final PathMatcher pathMatcher = new AntPathMatcher();
	private String protectUrlPattern = "/api/**";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			if (request.getMethod().equals("OPTIONS")) {
				filterChain.doFilter(request, response);
				return;
			}
			if (isProtectedUrl(request)) {
				Map<String, Object> claims = JwtUtil.validateTokenAndGetClaims(request);
				String username = String.valueOf(claims.get(USERNAME));
				String role = String.valueOf(claims.get(ROLE));
				// 最关键的部分就是这里, 我们直接注入了

				SecurityContextHolder.getContext().setAuthentication(
						new UsernamePasswordAuthenticationToken(username, null, Arrays.asList(() -> role)));

			}
		} catch (Exception e) {
//			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "非法用户");

//			ResponseVO responseVO = new ResponseVO(401, "非法用户", null);
//			ObjectMapper objectMapper = new ObjectMapper();
//			PrintWriter out = response.getWriter();
//			objectMapper.writeValue(out, responseVO);
//			out.flush();
//			out.close();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}
		filterChain.doFilter(request, response);
	}

	private boolean isProtectedUrl(HttpServletRequest request) {
		return pathMatcher.match(protectUrlPattern, request.getServletPath());
	}

}