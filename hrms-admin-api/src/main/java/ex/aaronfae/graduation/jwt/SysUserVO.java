package ex.aaronfae.graduation.jwt;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserVO implements Serializable {

	private static final long serialVersionUID = 5851974804639714930L;

	private String username;
	private String password;

}
