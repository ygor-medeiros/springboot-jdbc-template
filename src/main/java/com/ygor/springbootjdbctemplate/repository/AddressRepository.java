package com.ygor.springbootjdbctemplate.repository;

import com.ygor.springbootjdbctemplate.interfaces.IBaseRepository;
import com.ygor.springbootjdbctemplate.mapper.AddressRowMapper;
import com.ygor.springbootjdbctemplate.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepository implements IBaseRepository<Address> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Address getById(int id) {
        try {
            return jdbcTemplate.queryForObject("select * from address where user_id = ?", new Object[]{id}, new AddressRowMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Address> getAll() {
        return jdbcTemplate.query("select * from address", new AddressRowMapper());
    }

    @Override
    public Address create(Address address) {
        String sql = "insert into address (user_id, street, city, state, country, zip_code) values (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, address.getUserId(), address.getStreet(), address.getCity(),
                address.getState(), address.getCountry(), address.getZipCode());

        return address;
    }

    @Override
    public void update(int id, Address address) {
        String sql = "update address set street = ?, city = ?, state = ?, " +
                "country = ?, zip_code = ? where user_id = ?";

        jdbcTemplate.update(sql, address.getStreet(), address.getCity(), address.getState(),
                address.getCountry(), address.getZipCode(), id);
    }

    @Override
    public void remove(int id) {
        jdbcTemplate.update("delete from address where user_id = ?", id);
    }
}
