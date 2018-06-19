package com.url.shortening.service;

import com.url.shortening.exception.ShorteningConvertException;
import com.url.shortening.model.UrlMappingDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ShorteningConverterTest {

	private ShorteningConverter converter = new ShorteningConverter();

	private List<String> duplicationUrlList;
	private List<String> testUrlList;

	private String duplicationUrl = "kakaoPay.com";

	@Before
	public void setUp() throws Exception {
		testUrlList = new ArrayList<>();
		duplicationUrlList = new ArrayList<>();

		testUrlList.add("kakao.com");
		testUrlList.add("naver.com");
		testUrlList.add(duplicationUrl);

		duplicationUrlList.add("http://" + duplicationUrl);
		duplicationUrlList.add("http:/" + duplicationUrl);
		duplicationUrlList.add("https://" + duplicationUrl);
		duplicationUrlList.add("https:/" + duplicationUrl);
	}

	@Test
	public void convertAndFind() throws Exception {
		HashSet<String> shortUrlSet = new HashSet<>();
		for (String url : testUrlList) {
			shortUrlSet.add(converter.convert(url).getShortUrl());
		}
		Assert.assertEquals(testUrlList.size(), shortUrlSet.size());
		for (String shortUrl : shortUrlSet) {
			UrlMappingDto byShortUrl = converter.findByShortUrl(shortUrl.replace("http://localhost:8080/", ""));
			Assert.assertTrue(testUrlList.stream().anyMatch(each -> byShortUrl.getOriginUrl().equals(each)));
		}
	}

	@Test
	public void duplicationUrlListToSameShortUrl() throws Exception {
		final UrlMappingDto dto = converter.convert(duplicationUrlList.get(0));

		for (String url : duplicationUrlList) {
			UrlMappingDto convert = converter.convert(url);
			Assert.assertEquals(dto.getShortUrl(), convert.getShortUrl());
			Assert.assertEquals(dto.getKey(), convert.getKey());
		}
	}

	@Test(expected = ShorteningConvertException.class)
	public void convert_exception() throws Exception {
		converter.findByShortUrl("");
	}

	@Test(expected = ShorteningConvertException.class)
	public void convert_not_found() throws Exception {
		converter.findByShortUrl("abcd");
	}
}