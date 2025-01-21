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

    private static final String BASE_SELECT_USER_QUERY = "SELECT u FROM User u ";
    private static final String BASE_COUNT_USER_QUERY = "SELECT COUNT(u) FROM User u ";

    public UserDAO() {
        super(User.class);
    }

    public record UserFilter(
        Set<User.Status> status,
        Set<Role.Name> roles,
        String email,
        Long size,
        Long offset) {
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

        String statement = filterQueryBuilder(BASE_SELECT_USER_QUERY, userFilter);

        TypedQuery<User> query = getEntityManager().createQuery(statement, User.class);

        query = setFilterParameters(query, userFilter);

        if (userFilter.size() != null) {
            query.setMaxResults(userFilter.size().intValue());
        }

        if (userFilter.offset() != null) {
            query.setFirstResult(userFilter.offset().intValue());
        }

        return QueryUtils.getResultListFromQuery(query);
    }

    public int countFiltered(UserFilter userFilter) {

        String statement = filterQueryBuilder(BASE_COUNT_USER_QUERY, userFilter);

        TypedQuery<User> query = getEntityManager().createQuery(statement, User.class);

        query = setFilterParameters(query, userFilter);

        return QueryUtils.getIntResultFromQuery(query);
    }

    private String filterQueryBuilder(String baseSelectQuery, UserFilter filter) {

        StringBuilder selectText = new StringBuilder(baseSelectQuery);
        StringBuilder filterText = new StringBuilder("");
        String prefix = "WHERE ";

        if (!filter.status().isEmpty()) {
            filterText.append(prefix).append("u.status IN :status ");
            prefix = "AND ";
        }

        if (!filter.roles().isEmpty()) {
            selectText.append("JOIN u.roles r ");
            filterText.append(prefix).append("r.name IN :roles ");
            prefix = "AND ";
        }

        if (filter.email() != null && !filter.email().isBlank()) {
            filterText.append(prefix).append("u.email = :email ");
            prefix = "AND ";
        }

        return selectText.append(filterText).toString();

    }

    private <T> TypedQuery<T> setFilterParameters(TypedQuery<T> query, UserFilter filter) {

        if (!filter.status().isEmpty()) {
            query.setParameter("status", filter.status());
        }

        if (!filter.roles().isEmpty()) {
            query.setParameter("roles", filter.roles());
        }

        if (filter.email() != null && !filter.email().isBlank()) {
            query.setParameter("email", filter.email());
        }

        return query;
    }

}
