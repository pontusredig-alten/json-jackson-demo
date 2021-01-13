import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;

public class JacksonDemo {

    User userObject = new User("Kalle", "Anka", "kalle@mail.com", "0700123456",
            new Address("Paradisäppelvägen 111", "12345", "Ankeborg"));

    final File JSON_FILE = new File("user.json");
    final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        JacksonDemo demo = new JacksonDemo();
//        demo.javaObjectToJsonFile();
//        demo.javaObjectToJsonOutput();
//        demo.javaObjectToJsonString();
        demo.jsonFileToJavaObject();

    }

    public void javaObjectToJsonFile() throws IOException {
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(JSON_FILE, userObject);
    }

    public void javaObjectToJsonOutput() throws IOException {
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(System.out, userObject);
    }

    public void javaObjectToJsonString() throws IOException {
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String jsonString = writer.writeValueAsString(userObject);
        System.out.println("JSON STRING: " + jsonString);
    }

    public void jsonFileToJavaObject() throws IOException {
        User userParsedFromJson = mapper.readValue(JSON_FILE, User.class);
        System.out.println("JAVA OBJECT: " + userParsedFromJson);
    }



}
