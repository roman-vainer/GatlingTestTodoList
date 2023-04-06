package gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;

public class Scenario {

    public static ScenarioBuilder scn = CoreDsl.scenario("testTodo")
            .exec(Steps.request_1)
            .pause(5)
            .exec(Steps.request_2);
}
