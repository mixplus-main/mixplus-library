package com.mixplus.library.mysql;




import com.mixplus.library.util.StringUtil;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public class MySQL {
    private String host;
    private int port;
    private String username;
    private String password;
    private String database;

    private Connection connection;

    public MySQL(
            String host,
            int port,
            String username,
            String password,
            String database
    ) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public MySQL() {

    }

    public void createDatabase(String name) {
        if (!StringUtil.isValidIdentifier(name)) {
            throw new IllegalArgumentException(
                    "Invalid database name: " + database
            );
        }

        String url = "jdbc:mysql://" + host + ":" + port;
        try (
                Connection connection = DriverManager.getConnection(
                        url,
                        username,
                        password
                );
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS " + database
            );

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to create database: " + database,
                    e
            );
        }
    }

    public void connect() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            connection = DriverManager.getConnection(
                    url,
                    username,
                    password
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to MySQL", e);
        }
    }

    @Deprecated
    public int executeUpdate(String sql) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute SQL", e);
        }
    }

    @Deprecated
    public List<Map<String, Object>> executeQuery(String sql) {
        List<Map<String, Object>> result = new ArrayList<>();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
                ) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);

                    row.put(columnName, value);
                }
                result.add(row);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query", e);
        }
    }

    public void createTable(String tableName, TableElement... elements) {
        if (!StringUtil.isValidIdentifier(tableName)) {
            throw new IllegalArgumentException(
                    "Invalid table name: " + tableName
            );
        }

        if (elements == null || elements.length == 0) {
            throw new IllegalArgumentException(
                    "Table must contain at least one element"
            );
        }
        String columns = Arrays.stream(elements)
                .map(TableElement::toSQL)
                .collect(Collectors.joining(", "));



        String sql = "CREATE TABLE " + tableName + " (" + columns + ")";

        executeUpdate(sql);

    }

    public void insert(String tableName, Map<String, Object> values) {
        if (tableName == null || tableName.isBlank()) {
            throw new IllegalArgumentException("Table name cannot be null or empty");
        }

        if (!StringUtil.isValidIdentifier(tableName)) {
            throw new IllegalArgumentException("Invalid table name: " + tableName);
        }

        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Values cannot be null or empty");
        }
        for (String column : values.keySet()) {
            if (!StringUtil.isValidIdentifier(column)) {
                throw new IllegalArgumentException(
                        "Invalid column name: " + column
                );
            }
        }

        String columns = String.join(", ", values.keySet());

        String placeholders = String.join(
                ", ",
                Collections.nCopies(values.size(), "?")
        );

        String sql = "INSERT INTO " + tableName + " (" +
                columns + ") VALUES (" + placeholders + ")";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int index = 1;

            for (Object value : values.values()) {
                statement.setObject(index++, value);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert data", e);
        }
    }

    public int update(
            String tableName,
            Map<String, Object> values,
            Where where
    ) {
        if (!StringUtil.isValidIdentifier(tableName)) {
            throw new RuntimeException("Invalid table name: " + tableName);
        }

        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException(
                    "Values cannot be null or empty"
            );
        }

        for (String column : values.keySet()) {
            if (!StringUtil.isValidIdentifier(column)) {
                throw new IllegalArgumentException(
                        "Invalid column name: " + column
                );
            }
        }

        if (where == null) {
            throw new IllegalArgumentException(
                    "Where cannot be null or empty"
            );
        }

        String set =  values.keySet().stream()
                .map(column -> column + " = ?")
                .collect(Collectors.joining(", "));

        String sql = "UPDATE " + tableName +
                " SET " + set +
                " WHERE " + where.toSQL();

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            int index = 1;

            for (Object value : values.values()) {
                statement.setObject(index++, value);
            }

            for (Object parameter : where.getParameters()) {
                statement.setObject(index++, parameter);
            }

            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update data", e);
        }
    }

    public int delete(
            String tableName,
            Where where
    ) {
        if (!StringUtil.isValidIdentifier(tableName)) {
            throw new IllegalArgumentException(
                    "Invalid table name: " + tableName
            );
        }

        if (where == null) {
            throw new IllegalArgumentException(
                    "Where cannot be null or empty"
            );
        }

        String sql = "DELETE FROM " + tableName + " WHERE " + where.toSQL();

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            int index = 1;

            for (Object parameter : where.getParameters()) {
                statement.setObject(index++, parameter);
            }

            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete data", e);
        }
    }

    public List<Map<String, Object>> select(String tableName) {
        if (!StringUtil.isValidIdentifier(tableName)) {
            throw new IllegalArgumentException(
                    "Invalid table name: " + tableName
            );
        }

        if (!isTable(tableName)) {
            throw new IllegalArgumentException(
                    "Table does not exist: " + tableName
            );
        }

        return executeQuery("SELECT * FROM " + tableName);
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to close connection", sqlException);
        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public boolean isDatabase(String databaseName) {
        String sql = "SHOW DATABASES LIKE ?";

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            statement.setString(1, databaseName);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check database", e);
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean isTable(String tableName) {
        String sql = "SHOW TABLES LIKE ?";

        try (
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            statement.setString(1, tableName);


            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check table", e);
        }
    }
}
