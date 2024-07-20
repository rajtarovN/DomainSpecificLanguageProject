package uns.ac.rs.mbrs.utils;

import org.springframework.stereotype.Component;
import uns.ac.rs.mbrs.model.Bill;
import uns.ac.rs.mbrs.model.Customer;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLClassLoader;

@Component
public class DynamicCodeExecution {

    public void execute(String textOfCode, long id, Customer person, Bill bill) throws Exception {
        try {
            String className = "GeneratedCode" + id;
            String relativePath = "/src/main/java/uns/ac/rs/mbrs/gen";
            String currentProjectDir = System.getProperty("user.dir");
            String directoryPath = currentProjectDir + relativePath;
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File sourceFile = new File(directoryPath + "/" + className + ".java");
            FileWriter writer = new FileWriter(sourceFile);
            writer.write(textOfCode);
            writer.close();
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int compilationResult = compiler.run(null, null, null, sourceFile.getAbsolutePath());
            if (compilationResult != 0) {
                throw new Exception("Compilation failed.");
            }
            File compiledClassDir = new File(currentProjectDir + "/src/main/java/");
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{compiledClassDir.toURI().toURL()});
            Class<?> cls = Class.forName("uns.ac.rs.mbrs.gen." + className, true, classLoader);
            Object instance = cls.getDeclaredConstructor().newInstance();
            cls.getMethod("execute", Customer.class, Bill.class).invoke(instance, person, bill);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Code execution error: " + e.getMessage());
        }
    }
}