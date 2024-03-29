package com.ares.springionic.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
			e.printStackTrace();
			return "";
		}
	}

	public static List<Integer> decodeIntList(String s) {
		return Arrays.asList(s.split(",")).stream().map(value -> Integer.parseInt(value)).collect(Collectors.toList());
	}

}
