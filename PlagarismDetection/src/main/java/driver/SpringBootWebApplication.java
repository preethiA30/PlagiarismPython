package driver;

import controller.CounterController;
import controller.CourseController;
import controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Represents driver class.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = CourseController.class)
@ComponentScan(basePackageClasses = UserController.class)
@ComponentScan(basePackageClasses = XmlConfiguration.class)
@ComponentScan(basePackageClasses = CounterController.class)
@PropertySource(ignoreResourceNotFound = false, value =     "classpath:application.properties")
@EnableAutoConfiguration
@ImportResource({"classpath*:applicationContext.xml"})
public class SpringBootWebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootWebApplication.class);
    }

  /**
   * Main method that runs the driver.
   */
  public static void main(String[] args) {
    SpringApplication.run(SpringBootWebApplication.class, args);
  }


}