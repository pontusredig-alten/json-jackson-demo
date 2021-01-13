import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

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
//        demo.jsonFileToJavaObject();
//        demo.jsonFileToJsonNode();
//        demo.jsonFileThroughStreaming();
        demo.jsonGenerator();

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

    public void jsonFileToJsonNode() throws IOException {
        JsonNode node = mapper.readTree(JSON_FILE);

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String fieldName = field.getKey();
            JsonNode childNode = field.getValue();
            System.out.println("KEY: " + fieldName);
            System.out.println("VALUE: " + childNode);
        }

        String phoneNumber = node.get("phone").asText();
        System.out.println("PHONE NUMBER: " + phoneNumber);

//        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
//        System.out.println("JSON NODE: " + writer.writeValueAsString(node));

    }

    public void jsonFileThroughStreaming() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(JSON_FILE);
        JsonToken token;

        while ((token = parser.nextToken()) != null) {
//            System.out.println(token);
//            System.out.println(parser.getText());

            if (token.isScalarValue()) {
                String currentName = parser.currentName();
                if (currentName != null) {
                    String text = parser.getText();
                    System.out.println(currentName + " : " + text);
                }
            }
        }
    }

    public void jsonGenerator() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(System.out);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("förnamn", userObject.getFirstName());
        jsonGenerator.writeStringField("efternamn", userObject.getLastName());
        jsonGenerator.writeEndObject();
        jsonGenerator.flush();
        jsonGenerator.close();

    }


}
