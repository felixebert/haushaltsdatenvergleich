package de.ifcore.hdv.converter.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import de.ifcore.hdv.converter.MinMax;
import de.ifcore.hdv.converter.data.LongValue;

public class MinMaxSerializer extends StdSerializer<MinMax> {

	public MinMaxSerializer(Class<MinMax> t) {
		super(t);
	}

	@Override
	public void serialize(MinMax value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonGenerationException {
		jgen.writeStartArray();
		for (LongValue l : value.getData()) {
			if (l.isValid())
				jgen.writeNumber(l.getValue().longValue());
			else
				jgen.writeString(l.getOrgValue());
		}
		jgen.writeEndArray();
	}
}
