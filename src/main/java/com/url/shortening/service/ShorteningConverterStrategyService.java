package com.url.shortening.service;

import com.url.shortening.model.ConvertType;
import com.url.shortening.model.UrlMappingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class ShorteningConverterStrategyService {

	private Map<ConvertType, ShorteningConverter> converterMap = new EnumMap<>(ConvertType.class);

	@Autowired
	private List<ShorteningConverter> converterList;

	@PostConstruct
	public void setUp() {
		for (ShorteningConverter each : converterList) {
			converterMap.put(each.getConvertType(), each);
		}
	}

	private ShorteningConverter get(ConvertType convertType) {
		return converterMap.get(convertType);
	}

	public UrlMappingDto convert(ConvertType convertType, String url) {
		return get(convertType).convert(url);
	}
}
