package pocketestore.daoimpl.utils;

import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


/**
 * Java 操作数据库的工具类
 *
 * @描述： 1. 在经常用的数据库操作中我们主要常用的是Insert/Update/Delete/Select
 * 2. 在执行SQL查询的时候可以使用的包括sql语句和sql存储过程
 * 3. 执行的SQL参数可以包含一个或多个参数
 * 4. 返回的结果可以是影响的行数或者返回结果集
 * 5. 执行过程中可以选择是否使用事务
 * 6. 在业务层面上需要提供有效接口，在事务中实现多个业务操作
 * @方案： 为了实现以上提供的功能，该helper类模拟了c#的SqlHelper类
 * 但是对于事务，由于Java没有所谓的SqlTransaction类，而是Connection自带光环
 * 同时java也没有对Parameter进行封装，从而显得封装有些简陋
 */
public class MySqlHelper {
    /**
     * 数据库相关配置信息
     **/
    private static String driver = "";
    private static String url = "";
    private static String userName = "";
    private static String password = "";

    /**
     * 读取配置文件相关对象
     **/
    private static Properties properties = null;
    private static FileInputStream fileInputStream = null;

    /**
     * 静态构造函数，初始化相关数据库对象
     * java没有静态构造函数，只有静态初始化，或者是静态代码块
     **/
    static {
        try {
            properties = new Properties();
            String executePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String dbConfigPath = executePath + "dbInfo.properties";
            fileInputStream = new FileInputStream(dbConfigPath);
            properties.load(fileInputStream);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
            Class.forName(driver);

        } catch (Exception e) {
            //如果有错误则输出到控制台
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fileInputStream = null;
        }
    }

    /**
     * 获取数据库连接对象Connection
     *
     * @return Connection
     */
    public static Connection getConnection() {
        return getConnection(false);
    }

    /**
     * 获取数据库连接对象Connection
     *
     * @param enableTransaction
     * @return Connection对象
     */
    public static Connection getConnection(boolean enableTransaction) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, userName, password);
            if (enableTransaction) {
                conn.setAutoCommit(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 自动创建Connection对象，执行数据库命令，返回非数据集
     *
     * @param commandType 数据库命令的类型，Text（sql语句）或者StorageProcedure（存储过程）
     * @param commandSql  数据库命令
     * @param parameters  执行数据库命令所需要的参数
     * @return 返回int值，指示该命令引起数据库变化的行数
     */
    public static int executeNonQuery(CommandType commandType, String commandSql, Object... parameters) {
        Connection connection = getConnection();
        return executeNonQuery(connection, commandType, commandSql, parameters);
    }

    /**
     * 通过给定的Connection对象，执行数据库命令，返回非数据集
     *
     * @param connection  有效的数据库连接Connection
     * @param commandType 数据库命令的类型，Text（sql语句）或者StorageProcedure（存储过程）
     * @param commandSql  数据库命令
     * @param parameters  执行数据库命令所需要的参数
     * @return 返回int值，指示该命令引起数据库变化的行数
     */
    public static int executeNonQuery(Connection connection, CommandType commandType, String commandSql, Object... parameters) {
        int rowCount = 0;
        try {
            rowCount = executeNonQuery(connection, commandType, false, commandSql, parameters);
        } catch (SQLException e) {
            System.out.println("执行数据库查询失败");
            e.printStackTrace();
        }
        return rowCount;
    }

    /**
     * 通过给定的Connection对象，执行数据库命令，返回非数据集
     *
     * @param connection        有效的数据库连接Connection
     * @param commandType       数据库命令的类型，Text（sql语句）或者StorageProcedure（存储过程）
     * @param enableTransaction 是否启用事务
     * @param commandSql        数据库命令
     * @param parameters        执行数据库命令所需要的参数
     * @return 返回int值，指示该命令引起数据库变化的行数
     * @throws SQLException
     */
    public static int executeNonQuery(Connection connection, CommandType commandType, boolean enableTransaction,
                                      String commandSql, Object... parameters) throws SQLException {
        int rowCount = 0;
        PreparedStatement statement = buildStatement(connection, commandType, commandSql, parameters);
        try {
            boolean flag = statement.execute();
            if (!flag) {
                //获取最后一样的row number
                rowCount = statement.getUpdateCount();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (!enableTransaction) {
                close(connection, statement);
            } else {
                close(null, statement);
            }
        }
        return rowCount;
    }

    public static String executeNonQueryWithKey(String commandSql, Object... parameters) {
        Connection connection = getConnection();
        return executeNonQueryWithKey(connection, commandSql, parameters);
    }

    public static String executeNonQueryWithKey(Connection connection, String commandSql, Object... parameters) {
        String key = null;
        try {
            key = executeNonQueryWithKey(connection, false, commandSql, parameters);
        } catch (SQLException e) {
            System.out.println("执行数据库查询失败");
            e.printStackTrace();
        }
        return key;
    }

    public static String executeNonQueryWithKey(Connection connection, boolean enableTransaction,
                                                String commandSql, Object... parameters) throws SQLException {
        String key = null;
        PreparedStatement statement = buildStatementWithKey(connection, commandSql, parameters);
        try {
            boolean flag = statement.execute();
            if (!flag) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    key = resultSet.getObject(1).toString();
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (!enableTransaction) {
                close(connection, statement);
            } else {
                close(null, statement);
            }
        }
        return key;
    }

    /**
     * 通过自动创建的Connection对象，执行数据库命令，返回数据集
     *
     * @param commandType 数据库命令的类型，Text（sql语句）或者StorageProcedure（存储过程）
     * @param commandSql  数据库命令
     * @param parameters  执行数据库命令所需要的参数
     * @return 返回数据集ResultSet
     */
    public static List<Map<String, Object>> executeResultSet(CommandType commandType, String commandSql, Object... parameters) {
        Connection connection = getConnection();
        return executeResultSet(connection, commandType, commandSql, parameters);
    }

    /**
     * 通过给定的Connection对象，执行数据库命令，返回数据集
     *
     * @param connection  有效的数据库连接Connection
     * @param commandType 数据库命令的类型，Text（sql语句）或者StorageProcedure（存储过程）
     * @param commandSql  数据库命令
     * @param parameters  执行数据库命令所需要的参数
     * @return 返回数据集ResultSet
     */
    public static List<Map<String, Object>> executeResultSet(Connection connection, CommandType commandType, String commandSql, Object... parameters) {
        List dataList = null;
        PreparedStatement statement = buildStatement(connection, commandType, commandSql, parameters);
        try {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null) {
                dataList = convertResultToList(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("执行数据库查询失败");
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
        return dataList;
    }


    public static Object executeScalar(CommandType commandType, String commandSql, Object... parameters) {
        Connection connection = getConnection();
        return executeScalar(connection, commandType, commandSql, parameters);
    }

    /**
     * 通过给定的Connection对象，执行数据库命令，返回第一行的第一列数据
     *
     * @param connection  有效的数据库连接Connection
     * @param commandType 数据库命令的类型，Text（sql语句）或者StorageProcedure（存储过程）
     * @param commandSql  数据库命令
     * @param parameters  执行数据库命令所需要的参数
     * @return 返回的对象，可以通过强制转换成所需类型
     */
    public static Object executeScalar(Connection connection, CommandType commandType, String commandSql, Object... parameters) {
        Object objectResult = null;
        try {
            objectResult = executeScalar(connection, commandType, false, commandSql, parameters);
        } catch (SQLException e) {
            System.out.println("执行数据库查询失败");
            e.printStackTrace();
        }
        return objectResult;
    }

    /**
     * 通过给定的Connection对象，执行数据库命令，返回第一行的第一列数据
     *
     * @param connection  有效的数据库连接Connection
     * @param commandType 数据库命令的类型，Text（sql语句）或者StorageProcedure（存储过程）
     * @param commandSql  数据库命令
     * @param parameters  执行数据库命令所需要的参数
     * @return 返回的对象，可以通过强制转换成所需类型
     */
    public static Object executeScalar(Connection connection, CommandType commandType, boolean enableTransaction,
                                       String commandSql, Object... parameters) throws SQLException {
        Object result = null;
        PreparedStatement statement = buildStatement(connection, commandType, commandSql, parameters);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery();
            //读取第一行数据
            if (rs.next()) {
                result = rs.getObject(1);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (!enableTransaction) {
                close(connection, statement, rs);
            } else {
                close(null, statement, rs);
            }
        }
        return result;
    }

    /**
     * 关闭数据库资源
     *
     * @param connection 数据库连接
     */
    public static void close(Connection connection) {
        close(connection, null);
    }

    /**
     * 关闭数据库资源
     *
     * @param connection        数据库连接
     * @param preparedStatement 数据库预编译语句
     */
    public static void close(Connection connection, PreparedStatement preparedStatement) {
        close(connection, preparedStatement, null);
    }

    /**
     * 关闭数据库资源
     *
     * @param connection        数据库连接
     * @param preparedStatement 数据库预编译语句
     * @param resultSet         查询结果的第一行第一列
     */
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static PreparedStatement buildStatement(Connection connection, CommandType commandType, String sql, Object... parameters) {
        if (commandType == CommandType.Text) {
            return BuildPrepareStatement(connection, sql, parameters);
        } else if (commandType == CommandType.StoredProcedure) {
            return buildCallabeStatement(connection, sql, parameters);
        }
        return null;
    }

    private static PreparedStatement buildStatementWithKey(Connection connection, String commandSql, Object... parameters) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(commandSql,new String[]{"Id"});
            buildStatementParameters(preparedStatement, parameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    private static PreparedStatement buildCallabeStatement(Connection connection, String commandSql, Object... parameters) {
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(commandSql);
            buildStatementParameters(callableStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return callableStatement;
    }

    private static PreparedStatement BuildPrepareStatement(Connection connection, String commandSql, Object... parameters) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(commandSql);
            buildStatementParameters(preparedStatement, parameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    private static void buildStatementParameters(PreparedStatement statement, Object... parameters) {
        if (parameters != null) {
            try {
                for (int index = 0; index < parameters.length; index++) {
                    statement.setObject(index + 1, parameters[index]);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 将数据库的ResultSet转换成List列表
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private static List<Map<String, Object>> convertResultToList(ResultSet resultSet) throws SQLException {
        List dataList = null;
        try {
            ResultSetMetaData md = resultSet.getMetaData();//获取元数据
            int columnCount = md.getColumnCount();//获取列的数量
            dataList = new ArrayList();
            while (resultSet.next()) {
                Map rowData = new HashMap();//声明Map
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnLabel(i), resultSet.getObject(i));//获取键名及值
                }
                dataList.add(rowData);
            }
        } catch (SQLException e) {
            throw e;
        }

        return dataList;
    }

}
