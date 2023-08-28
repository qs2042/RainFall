package com.qing.erp.common.module.mysql;

import com.qing.erp.common.module.mysql.constant.DbType;
import com.qing.erp.common.module.mysql.gen.jpa.GenJpa;
import com.qing.erp.common.module.mysql.pojo.ColumnMetaData;
import com.qing.erp.common.module.mysql.pojo.TableMetaData;
import com.qing.erp.common.str.StrUtil;
import lombok.SneakyThrows;
import lombok.val;

import java.sql.*;
import java.util.*;

public class MySQLClient implements IClient {
    private final Connection connection;

    public MySQLClient(Connection connection) {
        this.connection = connection;
    }

    @SneakyThrows
    public MySQLClient(String url, String username, String password) {
        this.connection = DriverManager.getConnection(url, username, password);
    }

    @SneakyThrows
    public MySQLClient(String host, int port, String database, String username, String password) {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true";
        this.connection = DriverManager.getConnection(url, username, password);
    }

    @SneakyThrows
    public void close() {
        if (connection != null) {
            connection.close();
        }
    }

    @SneakyThrows
    public DatabaseMetaData getMetaData() {
        return connection.getMetaData();
    }

    @SneakyThrows
    @Override
    public HashMap<String, Object> getDatabaseMetaDataMap() {
        val databaseMetaData = connection.getMetaData();
        return new HashMap<String, Object>() {{
            put("URL", databaseMetaData.getURL());
            put("UserName", databaseMetaData.getUserName());
            put("DatabaseProductName", databaseMetaData.getDatabaseProductName());
            put("DatabaseProductVersion", databaseMetaData.getDatabaseProductVersion());
            put("DriverName", databaseMetaData.getDriverName());
            put("DriverVersion", databaseMetaData.getDriverVersion());
            put("DriverMajorVersion", databaseMetaData.getDriverMajorVersion());
            put("DriverMinorVersion", databaseMetaData.getDriverMinorVersion());
            put("JDBCMajorVersion", databaseMetaData.getJDBCMajorVersion());
            put("JDBCMinorVersion", databaseMetaData.getJDBCMinorVersion());
            put("MaxBinaryLiteralLength", databaseMetaData.getMaxBinaryLiteralLength());
            put("MaxCatalogNameLength", databaseMetaData.getMaxCatalogNameLength());
            put("MaxCharLiteralLength", databaseMetaData.getMaxCharLiteralLength());
            put("MaxColumnNameLength", databaseMetaData.getMaxColumnNameLength());
            put("MaxColumnsInGroupBy", databaseMetaData.getMaxColumnsInGroupBy());
            put("MaxColumnsInIndex", databaseMetaData.getMaxColumnsInIndex());
            put("MaxColumnsInOrderBy", databaseMetaData.getMaxColumnsInOrderBy());
            put("MaxColumnsInSelect", databaseMetaData.getMaxColumnsInSelect());
            put("MaxColumnsInTable", databaseMetaData.getMaxColumnsInTable());
            put("MaxConnections", databaseMetaData.getMaxConnections());
            put("MaxCursorNameLength", databaseMetaData.getMaxCursorNameLength());
            put("MaxIndexLength", databaseMetaData.getMaxIndexLength());
            put("MaxProcedureNameLength", databaseMetaData.getMaxProcedureNameLength());
            put("MaxRowSize", databaseMetaData.getMaxRowSize());
            put("MaxSchemaNameLength", databaseMetaData.getMaxSchemaNameLength());
            put("MaxStatementLength", databaseMetaData.getMaxStatementLength());
            put("MaxStatements", databaseMetaData.getMaxStatements());
            put("MaxTableNameLength", databaseMetaData.getMaxTableNameLength());
            put("MaxTablesInSelect", databaseMetaData.getMaxTablesInSelect());
            put("MaxUserNameLength", databaseMetaData.getMaxUserNameLength());
//            put("NumericFunctions", databaseMetaData.getNumericFunctions());
//            put("StringFunctions", databaseMetaData.getStringFunctions());
//            put("SystemFunctions", databaseMetaData.getSystemFunctions());
//            put("TimeDateFunctions", databaseMetaData.getTimeDateFunctions());
            put("ExtraNameCharacters", databaseMetaData.getExtraNameCharacters());
            put("IdentifierQuoteString", databaseMetaData.getIdentifierQuoteString());
            put("SchemaTerm", databaseMetaData.getSchemaTerm());
            put("ProcedureTerm", databaseMetaData.getProcedureTerm());
            put("CatalogTerm", databaseMetaData.getCatalogTerm());
            put("ResultSetHoldability", databaseMetaData.getResultSetHoldability());
//            put("SQLKeywords", databaseMetaData.getSQLKeywords());
            put("SQLStateType", databaseMetaData.getSQLStateType());
            put("supportsAlterTableWithAddColumn", databaseMetaData.supportsAlterTableWithAddColumn());
            put("supportsAlterTableWithDropColumn", databaseMetaData.supportsAlterTableWithDropColumn());
            put("supportsANSI92EntryLevelSQL", databaseMetaData.supportsANSI92EntryLevelSQL());
            put("supportsANSI92FullSQL", databaseMetaData.supportsANSI92FullSQL());
            put("supportsANSI92IntermediateSQL", databaseMetaData.supportsANSI92IntermediateSQL());
            put("supportsBatchUpdates", databaseMetaData.supportsBatchUpdates());
            put("supportsCatalogsInDataManipulation", databaseMetaData.supportsCatalogsInDataManipulation());
            put("supportsCatalogsInIndexDefinitions", databaseMetaData.supportsCatalogsInIndexDefinitions());
            put("supportsCatalogsInPrivilegeDefinitions", databaseMetaData.supportsCatalogsInPrivilegeDefinitions());
            put("supportsCatalogsInProcedureCalls", databaseMetaData.supportsCatalogsInProcedureCalls());
            put("supportsCatalogsInTableDefinitions", databaseMetaData.supportsCatalogsInTableDefinitions());
            put("supportsColumnAliasing", databaseMetaData.supportsColumnAliasing());
            put("supportsConvert", databaseMetaData.supportsConvert());
            put("supportsConvert2", databaseMetaData.supportsConvert(1, 2));
            put("supportsCoreSQLGrammar", databaseMetaData.supportsCoreSQLGrammar());
            put("supportsCorrelatedSubqueries", databaseMetaData.supportsCorrelatedSubqueries());
            put("supportsDataDefinitionAndDataManipulationTransactions", databaseMetaData.supportsDataDefinitionAndDataManipulationTransactions());
            put("supportsDataManipulationTransactionsOnly", databaseMetaData.supportsDataManipulationTransactionsOnly());
            put("supportsDifferentTableCorrelationNames", databaseMetaData.supportsDifferentTableCorrelationNames());
            put("supportsExpressionsInOrderBy", databaseMetaData.supportsExpressionsInOrderBy());
            put("supportsExtendedSQLGrammar", databaseMetaData.supportsExtendedSQLGrammar());
            put("supportsFullOuterJoins", databaseMetaData.supportsFullOuterJoins());
            put("supportsGetGeneratedKeys", databaseMetaData.supportsGetGeneratedKeys());
            put("supportsGroupBy", databaseMetaData.supportsGroupBy());
            put("supportsGroupByBeyondSelect", databaseMetaData.supportsGroupByBeyondSelect());
            put("supportsGroupByUnrelated", databaseMetaData.supportsGroupByUnrelated());
            put("supportsIntegrityEnhancementFacility", databaseMetaData.supportsIntegrityEnhancementFacility());
            put("supportsLikeEscapeClause", databaseMetaData.supportsLikeEscapeClause());
            put("supportsLimitedOuterJoins", databaseMetaData.supportsLimitedOuterJoins());
            put("supportsMinimumSQLGrammar", databaseMetaData.supportsMinimumSQLGrammar());
            put("supportsMixedCaseIdentifiers", databaseMetaData.supportsMixedCaseIdentifiers());
            put("supportsMixedCaseQuotedIdentifiers", databaseMetaData.supportsMixedCaseQuotedIdentifiers());
            put("supportsMultipleOpenResults", databaseMetaData.supportsMultipleOpenResults());
            put("supportsMultipleResultSets", databaseMetaData.supportsMultipleResultSets());
            put("supportsMultipleTransactions", databaseMetaData.supportsMultipleTransactions());
            put("supportsNamedParameters", databaseMetaData.supportsNamedParameters());
            put("supportsNonNullableColumns", databaseMetaData.supportsNonNullableColumns());
            put("supportsOpenCursorsAcrossCommit", databaseMetaData.supportsOpenCursorsAcrossCommit());
            put("supportsOpenCursorsAcrossRollback", databaseMetaData.supportsOpenCursorsAcrossRollback());
            put("supportsOpenStatementsAcrossCommit", databaseMetaData.supportsOpenStatementsAcrossCommit());
            put("supportsOpenStatementsAcrossRollback", databaseMetaData.supportsOpenStatementsAcrossRollback());
            put("supportsOrderByUnrelated", databaseMetaData.supportsOrderByUnrelated());
            put("supportsOuterJoins", databaseMetaData.supportsOuterJoins());
            put("supportsPositionedDelete", databaseMetaData.supportsPositionedDelete());
            put("supportsPositionedUpdate", databaseMetaData.supportsPositionedUpdate());
            put("supportsResultSetConcurrency", databaseMetaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY));
            put("supportsResultSetHoldability", databaseMetaData.supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT));
            put("supportsResultSetType", databaseMetaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
            put("supportsSavepoints", databaseMetaData.supportsSavepoints());
            put("supportsSchemasInDataManipulation", databaseMetaData.supportsSchemasInDataManipulation());
            put("supportsSchemasInIndexDefinitions", databaseMetaData.supportsSchemasInIndexDefinitions());
            put("supportsSchemasInPrivilegeDefinitions", databaseMetaData.supportsSchemasInPrivilegeDefinitions());
            put("supportsSchemasInProcedureCalls", databaseMetaData.supportsSchemasInProcedureCalls());
            put("supportsSchemasInTableDefinitions", databaseMetaData.supportsSchemasInTableDefinitions());
            put("supportsSelectForUpdate", databaseMetaData.supportsSelectForUpdate());
            put("supportsStatementPooling", databaseMetaData.supportsStatementPooling());
            put("supportsStoredFunctionsUsingCallSyntax", databaseMetaData.supportsStoredFunctionsUsingCallSyntax());
            put("supportsStoredProcedures", databaseMetaData.supportsStoredProcedures());
            put("supportsSubqueriesInComparisons", databaseMetaData.supportsSubqueriesInComparisons());
            put("supportsSubqueriesInExists", databaseMetaData.supportsSubqueriesInExists());
            put("supportsSubqueriesInIns", databaseMetaData.supportsSubqueriesInIns());
            put("supportsSubqueriesInQuantifieds", databaseMetaData.supportsSubqueriesInQuantifieds());
            put("supportsTableCorrelationNames", databaseMetaData.supportsTableCorrelationNames());
            put("supportsTransactionIsolationLevel", databaseMetaData.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED));
            put("supportsTransactions", databaseMetaData.supportsTransactions());
            put("supportsUnion", databaseMetaData.supportsUnion());
            put("supportsUnionAll", databaseMetaData.supportsUnionAll());
            put("usesLocalFilePerTable", databaseMetaData.usesLocalFilePerTable());
            put("usesLocalFiles", databaseMetaData.usesLocalFiles());
        }};
    }

    @SneakyThrows
    @Override
    public List<String> getDatabases() {
        List<String> databases = new ArrayList<>();
        try (ResultSet rs = connection.getMetaData().getCatalogs()) {
            while (rs.next()) {
                databases.add(rs.getString("TABLE_CAT"));
            }
        }
        return databases;
    }

    @SneakyThrows
    @Override
    public List<String> getTableNames(String databaseName) {
        List<String> tableNames = new ArrayList<>();
        try (ResultSet rs = connection.getMetaData().getTables(databaseName, null, null, new String[]{"TABLE"})) {
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        }
        return tableNames;
    }

    @SneakyThrows
    @Override
    public List<TableMetaData> getTableMetaData(String databaseName) {
        List<TableMetaData> tableMetaDatas = new ArrayList<>();
        try (ResultSet rs = connection.getMetaData().getTables(databaseName, null, null, new String[]{"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String tableCat = rs.getString("TABLE_CAT");
                String tableSchem = rs.getString("TABLE_SCHEM");
                String tableType = rs.getString("TABLE_TYPE");

                String selfReferencingColName = rs.getString("SELF_REFERENCING_COL_NAME");
                String refGeneration = rs.getString("REF_GENERATION");
                String remarks = rs.getString("REMARKS");

                List<ColumnMetaData> columnMetaDatas = getColumnMetaData(databaseName, tableName);
                tableMetaDatas.add(new TableMetaData(
                        tableName, tableType,
                        tableSchem, tableCat, selfReferencingColName,
                        remarks, refGeneration,
                        columnMetaDatas
                ));
            }
        }
        return tableMetaDatas;
    }

    @SneakyThrows
    @Override
    public List<ColumnMetaData> getColumnMetaData(String databaseName, String tableName) {
        List<ColumnMetaData> columnMetaDatas = new ArrayList<>();
        try (ResultSet rs = connection.getMetaData().getColumns(databaseName, null, tableName, null)) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String typeName = rs.getString("TYPE_NAME");
                String remarks = rs.getString("REMARKS");
                int columnSize = rs.getInt("COLUMN_SIZE");
                boolean isNullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
                columnMetaDatas.add(new ColumnMetaData(columnName, typeName, remarks, columnSize, isNullable));
            }
        }
        return columnMetaDatas;
    }

    @SneakyThrows
    @Override
    public void execute(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    @SneakyThrows
    @Override
    public ResultSet executeQuery(String sql) {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    @SneakyThrows
    @Override
    public int executeUpdate(String sql) {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }


    //
    @SneakyThrows
    public String getTableEntity(String databaseName, String tableName) {
        DatabaseMetaData metaData = connection.getMetaData();

        // 获取列名, 类型, 注释
        ResultSet rs = metaData.getColumns(null, databaseName, tableName, null);
        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String> columnTypes = new ArrayList<>();
        ArrayList<Integer> columnTypeValues = new ArrayList<>();
        ArrayList<String> columnComments = new ArrayList<>();
        while (rs.next()) {
            columnNames.add(rs.getString("COLUMN_NAME"));
            columnTypes.add(rs.getString("TYPE_NAME"));
            columnTypeValues.add(rs.getInt("COLUMN_SIZE"));
            columnComments.add(rs.getString("REMARKS"));
        }

        // 要添加的库
        ArrayList<String> imports = new ArrayList<>();
        imports.add("import lombok.Data;");
        imports.add("import lombok.NoArgsConstructor;");
        imports.add("import lombok.AllArgsConstructor;");

        // 创建Java Bean类名
        String className = StrUtil.underscoreToCamelCase(tableName);
        className = className.substring(0, 1).toUpperCase() + className.substring(1);

        // 创建Java Bean类
        StringBuilder sb = new StringBuilder();
        // 注解
        sb.append("@Data").append("\n");
        sb.append("@NoArgsConstructor").append("\n");
        sb.append("@AllArgsConstructor").append("\n");
        // 类
        sb.append("public class ").append(className).append(" {\n");
        for (int i = 0; i < columnNames.size(); i++) {
            // 字段名称,字段类型,字段注释
            val name = columnNames.get(i);
            val type = columnTypes.get(i);
            val comment = columnComments.get(i);

            // java字段类型
            String javaType = DbType.getJavaType(type, imports);

            sb
                    // 注释
                    .append("    /**\n")
                    .append("     * ").append(comment).append("\n")
                    .append("     */\n")
                    // 字段
                    .append("    private ").append(javaType).append(" ")
                    .append(StrUtil.underscoreToCamelCase(name)).append(";\n");
        }
        sb.append("}\n");

        val sbStart = new StringBuilder();
        imports.stream().distinct().forEach(v -> sbStart.append(v).append("\n"));
        sbStart.append("\n").append(sb.toString());
        return sbStart.toString();
    }

    public HashMap<String, Object> getTableEntityList(String databaseName) {
        val tableNames = getTableNames(databaseName);

        val map = new HashMap<String, Object>();
        tableNames.forEach(v -> map.put(v, getTableEntity(databaseName, v)));
        return map;
    }


    public static void main(String[] args) {
        val mc = new MySQLClient("jdbc:mysql://localhost:3306/erp_system", "root", "zjr.1199");

//        System.out.println(mc.getDatabaseMetaDataMap());
//        System.out.println(mc.getDatabases());

//        System.out.println(mc.getTableNames("sys"));
//        System.out.println(mc.getTableMetaData("sys"));
//        System.out.println(mc.getTableEntity("sys", "sys_config"));
    }
}
