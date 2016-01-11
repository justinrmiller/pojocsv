package com.justinrmiller.pojocsv;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class PojoCsvWriter<T> {
    private final static String DEFAULT_SEPARATOR = ",";
    private final static String DEFAULT_LINE_BREAK = "\n";

    private final ArrayList<Field> fields;
    private final String separator;
    private final String lineBreak;
    private final Set<String> renderOnly;

    public PojoCsvWriter(Class<T> inputClass, Set<String> renderOnly) {
        this(inputClass, DEFAULT_SEPARATOR, DEFAULT_LINE_BREAK, renderOnly);
    }

    public PojoCsvWriter(Class<T> inputClass, String separator, Set<String> renderOnly) {
        this(inputClass, separator, DEFAULT_LINE_BREAK, renderOnly);
    }

    public PojoCsvWriter(Class<T> inputClass, String separator, String lineBreak, Set<String> renderOnly) {
        if (inputClass == null) throw new IllegalArgumentException();
        if (separator == null) throw new IllegalArgumentException();
        if (lineBreak == null) throw new IllegalArgumentException();

        this.separator = separator;
        this.lineBreak = lineBreak;
        this.renderOnly = renderOnly;

        Set<Class<?>> types = new HashSet<>();
        types.add(Boolean.class);
        types.add(Character.class);
        types.add(Byte.class);
        types.add(Short.class);
        types.add(Integer.class);
        types.add(Long.class);
        types.add(Float.class);
        types.add(Double.class);
        types.add(String.class);
        types.add(BigDecimal.class);

        this.fields = new ArrayList<>();

        for (Field field : inputClass.getDeclaredFields()) {
            if (!types.contains(field.getType())) {
                throw new RuntimeException("The following field is not supported: " + field.getName());
            }

            if (renderOnly == null || renderOnly.contains(field.getName())) {
                fields.add(field);
            }
        }
    }

    public void writeHeader(Writer writer) throws IOException {
        if (writer == null) throw new IllegalArgumentException();

        writeHeader(writer, new HashMap<String, String>());
    }

    public void writeHeader(Writer writer, Map<String, String> overrides) throws IOException {
        if (writer == null) throw new IllegalArgumentException();
        if (overrides == null) throw new IllegalArgumentException();

        for (int i = 0; i < fields.size(); ++i) {
            fields.get(i).setAccessible(true);
            final String headerLabel = overrides.getOrDefault(fields.get(i).getName(), fields.get(i).getName());
            writer.write(headerLabel);
            if (i < fields.size() - 1) {
                writer.write(separator);
            }
        }

        writer.write(lineBreak);
    }

    public void writeLine(Writer writer, T input) throws IllegalAccessException, IOException {
        if (writer == null) throw new IllegalArgumentException();
        if (input == null) throw new IllegalArgumentException();

        for (int i = 0; i < fields.size(); ++i) {
            writer.write(fields.get(i).get(input) == null ? "null" : fields.get(i).get(input).toString());
            if (i < fields.size() - 1) {
                writer.write(separator);
            }
        }

        writer.write(lineBreak);
    }
}
