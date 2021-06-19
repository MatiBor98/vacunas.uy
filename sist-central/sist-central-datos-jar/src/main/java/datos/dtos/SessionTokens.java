package datos.dtos;

import java.io.Serializable;

public class SessionTokens implements Serializable {

	String accessToken;
	String refreshToken;
	
	public SessionTokens() {}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
}
