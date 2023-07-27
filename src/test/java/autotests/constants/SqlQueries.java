package autotests.constants;

public final class SqlQueries {

    public static final String DELETE_DUCK_FROM_TABLE_QUERY = "DELETE FROM DUCK WHERE ID=${duckId};";
    public static final String CLEAR_TABLE = "DELETE FROM DUCK;";
    public static final String SELECT_LAST_DUCK_ID_FROM_DATABASE_QUERY = "SELECT ID FROM DUCK ORDER BY ID DESC LIMIT 1;";
    public static final String SELECT_NUMBER_OF_DUCKS_FROM_TABLE = "SELECT COUNT(*) as duckcount FROM duck;";
    public static final String CREATE_TEST_DUCK_QUERY = "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
            "VALUES (${duckId}, 'orange', 100.0, 'glass', 'nya','ACTIVE');";
}