package ex.aaronfae.graduation.jwt;

import java.util.List;

public class UserInfoVO {

	private String name;
	private String avatar;
	private String introduction;
	private List<String> roles;

	public UserInfoVO() {
	}

	public UserInfoVO(String name, String avatar, String introduction, List<String> roles) {
		super();
		this.name = name;
		this.avatar = avatar;
		this.introduction = introduction;
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
