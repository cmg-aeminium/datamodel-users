/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.datamodel.users.dao.identity;

import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import pt.cmg.aeminium.datamodel.users.dao.JPACrudDAO;
import pt.cmg.aeminium.datamodel.users.entities.identity.Role;
import pt.cmg.aeminium.datamodel.users.entities.identity.User;
import pt.cmg.jakartautils.jpa.QueryUtils;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class UserDAO extends JPACrudDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    public record UserFilter(Set<User.Status> status, Set<Role.Name> roles, String email, Long size, Long offset) {
    };

    public User findByEmail(String email) {

        if (StringUtils.isBlank(email)) {
            return null;
        }

        TypedQuery<User> query = getEntityManager().createNamedQuery(User.QUERY_FIND_BY_EMAIL, User.class);
        query.setParameter("email", email);

        List<User> resultList = QueryUtils.getResultListFromQuery(query);
        return resultList.isEmpty() ? null : resultList.getFirst();
    }

    public List<User> findByFiltered(UserFilter userFilter) {

        TypedQuery<User> query = null;

        StringBuilder select = new StringBuilder("SELECT u FROM User u ");
        StringBuilder filter = new StringBuilder("");
        String sqlFilter = "WHERE ";

        if (!userFilter.status().isEmpty()) {
            filter.append(sqlFilter).append("u.status IN :status ");
            sqlFilter = "AND ";
        }

        if (!userFilter.roles().isEmpty()) {
            select.append("JOIN u.roles r ");
            filter.append(sqlFilter).append("r.name IN :roles ");
            sqlFilter = "AND ";
        }

        if (userFilter.email() != null && !userFilter.email().isBlank()) {
            filter.append(sqlFilter).append("u.email = :email ");
            sqlFilter = "AND ";
        }

        String statement = select.append(filter).toString();
        query = getEntityManager().createQuery(statement, User.class);

        if (!userFilter.status().isEmpty()) {
            query.setParameter("status", userFilter.status());
        }

        if (!userFilter.roles().isEmpty()) {
            query.setParameter("roles", userFilter.roles());
        }

        if (userFilter.email() != null && !userFilter.email().isBlank()) {
            query.setParameter("email", userFilter.email());
        }

        if (userFilter.size() != null) {
            query.setMaxResults(userFilter.size().intValue());
        }

        if (userFilter.offset() != null) {
            query.setFirstResult(userFilter.offset().intValue());
        }

        return QueryUtils.getResultListFromQuery(query);
    }

}
