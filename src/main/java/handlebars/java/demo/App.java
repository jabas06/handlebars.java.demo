package handlebars.java.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.JsonNodeValueResolver;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import org.apache.commons.io.FilenameUtils;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {
        java.util.Properties props = new Properties();
        props.load(App.class.getResourceAsStream("/application.properties"));

        String templatesPath = props.getProperty("templatesPath");
        String templateFile = props.getProperty("templateFile");
        String payloadFile = props.getProperty("payloadFile");
        String outFile = props.getProperty("outFile");

        apply(templatesPath, templateFile, payloadFile, outFile);
    }

    private static void apply(String templatesPath, String templateFile, String jsonFile, String outputFile)
            throws IOException {

        String templateName = FilenameUtils.getBaseName(templateFile);
        String templateExt = "." + FilenameUtils.getExtension(templateFile);

        String filePath = Paths.get(new File("").getAbsolutePath(), templatesPath).toString();
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath, jsonFile)));
        JsonNode model = new ObjectMapper().readValue(jsonContent, JsonNode.class);

        TemplateLoader loader = new FileTemplateLoader(filePath, templateExt);
        Handlebars handlebars = new Handlebars(loader);
        registerHelpers(handlebars);

        Template template = handlebars.compile(templateName);
        Context context = Context.newBuilder(model).resolver(JsonNodeValueResolver.INSTANCE).build();

        String templateString = template.apply(context);

        String outputPath = Paths.get(filePath, outputFile).toString();
        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            fileWriter.write(templateString);
        }

        System.out.println("SUCCESS: File " + outputPath + " has been rendered.");
    }

    private static void registerHelpers(Handlebars handlebars) {
        handlebars.registerHelpers(ConditionalHelpers.class);
        // handlebars.registerHelpers(StringHelpers.class);
    }
}
