package com.sil.bejpa.common.constant;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantsStatic {

	@Value("${custom.format.datetime}") private String FORMAT_DATETIME;
	public static String format_datetime;
	
	@PostConstruct // 메서드로 static 변수에 할당
	public void init() {
		format_datetime = FORMAT_DATETIME;
	}
	
}
