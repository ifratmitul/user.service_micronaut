package userservice.com.config;

import io.micronaut.context.annotation.Factory;
import io.micronaut.transaction.TransactionOperations;
import jakarta.inject.Singleton;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbc.micronaut.MicronautJdbcLockProvider;

import java.net.ConnectException;
import java.sql.Connection;

@Factory
public class ShedlockConfig {
    @Singleton
    public LockProvider lockProvider (TransactionOperations<Connection> transactionManager) {
        return new MicronautJdbcLockProvider(transactionManager);
    }
}
