package com.justinrmiller.pojocsv;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.*;

public class PojoCsvWriter<T> {
    private final static String DEFAULT_SEPARATOR = ",";
    private final static String DEFAULT_LINE_BREAK = "\n";

    private final Field [] fields;
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

        this.fields = inputClass.getDeclaredFields();
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

        for (Field field : fields) {
            if (!types.contains(field.getType())) {
                throw new RuntimeException("The following field is not supported: " + field.getName());
            }
        }
    }

    public void writeHeader(Writer writer) throws IOException {
        if (writer == null) throw new IllegalArgumentException();

        writeHeader(writer, new HashMap<>());
    }

    public void writeHeader(Writer writer, Map<String, String> overrides) throws IOException {
        if (writer == null) throw new IllegalArgumentException();
        if (overrides == null) throw new IllegalArgumentException();

        if (renderOnly != null) {
            for (int i = 0; i < fields.length; ++i) {
                fields[i].setAccessible(true);
                if (renderOnly.contains(fields[i].getName())) {
                    final String headerLabel = overrides.getOrDefault(fields[i].getName(), fields[i].getName());
                    writer.write(headerLabel);
                    if (i < fields.length - 1) {
                        writer.write(separator);
                    }
                }
            }
        } else {
            for (int i = 0; i < fields.length; ++i) {
                fields[i].setAccessible(true);
                final String headerLabel = overrides.getOrDefault(fields[i].getName(), fields[i].getName());
                writer.write(headerLabel);
                if (i < fields.length - 1) {
                    writer.write(separator);
                }
            }
        }

        writer.write(lineBreak);
    }

    public void writeLine(Writer writer, T input) throws IllegalAccessException, IOException {
        if (writer == null) throw new IllegalArgumentException();
        if (input == null) throw new IllegalArgumentException();

        if (renderOnly != null) {
            for (int i = 0; i < fields.length; ++i) {
                fields[i].setAccessible(true);
                    if (renderOnly.contains(fields[i].getName())) {
                    writer.write(fields[i].get(input) == null ? "null" : fields[i].get(input).toString());
                    if (i < fields.length - 1) {
                        writer.write(separator);
                    }
                }
            }
        } else {
            for (int i = 0; i < fields.length; ++i) {
                writer.write(fields[i].get(input) == null ? "null" : fields[i].get(input).toString());
                if (i < fields.length - 1) {
                    writer.write(separator);
                }
            }
        }

        writer.write(lineBreak);
    }
}
