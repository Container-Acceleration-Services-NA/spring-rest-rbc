package hello;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.metrics.instrument.Counter;
import org.springframework.metrics.instrument.MeterRegistry;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class GreetingController {

    private static final String template = "%s, %s, %s!";
    private final Counter counter;

    @Value("${greeting.prefix}")
    private String msgPrefix;

    @Value("${greeting.suffix}")
    private String msgSuffix;

    public GreetingController(MeterRegistry registry) {
       counter = registry.counter("greeting_counter");
    }

    @RequestMapping(value = "/v1/greeting" ,  method = RequestMethod.GET)
    @ApiOperation(value = "Generate a greeting for a given name")
    public Greeting greeting(@RequestParam(value="name", defaultValue="Whats up doc") String name) {
        counter.increment();
        return new Greeting((int)counter.count(),
                            String.format(template, msgPrefix, name, msgSuffix));
    }

    @GetMapping("/docs")
    public RedirectView docs() {
        return new RedirectView("swagger-ui.html");
    }

    @RequestMapping(value = "/v1/version",  method = RequestMethod.GET)
    @ApiOperation(value = "List current app version.")
    public Version version() {
	Version appV = new Version();
	System.out.println("Ver:" + appV.getVersion());
        return appV;
    }

    @RequestMapping(value = "/v1/hostinfo",  method = RequestMethod.GET)
    @ApiOperation(value = "List host name running the sample application.")
    public HostInfo hostinfo() throws IOException {
      return new HostInfo();
    }
    
    @RequestMapping(value = "/v1/envinfo" ,  method = RequestMethod.GET)
    @ApiOperation(value = "Display environment variables.")
    public EnvInfo envinfo(@RequestParam(value="filter", defaultValue="*") String filter) throws IOException {
      return new EnvInfo(filter);
    }
    
}
