package ex.aaronfae.graduation.jwt;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentSysUserVO implements Serializable {

	private static final long serialVersionUID = -4983985806683485464L;

	private Long id;
	private Long employeeId;
	private String name;
	private String username;
	private List<SysRole> roles;

}
