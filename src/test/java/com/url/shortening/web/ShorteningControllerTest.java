package com.url.shortening.web;

import com.url.shortening.exception.ShorteningConvertException;
import com.url.shortening.model.UrlMappingDto;
import com.url.shortening.service.ShorteningConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ShorteningControllerTest {

	@InjectMocks
	private ShorteningController shorteningController;

	@Mock
	private ShorteningConverter shorteningConverter;

	UrlMappingDto urlMappingDto = new UrlMappingDto("", "keyValue", "www.kakaoPay.com");

	@Test
	public void path() throws Exception {
		Mockito.when(shorteningConverter.findByShortUrl(null)).thenThrow(new ShorteningConvertException("URL 정보가 없습니다."));
		Mockito.when(shorteningConverter.findByShortUrl(Mockito.anyString())).thenReturn(urlMappingDto);

		Assert.assertEquals("error", shorteningController.redirect(null));
		Assert.assertTrue(shorteningController.redirect("testUrl").contains("www.kakaoPay.com"));
	}

	@Test
	public void encrypt() throws Exception {
		Mockito.when(shorteningConverter.convert(null)).thenThrow(new ShorteningConvertException("URL 정보가 없습니다."));
		Mockito.when(shorteningConverter.convert(Mockito.anyString())).thenReturn(urlMappingDto);

		UrlMappingDto testUrl = shorteningController.encrypt("testUrl");
		Assert.assertEquals(urlMappingDto.getOriginUrl(), testUrl.getOriginUrl());

		try {
			shorteningController.encrypt(null);
		} catch (ShorteningConvertException e) {
			Assert.assertEquals("URL 정보가 없습니다.", e.getMessage());
		}
	}

}