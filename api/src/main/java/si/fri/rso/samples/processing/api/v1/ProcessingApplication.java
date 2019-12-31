package si.fri.rso.samples.processing.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.kumuluz.ee.discovery.annotations.RegisterService;

@RegisterService
@ApplicationPath("/v1")
public class ProcessingApplication extends Application {
}
