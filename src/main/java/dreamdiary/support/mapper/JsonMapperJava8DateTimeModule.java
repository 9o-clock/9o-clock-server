package dreamdiary.support.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class JsonMapperJava8DateTimeModule extends SimpleModule {
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.KOREAN);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.KOREAN);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT, Locale.KOREAN);
    public JsonMapperJava8DateTimeModule() {
        /*LocalTime Format*/
        this.addSerializer(LocalTime.class, new LocalTimeJsonSerializer());
        this.addDeserializer(LocalTime.class, new LocalTimeJsonDeserializer());
        /*LocalDate Format*/
        this.addSerializer(LocalDate.class, new LocalDateJsonSerializer());
        this.addDeserializer(LocalDate.class, new LocalDateJsonDeserializer());
        /*LocalDateTime Format*/
        this.addSerializer(LocalDateTime.class, new LocalDateTimeJsonSerializer());
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeJsonDeserializer());
    }

    private static class LocalTimeJsonSerializer extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(TIME_FORMATTER.format(value));
        }
    }
    private static class LocalTimeJsonDeserializer extends JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalTime.parse(p.getValueAsString(), TIME_FORMATTER);
        }
    }

    private static class LocalDateJsonSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(DATE_FORMATTER.format(value));
        }
    }
    private static class LocalDateJsonDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDate.parse(p.getValueAsString(), DATE_FORMATTER);
        }
    }

    private static class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(DATE_TIME_FORMATTER.format(value));
        }
    }
    private static class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDateTime.parse(p.getValueAsString(), DATE_TIME_FORMATTER);
        }
    }
}
