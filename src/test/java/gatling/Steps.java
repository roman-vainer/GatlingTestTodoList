package gatling;


import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.http.HttpDsl;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.jdbc.JdbcDsl.jdbcFeeder;

public class Steps {

    private static final String LOCAL_DB = "localhost";
    private static final String AWS_DB = "todolistexample-env.eba-2ekaiff6.eu-central-1.elasticbeanstalk.com";
//    private static final String AWS_DB = "http://52.58.56.29";

    public static ChainBuilder request_1 = CoreDsl.exec(
            HttpDsl.http("Creating")
                    .post("/todo?locale=en")
                    .header("Content-Type", "application/json")
                    .body(StringBody("{\"action\": \"any\",\"plannedTime\":\"2023-10-10\",\"status\":\"PLANNED\"}"))
                    .basicAuth("Admin", "admin")
                    .check(HttpDsl.status().is(201))
    );

    private static final FeederBuilder<Object> feeder = jdbcFeeder(
            "jdbc:h2:tcp://" + AWS_DB + ":9092/mem:testdb;DB_CLOSE_DELAY=-1",
            "sa",
            "",
            "SELECT ID FROM TASK_ENTITY");

    public static ChainBuilder request_2 = CoreDsl.feed(feeder).exec(
            HttpDsl.http("Changing")
                    .put("/todo/#{ID}?locale=en")
                    .header("Content-Type", "application/json")
                    .body(StringBody("{\"status\":\"CANCELLED\"}"))
                    .basicAuth("Admin", "admin")
                    .check(HttpDsl.status().is(200))
    );

    public static ChainBuilder request_3 = CoreDsl.feed(feeder).exec(
            HttpDsl.http("Getting")
                    .get("/todo/#{ID}?locale=en")
                    .basicAuth("Admin", "admin")
                    .check(HttpDsl.status().is(200))
    );

    public static ChainBuilder request_4 = CoreDsl.feed(feeder).exec(
            HttpDsl.http("Deleting")
                    .delete("/todo/#{ID}?locale=en")
                    .basicAuth("Admin", "admin")
                    .check(HttpDsl.status().is(200))
    );
}
