package hello;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Process;
import java.lang.Runtime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

public class Version {

   @Value("${application.version}")
   private String appVersion;

   public String getVersion() {
	   return "v1.2";
   }

}
