package com.valerio.userpersistency.security.configImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

public class CustomJdbcTokenImpl extends JdbcTokenRepositoryImpl {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomJdbcTokenImpl.class);

    public static final String CREATE_TABLE_SQL =
            "create table persistent_logins (" +
                    "username varchar(64) not null, " +
                    "series varchar(64) primary key, " +
                    "token varchar(64) not null, " +
                    "last_used timestamp not null)";

    public CustomJdbcTokenImpl() {
        super();
    }

    @Override
    protected void initDao() {
        try {
            super.getJdbcTemplate().execute(CREATE_TABLE_SQL);
        } catch (DataAccessException e) {
            LOGGER.info("table persistent_logins have been already created");
        }
    }

}
