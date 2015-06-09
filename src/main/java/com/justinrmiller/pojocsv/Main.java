package com.justinrmiller.pojocsv;

import com.justinrmiller.pojocsv.pojo.User;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String [] args) throws IllegalAccessException, IOException {
        final User user1 = new User("Justin", "Miller", 10, 1.0f);
        final User user2 = new User("Megan", null, 12, 1.0f);
        final User user3 = new User("Ken", "Miller", null, 1.0f);
        final User user4 = new User("Brenda", "Miller", 10, null);

        Map<String, String> nameResolver = new HashMap<>();
        nameResolver.put("firstName", "First Name");
        nameResolver.put("lastName", "Last Name");
        nameResolver.put("age", "Age");
        nameResolver.put("score", "Score");

        PojoCsvWriter<User> csvWriter = new PojoCsvWriter<>(User.class);


        PrintStream out = System.out;
        PrintWriter pw = new PrintWriter(out);
        csvWriter.writeHeader(pw);
        csvWriter.writeHeader(pw, nameResolver);
        csvWriter.writeLine(pw, user1);
        csvWriter.writeLine(pw, user2);
        csvWriter.writeLine(pw, user3);
        csvWriter.writeLine(pw, user4);
        pw.flush();
        pw.close();
    }
}
