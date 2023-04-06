package gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class GatlingTest extends Simulation {
    HttpProtocolBuilder httpProtocol = HttpDsl.http.baseUrl("http://tasklist-env.eba-jsesxh2v.eu-central-1.elasticbeanstalk.com");

    public GatlingTest() {
        this.setUp(
                Scenario.scn.injectOpen(
                        CoreDsl.constantUsersPerSec(10).during(10)
                )
        ).protocols(httpProtocol);
    }
}
