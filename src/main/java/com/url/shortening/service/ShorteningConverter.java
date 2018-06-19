package com.url.shortening.service;

import com.google.common.collect.Lists;
import com.url.shortening.exception.ShorteningConvertException;
import com.url.shortening.model.UrlMappingDto;
import com.url.shortening.model.UrlMappingHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ShorteningConverter {
	private UrlMappingHolder holder;

	public ShorteningConverter() {
		this.holder = new UrlMappingHolder();
	}

	private void validation(String url) {
		if (url == null || url.length() < 1) {
			throw new ShorteningConvertException("URL 정보가 없습니다.");
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
		return UrlMappingDto.builder().key(buildRandomUrl()).domain(StaticValues.DOMAIN).originUrl(url).build();
	}

	private UrlMappingDto save(UrlMappingDto mappingDto) {
		holder.getOriginUrlMappingMap().put(mappingDto.getOriginUrl(), mappingDto);
		holder.getShortUrlMappingMap().put(mappingDto.getKey(), mappingDto);
		return mappingDto;
	}

	private String buildRandomUrl() {
		Random random = new Random();
		StringBuilder key;
		do {
			key = new StringBuilder();
			for (int index = 0; index <= StaticValues.URL_LENGTH; index++) {
				key.append(StaticValues.TABLE[random.nextInt(StaticValues.BASE)]);
			}
		} while (holder.getShortUrlMappingMap().containsKey(key.toString()));

		return key.toString();
	}

	public UrlMappingDto findByShortUrl(String shortURL) {

		validation(shortURL);

		return Optional.ofNullable(holder.getShortUrlMappingMap().get(getUrl(shortURL)))
			.orElseThrow(() -> new ShorteningConvertException("매핑되는 축약 URL 이 없습니다."));
	}

	private String getUrl(String shortURL) {
		return shortURL.replace(StaticValues.DOMAIN, "");
	}

	public List<UrlMappingDto> getUrlList() {
		return Lists.newArrayList(holder.getShortUrlMappingMap().values());
	}

	private static class StaticValues {
		private static final int URL_LENGTH = 8;
		private static final int BASE = 62;
		private static final String DOMAIN = "http://localhost:8080/";
		private static final char[] TABLE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	}
}
