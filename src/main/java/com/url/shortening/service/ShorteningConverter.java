package com.url.shortening.service;

import com.url.shortening.exception.ShorteningConvertException;
import com.url.shortening.model.ConvertType;
import com.url.shortening.model.UrlMappingDto;
import javafx.util.Pair;

public interface ShorteningConverter {

	String PARSER = ":/";

	String DEFAULT_PROTOCOL = "https://";

	ConvertType getConvertType();

	UrlMappingDto convert(String url);

	default void validation(String url) {
		if (url == null || url.length() < 1) {
			throw new ShorteningConvertException("변경할 URL 을 입력 바랍니다");
		}
	}

	default Pair<String, String> parser(String url) {
		if (url.contains(PARSER)) {
			String[] split = url.split(PARSER);
			return new Pair<>(split[0] + PARSER, split[1]);
		} else {
			return new Pair<>(DEFAULT_PROTOCOL, url);
		}
	}
}
