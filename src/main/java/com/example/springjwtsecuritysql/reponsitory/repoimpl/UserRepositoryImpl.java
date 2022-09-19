package com.example.springjwtsecuritysql.reponsitory.repoimpl;

import com.example.springjwtsecuritysql.reponsitory.BaseRepository;
import com.example.springjwtsecuritysql.reponsitory.UserRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserRepositoryImpl extends BaseRepository {
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getAllEmail() {
        String query = "SELECT USERS.email\n" +
                "FROM USERS\n";
        return getJdbcTemplate().queryForList(query, String.class);
    }
}
