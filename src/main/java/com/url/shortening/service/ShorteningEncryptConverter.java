package com.url.shortening.service;

import com.url.shortening.model.ConvertType;
import com.url.shortening.model.UrlMappingDto;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShorteningEncryptConverter implements ShorteningConverter {

	@Override
	public ConvertType getConvertType() {
		return ConvertType.ENCRYPT;
	}

	@Override
	public UrlMappingDto convert(String url) {

		validation(url);

		Pair<String, String> parser = parser(url);

		return null;
	}
}
