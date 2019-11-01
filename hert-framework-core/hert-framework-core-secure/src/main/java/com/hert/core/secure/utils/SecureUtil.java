package com.hert.core.secure.utils;

import com.hert.core.secure.LoginUser;
import com.hert.core.secure.TokenInfo;
import com.hert.core.secure.constant.SecureConstant;
import com.hert.core.secure.exception.SecureException;
import com.hert.core.secure.provider.IClientDetails;
import com.hert.core.secure.provider.IClientDetailsService;
import com.hert.core.secure.constant.TokenConstant;
import com.hert.core.tool.utils.Charsets;
import com.hert.core.tool.utils.Func;
import com.hert.core.tool.utils.SpringUtil;
import com.hert.core.tool.utils.StringPool;
import com.hert.core.tool.utils.StringUtil;
import com.hert.core.tool.utils.WebUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

/**
 * Secure工具类
 *
 * @author Chill
 */
public class SecureUtil {
	private static final String LOGIN_USER_REQUEST_ATTR = "_Login_USER_REQUEST_ATTR_";

	private final static String HEADER = TokenConstant.HEADER;
	private final static String BEARER = TokenConstant.BEARER;
	private final static String ACCOUNT = TokenConstant.ACCOUNT;
	private final static String ACCOUNT_TYPE = TokenConstant.ACCOUNT_TYPE;
	private final static String USER_ID = TokenConstant.USER_ID;
	private final static String ROLE_ID = TokenConstant.ROLE_ID;
	private final static String USER_NAME = TokenConstant.USER_NAME;
	private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
	private final static String PERMISSIONS = TokenConstant.PERMISSIONS;
	private final static String PERMISSIONS_ID = TokenConstant.PERMISSIONS_ID;
	private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
	private final static Integer AUTH_LENGTH = TokenConstant.AUTH_LENGTH;
	private static String BASE64_SECURITY = Base64.getEncoder().encodeToString(TokenConstant.SIGN_KEY.getBytes(Charsets.UTF_8));

	private static IClientDetailsService clientDetailsService;

	static {
		clientDetailsService = SpringUtil.getBean(IClientDetailsService.class);
	}

	/**
	 * 获取用户信息
	 *
	 * @return LoginUser
	 */
	public static LoginUser getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		// 优先从 request 中获取
		Object loginUser = request.getAttribute(LOGIN_USER_REQUEST_ATTR);
		if (loginUser == null) {
			loginUser = getUser(request);
			if (loginUser != null) {
				// 设置到 request 中
				request.setAttribute(LOGIN_USER_REQUEST_ATTR, loginUser);
			}
		}
		return (LoginUser) loginUser;
	}

	/**
	 * 获取用户信息
	 *
	 * @param request request
	 * @return LoginUser
	 */
	public static LoginUser getUser(HttpServletRequest request) {
		Claims claims = getClaims(request);
		if (claims == null) {
			return null;
		}
		return LoginUser.builder()
				.userId(Func.toInt(claims.get(SecureUtil.USER_ID)))
				.clientId(Func.toStr(claims.get(SecureUtil.CLIENT_ID)))
				.account(Func.toStr(claims.get(SecureUtil.ACCOUNT)))
				.roleName(Func.toStrList(Func.toStr(claims.get(SecureUtil.ROLE_NAME))))
				.roleId(Func.toIntList(Func.toStr(claims.get(SecureUtil.ROLE_ID))))
				.permission(Func.toStrList(Func.toStr(claims.get(SecureUtil.PERMISSIONS))))
				.permissionId(Func.toIntList(Func.toStr(claims.get(SecureUtil.PERMISSIONS_ID))))
				.userName(Func.toStr(claims.get(SecureUtil.USER_NAME)))
				.accountType(Func.toInt(Func.toStr(claims.get(SecureUtil.ACCOUNT_TYPE))))
				.build();
	}


	/**
	 * 获取用户id
	 *
	 * @return userId
	 */
	public static Integer getUserId() {
		HttpServletRequest request = WebUtil.getRequest();
		return getUserId(request);
	}

	/**
	 * 获取用户类型
	 *
	 * @return accountType
	 */
	public static Integer getAccountType() {
		LoginUser user = getUser();
		return (null == user) ? -1 : user.getAccountType();
	}

	/**
	 * 获取用户id
	 *
	 * @param request request
	 * @return userId
	 */
	public static Integer getUserId(HttpServletRequest request) {
		LoginUser user = getUser(request);
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户账号
	 *
	 * @return userAccount
	 */
	public static String getUserAccount() {
		HttpServletRequest request = WebUtil.getRequest();
		return getUserAccount(request);
	}

	/**
	 * 获取用户账号
	 *
	 * @param request request
	 * @return userAccount
	 */
	public static String getUserAccount(HttpServletRequest request) {
		LoginUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户名
	 *
	 * @return userName
	 */
	public static String getUserName() {
		HttpServletRequest request = WebUtil.getRequest();
		return getUserName(request);
	}

	/**
	 * 获取用户名
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserName(HttpServletRequest request) {
		LoginUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户角色name
	 *
	 * @return userName
	 */
	public static List<String> getUserRole() {
		LoginUser user = getUser();
		return (null == user) ? Arrays.asList() : user.getRoleName();
	}

	/**
	 * 获取用户角色id
	 *
	 * @return userName
	 */
	public static List<Integer> getUserRoleId() {
		LoginUser user = getUser();
		return (null == user) ? null : user.getRoleId();
	}

	/**
	 * 获取权限id
	 *
	 * @return userName
	 */
	public static List<Integer> getPermissionId() {
		LoginUser user = getUser();
		return (null == user) ? null : user.getPermissionId();
	}

	/**
	 * 获取权限
	 *
	 * @return userName
	 */
	public static List<String> getPermission() {
		LoginUser user = getUser();
		return (null == user) ? null : user.getPermission();
	}

	/**
	 * 获取客户端id
	 *
	 * @return tenantCode
	 */
	public static String getClientId() {
		LoginUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取Claims
	 *
	 * @param request request
	 * @return Claims
	 */
	public static Claims getClaims(HttpServletRequest request) {
		String accessToken = request.getHeader(SecureUtil.HEADER);
		return getClaims(accessToken);
	}

	/**
	 * 获取Claims
	 *
	 * @param accessToken request
	 * @return Claims
	 */
	public static Claims getClaims(String accessToken) {
		if ((accessToken != null) && (accessToken.length() > AUTH_LENGTH)) {
			String headStr = accessToken.substring(0, 6).toLowerCase();
			if (headStr.compareTo(SecureUtil.BEARER) == 0) {
				accessToken = accessToken.substring(7);
				return SecureUtil.parseJWT(accessToken);
			}
		}
		return null;
	}

	/**
	 * 获取请求头
	 *
	 * @return header
	 */
	public static String getHeader() {
		return getHeader(Objects.requireNonNull(WebUtil.getRequest()));
	}

	/**
	 * 获取请求头
	 *
	 * @param request request
	 * @return header
	 */
	public static String getHeader(HttpServletRequest request) {
		return request.getHeader(HEADER);
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param accessToken accessToken
	 * @return Claims
	 */
	public static Claims parseJWT(String accessToken) {
		try {
			return Jwts.parser()
				.setSigningKey(Base64.getDecoder().decode(BASE64_SECURITY))
				.parseClaimsJws(accessToken).getBody();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 创建令牌
	 *
	 * @param user      user
	 * @param audience  audience
	 * @param issuer    issuer
	 * @param tokenType tokenType
	 * @return jwt
	 */
	public static TokenInfo createJWT(Map<String, String> user, String audience, String issuer, String tokenType) {

		String[] tokens = extractAndDecodeHeader();
		assert tokens.length == 2;
		String clientId = tokens[0];
		String clientSecret = tokens[1];

		// 获取客户端信息
		IClientDetails clientDetails = clientDetails(clientId);

		// 校验客户端信息
		if (!validateClient(clientDetails, clientId, clientSecret)) {
			throw new SecureException("客户端认证失败!");
		}

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//生成签名密钥
		byte[] apiKeySecretBytes = Base64.getDecoder().decode(BASE64_SECURITY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//添加构成JWT的类
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JsonWebToken")
			.setIssuer(issuer)
			.setAudience(audience)
			.signWith(signatureAlgorithm, signingKey);

		//设置JWT参数
		user.forEach(builder::claim);

		//设置应用id
		builder.claim(CLIENT_ID, clientId);

		//添加Token过期时间
		long expireMillis;
		if (tokenType.equals(TokenConstant.ACCESS_TOKEN)) {
			expireMillis = clientDetails.getAccessTokenValidity() * 1000;
		} else if (tokenType.equals(TokenConstant.REFRESH_TOKEN)) {
			expireMillis = clientDetails.getRefreshTokenValidity() * 1000;
		} else {
			expireMillis = getExpire();
		}
		long expMillis = nowMillis + expireMillis;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp).setNotBefore(now);

		// 组装Token信息
		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setToken(builder.compact());
		tokenInfo.setExpire((int) expireMillis / 1000);

		return tokenInfo;
	}

	/**
	 * 获取过期时间(次日凌晨3点)
	 *
	 * @return expire
	 */
	public static long getExpire() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis() - System.currentTimeMillis();
	}

	/**
	 * 客户端信息解码
	 */
	@SneakyThrows
	public static String[] extractAndDecodeHeader() {
		// 获取请求头客户端信息
		String header = Objects.requireNonNull(WebUtil.getRequest()).getHeader(SecureConstant.BASIC_HEADER_KEY);
		if (header == null || !header.startsWith(SecureConstant.BASIC_HEADER_PREFIX)) {
			throw new SecureException("No client information in request header");
		}
		byte[] base64Token = header.substring(6).getBytes(Charsets.UTF_8_NAME);

		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException var7) {
			throw new RuntimeException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, Charsets.UTF_8_NAME);
		int index = token.indexOf(StringPool.COLON);
		if (index == -1) {
			throw new RuntimeException("Invalid basic authentication token");
		} else {
			return new String[]{token.substring(0, index), token.substring(index + 1)};
		}
	}

	/**
	 * 获取请求头中的客户端id
	 */
	public static String getClientIdFromHeader() {
		String[] tokens = extractAndDecodeHeader();
		assert tokens.length == 2;
		return tokens[0];
	}

	/**
	 * 获取客户端信息
	 *
	 * @param clientId 客户端id
	 * @return clientDetails
	 */
	private static IClientDetails clientDetails(String clientId) {
		return clientDetailsService.loadClientByClientId(clientId);
	}

	/**
	 * 校验Client
	 *
	 * @param clientId     客户端id
	 * @param clientSecret 客户端密钥
	 * @return boolean
	 */
	private static boolean validateClient(IClientDetails clientDetails, String clientId, String clientSecret) {
		if (clientDetails != null) {
			return StringUtil.equals(clientId, clientDetails.getClientId()) && StringUtil.equals(clientSecret, clientDetails.getClientSecret());
		}
		return false;
	}

}
