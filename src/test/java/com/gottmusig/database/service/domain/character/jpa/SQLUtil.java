package com.gottmusig.database.service.domain.character.jpa;

import com.google.common.base.Splitter;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by leong on 03.07.2017.
 */
public class SQLUtil {

    private DataSource dataSource;

    public SQLUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void execute(byte[] bytes) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Splitter.on("\n\n").trimResults().omitEmptyStrings().split(new String(bytes, StandardCharsets.UTF_8)).forEach(sql -> {
                try (Statement statement = conn.createStatement()) {
                    statement.execute(sql);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
