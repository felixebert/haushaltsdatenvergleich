package de.ifcore.hdv.converter.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import de.ifcore.hdv.converter.MinMax;

public class MinMaxSerializer extends StdSerializer<MinMax> {

	public MinMaxSerializer(Class<MinMax> t) {
		super(t);
	}

	@Override
	public void serialize(MinMax value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonGenerationException {
		jgen.writeStartArray();
		for (Long l : value.getData()) {
			if (l == null) {
				jgen.writeNull();
			}
			else
				jgen.writeNumber(l.longValue());
		}
		jgen.writeEndArray();
	}
}
