package br.com.techthordev.backend.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base class for all integration tests.
 * Sets up the Spring context and ensures transactions are rolled back.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class BaseIntegrationTest {
}
