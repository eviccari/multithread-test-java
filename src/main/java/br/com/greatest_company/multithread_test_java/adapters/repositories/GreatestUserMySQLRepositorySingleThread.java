package br.com.greatest_company.multithread_test_java.adapters.repositories;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.greatest_company.multithread_test_java.app.domain.dtos.GreatestUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;

@Repository
public class GreatestUserMySQLRepositorySingleThread implements GreatestUserRepository{

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public void create(GreatestUserDTO dto) throws InternalServerErrorException {
        var query = "insert into greatest_users.users (id, legacy_login, legacy_id,legacy_node_id, legacy_url, legacy_html_url , new_email, created_at)\n" +
                    " values (:id, :legacyLogin, :legacyID, :legacyNodeID, :legacyUrl, :legacyHtmlUrl , :newEmail, :createdAt)";

        try {
            namedParameterJdbcTemplate.update(
                    query,
                    new BeanPropertySqlParameterSource(dto)
            );
        }catch (DataAccessException dae) {
            throw new InternalServerErrorException(dae.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public void setEmpty() throws InternalServerErrorException {
        var query = "truncate table greatest_users.users";

        try {
            jdbcTemplate.execute(query);
        }catch (DataAccessException dae) {
            throw new InternalServerErrorException(dae.getMostSpecificCause().getMessage());
        }
    }
}
