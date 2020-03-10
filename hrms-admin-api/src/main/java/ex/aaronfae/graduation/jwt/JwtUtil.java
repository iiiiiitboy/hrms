package ex.aaronfae.graduation.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	public static final long EXPIRATION_TIME = 36000_000; // 10 hour
	public static final String SECRET = "af.graduation";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String USERNAME = "USERNAME";
	public static final String ROLE = "ROLE";

	public static String generateToken(String username, List<SysRole> roles) {
		// you can put any data in the map
		HashMap<String, Object> map = new HashMap<>();
		map.put(USERNAME, username);
		/*
		 * StringBuffer rolesStringBuffer = new StringBuffer();
		 * rolesStringBuffer.append("["); for (SysRole sysRole : roles) {
		 * rolesStringBuffer.append(sysRole.getName()); rolesStringBuffer.append(","); }
		 * rolesStringBuffer.deleteCharAt(rolesStringBuffer.lastIndexOf(","));
		 * rolesStringBuffer.append("]");
		 */
		map.put(ROLE, roles.toString());

		String jwt = Jwts.builder().setClaims(map).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		return TOKEN_PREFIX + jwt;
	}

	public static Map<String, Object> validateTokenAndGetClaims(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token == null) {
			throw new TokenValidationException("Missing token");
		}
		// parse the token. exception when token is invalid
		Map<String, Object> body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody();
		return body;
	}

	static class TokenValidationException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public TokenValidationException(String msg) {
			super(msg);
		}
	}
}