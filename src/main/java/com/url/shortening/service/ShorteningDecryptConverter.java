package com.url.shortening.service;

import com.url.shortening.exception.ShorteningConvertException;
import com.url.shortening.model.ConvertType;
import com.url.shortening.model.UrlMappingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ShorteningDecryptConverter extends ShorteningConverter {

	@Override
	public ConvertType getConvertType() {
		return ConvertType.DECRYPT;
	}

	@Override
	public UrlMappingDto convert(String shortURL) {

		validation(shortURL);

		return Optional.ofNullable(holder.getShortUrlMappingMap().get(getUrl(shortURL)))
			.orElseThrow(() -> new ShorteningConvertException("매핑되는 축약 URL 이 없습니다."));
	}

	private String getUrl(String shortURL) {
		return shortURL.replace(DOMAIN, "");
	}
}
