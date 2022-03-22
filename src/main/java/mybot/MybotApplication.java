package mybot;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Objects;

@SpringBootApplication
//@PropertySource("classpath:boss.yml")
public class MybotApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws IOException {
//        ClassLoader classLoader = MybotApplication.class.getClassLoader();
        ClassPathResource classPathResource = new ClassPathResource("serviceAccount.json");
//        File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccount.json")).getFile());
        InputStream serviceAccount = classPathResource.getInputStream();
//        FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://furseal-f1362-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();
        if (!(FirebaseApp.getApps().size() >0)) {
            FirebaseApp.initializeApp(options);
        }else {
            FirebaseApp.getApps();
        }

        SpringApplication.run(MybotApplication.class, args);
    }

}
