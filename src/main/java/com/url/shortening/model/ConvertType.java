package com.url.shortening.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConvertType {
	ENCRYPT("축약하기"),
	DECRYPT("해제하기");

	private String description;
}
