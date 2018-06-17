package com.url.shortening.model;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@SuppressWarnings("PMD.UnusedPrivateField")
public class UrlMappingHolder {
	@Getter
	private Map<String, UrlMappingDto> originUrlMappingMap = new HashMap<>();

	@Getter
	private Map<String, UrlMappingDto> shortUrlMappingMap = new HashMap<>();

}
