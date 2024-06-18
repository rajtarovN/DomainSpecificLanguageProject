package uns.ac.rs.mbrs.utils;

import org.springframework.stereotype.Component;
import uns.ac.rs.mbrs.model.Bill;
import uns.ac.rs.mbrs.model.Person;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLClassLoader;

@Component
public class DynamicCodeExecution {

    public void execute(String textOfCode, long id, Person person, Bill bill) throws Exception {
        try {
            // Class name
            String className = "GeneratedCode" + id;

            // Path for the generated source file
            String relativePath = "/src/main/java/uns/ac/rs/mbrs/gen"; // Ensure it's a forward slash

            // Current project directory
            String currentProjectDir = System.getProperty("user.dir");
            String directoryPath = currentProjectDir + relativePath;
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }
            File sourceFile = new File(directoryPath + "/" + className + ".java");
            FileWriter writer = new FileWriter(sourceFile);
            writer.write(textOfCode);
            writer.close();

            // Get the Java compiler
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            // Compile the source file
            int compilationResult = compiler.run(null, null, null, sourceFile.getAbsolutePath());
            if (compilationResult != 0) {
                throw new Exception("Compilation failed.");
            }

            // Path to the compiled classes (should point to the root of your package structure)
            File compiledClassDir = new File(currentProjectDir + "/src/main/java/");
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{compiledClassDir.toURI().toURL()});
            Class<?> cls = Class.forName("uns.ac.rs.mbrs.gen." + className, true, classLoader);

            // Create an instance of the class
            Object instance = cls.getDeclaredConstructor().newInstance();

            // Invoke the execute method
            cls.getMethod("execute", Person.class, Bill.class).invoke(instance, person, bill);

//            throw new Exception("Execution successful.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Code execution error: " + e.getMessage());
        }
    }
}
