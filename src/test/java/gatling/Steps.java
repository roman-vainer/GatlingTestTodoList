package gatling;


import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.http.HttpDsl;

import static io.gatling.javaapi.core.CoreDsl.StringBody;

public class Steps {
    public static ChainBuilder request_1 = CoreDsl.exec(
            HttpDsl.http("Creating")
                    .post("/todo?locale=en")
                    .header("Content-Type", "application/json")
                    .body(StringBody("{\"action\": \"any\",\"plannedTime\":\"2023-10-10\",\"status\":\"PLANNED\"}"))
                    .basicAuth("Admin", "admin")
                    .check(HttpDsl.status().is(201))
    );
    public static ChainBuilder request_2 = CoreDsl.exec(
            HttpDsl.http("Get")
                    .get("/todo")
//                    .header("Content-Type", "application/json")
//                    .body(StringBody("{\"status\":\"DONE\"}"))
                    .basicAuth("Admin", "admin")
                    .check(HttpDsl.status().is(200))
    );
}
