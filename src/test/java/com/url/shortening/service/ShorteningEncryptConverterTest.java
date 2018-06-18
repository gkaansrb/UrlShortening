package com.url.shortening.service;

import com.url.shortening.model.UrlMappingDto;
import com.url.shortening.model.UrlMappingHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ShorteningEncryptConverterTest {

	private ShorteningEncryptConverter converter = new ShorteningEncryptConverter();

	private List<String> duplicationUrlList;
	private List<String> testUrlList;

	private String duplicationUrl = "kakaoPay.com";

	@Before
	public void setUp() throws Exception {
		converter.holder = new UrlMappingHolder();
		testUrlList = new ArrayList<>();
		duplicationUrlList = new ArrayList<>();

		testUrlList.add("http://kakao.com");
		testUrlList.add("http://naver.com");
		testUrlList.add(duplicationUrl);

		duplicationUrlList.add("http://" + duplicationUrl);
		duplicationUrlList.add("http:/" + duplicationUrl);
		duplicationUrlList.add("https://" + duplicationUrl);
		duplicationUrlList.add("https:/" + duplicationUrl);

	}

	@Test
	public void convert() throws Exception {
		HashSet<String> shortUrlSet = new HashSet<>();
		for (String url : testUrlList) {
			shortUrlSet.add(converter.convert(url).getShortUrl());
		}
		Assert.assertEquals(testUrlList.size(), shortUrlSet.size());
	}

	@Test
	public void duplicationUrlListToSameShortUrl() throws Exception {
		final UrlMappingDto dto = converter.convert(duplicationUrlList.get(0));

		for (String url : duplicationUrlList) {
			UrlMappingDto convert = converter.convert(url);
			Assert.assertEquals(duplicationUrl, converter.deleteHttp(convert.getOriginUrl()));
			Assert.assertEquals(dto.getShortUrl(), convert.getShortUrl());
			Assert.assertEquals(dto.getKey(), convert.getKey());
		}

		Assert.assertTrue(converter.holder.getShortUrlMappingMap().size() == 1);
		Assert.assertTrue(converter.holder.getShortUrlMappingMap().size() == 1);
	}
}