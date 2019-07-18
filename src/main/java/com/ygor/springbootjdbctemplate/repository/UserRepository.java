package com.ygor.springbootjdbctemplate.repository;

import com.ygor.springbootjdbctemplate.interfaces.IBaseRepository;
import com.ygor.springbootjdbctemplate.mapper.UserRowMapper;
import com.ygor.springbootjdbctemplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepository implements IBaseRepository<User>{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public User getById(int id) {
        try {
            return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, new UserRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users", new UserRowMapper());

    }

    @Override
    public User create(User user) {
        String sql = "insert into users (name, email, cellphone) values (?, ?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {

                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getCellphone());

                return ps;

            }
        }, holder);

        if (holder.getKeys() != null && holder.getKeys().size() > 1) {
            user.setId((int) holder.getKeys().get("id"));
        }
        else {
            user.setId((int) holder.getKey());
        }

        return user;
    }

    @Override
    public void update(int id, User user) {
        String sql = "update users set name = ?, email = ?, cellphone = ? where id = ?";

        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getCellphone(), id);

    }

    @Override
    public void remove(int id) {
        jdbcTemplate.update("delete from users where id = ?", id);
    }


}
