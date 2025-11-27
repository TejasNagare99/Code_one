package com.excel.validations;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StrictDateDeserializer extends JsonDeserializer<Date> {
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
	private static final Pattern DATE_PATTERN = Pattern
			.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}(Z|[+-]\\d{2}:\\d{2})$");
	private final SimpleDateFormat dateFormat;

	public StrictDateDeserializer() {
		this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
		this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.dateFormat.setLenient(false);
	}

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// Check if the date string matches the exact pattern
		String dateStr = p.getText().trim();
		if (!DATE_PATTERN.matcher(dateStr).matches()) {
			throw new IOException("Invalid date format: " + dateStr);
		}
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			throw new IOException("Invalid date format: " + dateStr, e);
		}
	}

}
