package uns.ac.rs.mbrs.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

@RestController
public class CallPyController {

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/callPythonGet")
    public String callPythonGet() {
        String url = "http://127.0.0.1:5000/api/metamodel";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    @PostMapping("/callPythonPost")
    public String callPythonPost(@RequestBody String data) {
        String url = "http://127.0.0.1:5000/api/process";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ModelContent modelContent = new ModelContent(getModelClasses()+data);
        String json = "";
        try {
            json = objectMapper.writeValueAsString(modelContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error processing JSON";
        }
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response.getBody();
    }

    private String getModelClasses() {
        return "@Bill(make, sth)\n" +
                "class Bill{\n" +
                "  'cashier' String;\n" +
                "}\n" +
                "\n" +
                "@Bying(more)\n" +
                "class ItemWithPrice{\n" +
                "}\n" +
                "@Basket\n" +
                "class Basket{\n" +
                "}\n" +
                "@Item\n" +
                "class Item{\n" +
                "    'name' String;\n" +
                "    'quantity' int;\n" +
                "}\n" +
                "@Actionmake\n" +
                "class Action{\n" +
                "    'name' String;\n" +
                "}\n" +
                "class Address{\n" +
                "    'street' String;\n" +
                "    'number' String;\n" +
                "    'zip' String;\n" +
                "}\n" +
                "OneToOne{\n" +
                "    'Bill' to: 'Address';\n" +
                "}\n" +
                "file";
    }
}