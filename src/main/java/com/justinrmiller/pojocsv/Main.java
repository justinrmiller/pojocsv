package com.justinrmiller.pojocsv;

import com.justinrmiller.pojocsv.pojo.User;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String [] args) throws IllegalAccessException, IOException {
        final User user1 = new User("Justin", "Miller", new BigDecimal("10"), 1.0f);
        final User user2 = new User("Megan", null, new BigDecimal("12"), 1.0f);
        final User user3 = new User("Ken", "Miller", null, 1.0f);
        final User user4 = new User("Brenda", "Miller", new BigDecimal("15"), null);

        Map<String, String> nameResolver = new HashMap<>();
        nameResolver.put("firstName", "First Name");
        nameResolver.put("lastName", "Last Name");
        nameResolver.put("age", "Age");
        nameResolver.put("score", "Score");

        Set<String> renderOnly = new HashSet<>();
        renderOnly.add("firstName");
        renderOnly.add("lastName");
        renderOnly.add("age");

        PojoCsvWriter<User> csvWriter = new PojoCsvWriter<>(User.class, renderOnly);

        PrintStream out = System.out;
        PrintWriter pw = new PrintWriter(out);
        csvWriter.writeHeader(pw, nameResolver);
        csvWriter.writeLine(pw, user1);
        csvWriter.writeLine(pw, user2);
        csvWriter.writeLine(pw, user3);
        csvWriter.writeLine(pw, user4);
        pw.flush();
        pw.close();
    }
}
