package de.ifcore.hdv.converter.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import de.ifcore.hdv.converter.data.AccountValue;

public class AccountValueSerializer extends StdSerializer<AccountValue> {

	public AccountValueSerializer(Class<AccountValue> clazz) {
		super(clazz);
	}

	@Override
	public void serialize(AccountValue value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonGenerationException {
		if (value.getValue() == null)
			jgen.writeNull();
		else
			jgen.writeNumber(value.getValue());
	}
}
