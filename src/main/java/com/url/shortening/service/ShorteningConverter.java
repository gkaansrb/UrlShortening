package com.url.shortening.service;

import com.url.shortening.exception.ShorteningConvertException;
import com.url.shortening.model.UrlMappingDto;
import com.url.shortening.model.UrlMappingHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ShorteningConverter {

	private static final int URL_LENGTH = 8;
	private static final int BASE = 62;
	private static final String DOMAIN = "http://localhost:8080/";
	private static final char[] TABLE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	private UrlMappingHolder holder;

	public ShorteningConverter() {
		this.holder = new UrlMappingHolder();
	}

	private void validation(String url) {
		if (url == null || url.length() < 1) {
			throw new ShorteningConvertException("변경할 URL 을 입력 바랍니다");
		}
	}

	private String deleteHttp(String url) {
		return url.replace("https://", "")
			.replace("https:/", "")
			.replace("http://", "")
			.replace("http:/", "");
	}

	public UrlMappingDto convert(String originUrl) {

		validation(originUrl);

		String url = deleteHttp(originUrl);

		UrlMappingDto urlMappingDto = holder.getOriginUrlMappingMap().getOrDefault(url, getUrlMappingDto(url));

		return save(urlMappingDto);
	}

	private UrlMappingDto getUrlMappingDto(String url) {
		return UrlMappingDto.builder().key(buildRandomUrl()).domain(DOMAIN).originUrl(url).build();
	}

	private UrlMappingDto save(UrlMappingDto mappingDto) {
		holder.getOriginUrlMappingMap().put(mappingDto.getOriginUrl(), mappingDto);
		holder.getShortUrlMappingMap().put(mappingDto.getKey(), mappingDto);
		return mappingDto;
	}

	private String buildRandomUrl() {
		Random random = new Random();
		StringBuilder generateKey;
		do {
			generateKey = new StringBuilder();
			for (int index = 0; index <= URL_LENGTH; index++) {
				generateKey.append(TABLE[random.nextInt(BASE)]);
			}
		} while (holder.getShortUrlMappingMap().containsKey(generateKey.toString()));

		return generateKey.toString();
	}

	public UrlMappingDto findByShortUrl(String shortURL) {

		validation(shortURL);

		return Optional.ofNullable(holder.getShortUrlMappingMap().get(getUrl(shortURL)))
			.orElseThrow(() -> new ShorteningConvertException("매핑되는 축약 URL 이 없습니다."));
	}

	private String getUrl(String shortURL) {
		return shortURL.replace(DOMAIN, "");
	}
}
