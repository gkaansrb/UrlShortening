package com.url.shortening.service;

import com.url.shortening.model.ConvertType;
import com.url.shortening.model.UrlMappingDto;
import com.url.shortening.model.UrlMappingHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class ShorteningEncryptConverter extends ShorteningConverter {

	@Override
	public ConvertType getConvertType() {
		return ConvertType.ENCRYPT;
	}

	@Override
	public UrlMappingDto convert(String originUrl) {

		validation(originUrl);

		String url = deleteHttp(originUrl);

		UrlMappingDto urlMappingDto = holder.getOriginUrlMappingMap().getOrDefault(url, getUrlMappingDto(url));

		return save(urlMappingDto);
	}

	private UrlMappingDto getUrlMappingDto(String url) {
		return UrlMappingDto.builder().key(generateKey()).domain(DOMAIN).originUrl(url).build();
	}

	private UrlMappingDto save(UrlMappingDto mappingDto) {
		holder.getOriginUrlMappingMap().put(mappingDto.getOriginUrl(), mappingDto);
		holder.getShortUrlMappingMap().put(mappingDto.getKey(), mappingDto);
		return mappingDto;
	}

	private String generateKey() {
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
}
