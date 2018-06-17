package com.url.shortening.service;

import com.url.shortening.exception.ShorteningConvertException;
import com.url.shortening.model.ConvertType;
import com.url.shortening.model.UrlMappingDto;
import com.url.shortening.model.UrlMappingHolder;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ShorteningConverter {

	@Autowired
	protected UrlMappingHolder holder;

	static final int URL_LENGTH = 8;

	static final int BASE = 62;

	static final String DOMAIN = "https://localhost:8080/";

	static final char[] TABLE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

	abstract ConvertType getConvertType();

	abstract UrlMappingDto convert(String url);

	void validation(String url) {
		if (url == null || url.length() < 1) {
			throw new ShorteningConvertException("변경할 URL 을 입력 바랍니다");
		}
	}

	String deleteHttp(String url) {
		return url.replace("https://", "")
			.replace("https:/", "")
			.replace("http://", "")
			.replace("http:/", "");
	}
}
