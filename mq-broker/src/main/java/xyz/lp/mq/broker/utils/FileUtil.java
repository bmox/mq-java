package xyz.lp.mq.broker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.lp.mq.broker.model.TopicModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class FileUtil {

    public static String readFromFile(String path) {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            while (in.ready()) {
                sb.append(in.readLine());
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void overwriteFile(String path, String content) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        String path = "/Users/ruifeng/bmox/mq-java/broker/config/topic-info.json";
        String content = readFromFile(path);
        ObjectMapper objectMapper = new ObjectMapper();
        List<TopicModel> topicModels = objectMapper.readValue(content, new TypeReference<List<TopicModel>>() {
        });
        System.out.println(topicModels);
    }

}
