package com.url.shortening.service;

import com.url.shortening.exception.ShorteningConvertException;
import com.url.shortening.model.UrlMappingDto;
import com.url.shortening.model.UrlMappingHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ShorteningDecryptConverterTest {

	private ShorteningDecryptConverter converter = new ShorteningDecryptConverter();

	private String testUrl = "kakaoPay.com";
	private String testShort = "https://localhost:8080/123123";
	private String key = "123123";

	@Before
	public void setUp() throws Exception {
		converter.holder = new UrlMappingHolder();

		UrlMappingDto urlMappingDto = new UrlMappingDto(ShorteningConverter.DOMAIN, key, testUrl);

		converter.holder.getShortUrlMappingMap().put(key, urlMappingDto);
		converter.holder.getShortUrlMappingMap().put("123456", urlMappingDto);
		converter.holder.getShortUrlMappingMap().put("098765", urlMappingDto);
	}

	@Test
	public void convert() throws Exception {
		UrlMappingDto convert = converter.convert(testShort);

		Assert.assertEquals(key, convert.getKey());
		Assert.assertEquals(testShort, convert.getShortUrl());
		Assert.assertEquals(testUrl, convert.getOriginUrl());
	}

	@Test(expected = ShorteningConvertException.class)
	public void convert_exception() throws Exception {
		converter.convert("");
	}

	@Test(expected = ShorteningConvertException.class)
	public void convert_not_found() throws Exception {
		converter.convert("abcd");
	}
}