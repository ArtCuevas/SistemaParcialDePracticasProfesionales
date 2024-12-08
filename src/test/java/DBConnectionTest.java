import com.sppp.connection.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionTest {
    @Test
    void testGetConnection_NotNull() throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        assertNotNull(connection, "La conexión no debería ser nula");
    }

    @Test
    void testSingletonInstance() throws SQLException {
        DBConnection instance1 = DBConnection.getInstance();
        DBConnection instance2 = DBConnection.getInstance();
        assertSame(instance1, instance2, "Ambas instancias deberían ser iguales (patrón Singleton)");
    }

    @Test
    void testConnectionRecreatedAfterClose() throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();

        assertNotNull(connection, "La conexión no debería ser nula");
        connection.close();

        DBConnection newConnectionInstance = DBConnection.getInstance();
        assertNotSame(connection, newConnectionInstance.getConnection(), "La conexión debería recrearse después de cerrarse");
    }

}
