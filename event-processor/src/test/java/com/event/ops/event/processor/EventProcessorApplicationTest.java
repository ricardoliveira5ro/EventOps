package com.event.ops.event.processor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
public class EventProcessorApplicationTest {

    @Test
    void mainMethodShouldRunWithoutErrors() throws IOException {
        Path envDir = Paths.get("event-processor");
        Files.createDirectories(envDir);
        Path envFile = envDir.resolve(".env");

        Files.write(envFile, List.of(
                "DB_USERNAME=event-ops-test",
                "DB_PASSWORD=superadminunittests",
                "KAFKA_USERNAME=test_user",
                "KAFKA_PASSWORD=test_pass",
                "REDIS_HOST=redis-host-test",
                "REDIS_USERNAME=redis-user-test",
                "REDIS_PASSWORD=redis-pass-test"
        ));

        EventProcessorApplication.main(new String[]{});
    }
}
