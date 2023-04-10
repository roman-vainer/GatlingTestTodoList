package gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.global;

public class GatlingTest extends Simulation {

    private static final String LOCAL_URL = "http://localhost:5000";
    private static final String AWS_URL = "http://todolistexample-env.eba-2ekaiff6.eu-central-1.elasticbeanstalk.com";

    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl(AWS_URL);

    ScenarioBuilder scn1 = CoreDsl.scenario("scenario_1")
            .exec(Steps.request_1);

    ScenarioBuilder scn2 = CoreDsl.scenario("scenario_2")
            .exec(Steps.request_2);

    ScenarioBuilder scn3 = CoreDsl.scenario("scenario_2")
            .exec(Steps.request_3)
            .pause(1)
            .exec(Steps.request_4);


    public GatlingTest() {
        this.setUp(
                        scn1.injectOpen(CoreDsl.constantUsersPerSec(10).during(30)))
//                        scn3.injectOpen(CoreDsl.constantUsersPerSec(10).during(10)))
                .protocols(httpProtocol)
                .assertions(
//                        global().responseTime().max().lt(10000),
                        global().successfulRequests().percent().gt(80.0));
    }
}
