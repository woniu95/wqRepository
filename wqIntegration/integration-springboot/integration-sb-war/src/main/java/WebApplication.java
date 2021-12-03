import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @PackageName:PACKAGE_NAME
 * @ClassName:WebApplication
 * @Description:
 * @author: wq
 * @date 2021/11/10 22:32
 */
@ComponentScan("com.wq")
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
