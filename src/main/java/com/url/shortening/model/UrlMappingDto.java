package com.url.shortening.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlMappingDto {

	private String domain;

	private String key;

	private String originUrl;

	private String shortUrl;

	@Builder
	public UrlMappingDto(String domain, String key, String originUrl) {
		setDomain(domain);
		setKey(key);
		setOriginUrl(originUrl);
		setShortUrl(domain + key);
	}
}
