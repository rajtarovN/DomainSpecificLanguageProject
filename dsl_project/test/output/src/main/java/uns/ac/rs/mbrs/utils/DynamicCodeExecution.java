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
            // Naziv klase, pretpostavlja se da je naziv klase "HelloWorld"
            String className = "GeneratedCode"+id;

            // Zapišite kod u Java datoteku
            File sourceFile = new File(className + ".java");
            FileWriter writer = new FileWriter(sourceFile);
            writer.write(textOfCode);
            writer.close();

            // Dohvatite kompajler
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            // Kompajlirajte kod
            int compilationResult = compiler.run(null, null, null, sourceFile.getAbsolutePath());
            if (compilationResult != 0) {
                throw new Exception("Compilation failed.");
            }

            // Učitajte kompajliranu klasu
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File("").toURI().toURL()});
            Class<?> cls = Class.forName(className, true, classLoader);

            // Stvorite instancu klase
            Object instance = cls.getDeclaredConstructor().newInstance();

            // Pozovite execute metodu sa parametrom
            cls.getMethod("execute", Person.class, Bill.class).invoke(instance, person, bill);

            throw new Exception("Execution successful.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Code execution error: " + e.getMessage());
        }
    }
}
