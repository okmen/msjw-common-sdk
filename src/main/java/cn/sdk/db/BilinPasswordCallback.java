package cn.sdk.db;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.DruidPasswordCallback;

import cn.sdk.util.AesCipherUtil;

public class BilinPasswordCallback extends DruidPasswordCallback {
	
	private static final long serialVersionUID = 5924720124547471161L;
	
	private static final Logger LOG = Logger.getLogger(BilinPasswordCallback.class);
	
	private String passwordKey;
	
	public String getPasswordKey() {
		return passwordKey;
	}
	
	public void setPasswordKey(String passwordKey) {
		this.passwordKey = passwordKey;
	}
	
	@Override
	public char[] getPassword() {
		Properties props = this.getProperties();
		if (props == null || passwordKey == null || "".equals(passwordKey)) {
			return super.getPassword();
		} else {
			// 从connectionProperty里获取密码文件目录
			String passwordFile = props.getProperty("config.file");
			String secretKeyFile = props.getProperty("secretkey.file");
			if (passwordFile == null || secretKeyFile == null) {
				LOG.error("pom中connectionProperty里的config.file或secretkey.file属性没有配置");
			} else {
				InputStream ins = null;
				BufferedReader reader = null;
				try {
					// 读取密码配置文件
					ins = new BufferedInputStream(new FileInputStream(passwordFile));
					props.load(ins);
					// 读取密钥文件
					reader = new BufferedReader(new FileReader(secretKeyFile));
					String secretKey = reader.readLine();
					
					if (props.get(passwordKey) != null || secretKey == null) {
						String password = props.get(passwordKey).toString();
						return AesCipherUtil.aesDecrypt(password, secretKey).toCharArray();
					} else {
						LOG.error("给定的passworKey不存在或者密钥为空");
					}
				} catch (IOException e) {
					LOG.error("error: " + e.getMessage());
				} finally {
					if (ins != null) {
						try {
							ins.close();
						} catch (IOException e1) {
						}
					}
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e1) {
						}
					}
				}
				
			}
		}
		return null;
	}
	
}
