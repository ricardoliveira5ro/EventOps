package com.event.ops.event.collector;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
class EventCollectorApplicationTest {

    @Test
    void mainMethodShouldRunWithoutErrors() throws IOException {
        Path envDir = Paths.get("event-collector");
        Files.createDirectories(envDir);
        Path envFile = envDir.resolve(".env");

        Files.write(envFile, List.of(
            "DB_USERNAME=event-ops-test",
            "DB_PASSWORD=superadminunittests",
            "KAFKA_USERNAME=test_user",
            "KAFKA_PASSWORD=test_pass"
        ));

        EventCollectorApplication.main(new String[]{});
    }
}
