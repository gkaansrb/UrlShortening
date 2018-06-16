package com.url.shortening.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlMappingDto {

	private String key;

	private String originUrl;

	private String shorteningUrl;
}
