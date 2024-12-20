/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.datamodel.users.entities.identity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;
import org.eclipse.persistence.config.QueryHints;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.QueryHint;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import pt.cmg.aeminium.datamodel.common.entities.localisation.Language;

/**
 * @author Carlos Gonçalves
 */
@Entity
@Table(name = "users")
@Cache(type = CacheType.FULL, isolation = CacheIsolationType.SHARED, alwaysRefresh = true, coordinationType = CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES)
@NamedQuery(name = User.QUERY_FIND_BY_EMAIL,
    query = "SELECT u FROM User u WHERE u.email = :email",
    hints = {
        @QueryHint(name = QueryHints.QUERY_RESULTS_CACHE_TYPE, value = "FULL"),
        @QueryHint(name = QueryHints.QUERY_RESULTS_CACHE_SIZE, value = "500")
    })
public class User implements Serializable {

    /**
     * Auto-generated by IDE
     */
    private static final long serialVersionUID = -5624601134276531787L;

    public static final String QUERY_FIND_BY_EMAIL = "User.findByEmail";

    public enum Status {

        PENDING,
        ACTIVE,
        INACTIVE;

        private static final Map<String, Status> map = new HashMap<>();

        static {
            Status[] values = values();
            for (Status value : values) {
                map.put(value.name().toLowerCase(Locale.ROOT), value);
            }
        }

        // fromString is recognised by JAX-RS as an enum @Param converter - DO NOT change this method name
        public static Status fromString(String name) {
            if (name == null) {
                return null;
            }

            return map.get(name.toLowerCase(Locale.ROOT));
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQUENCE")
    @SequenceGenerator(name = "USERS_SEQUENCE",
        sequenceName = "users_id_seq",
        initialValue = 1,
        allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "salt", nullable = true)
    private String salt;

    @Column(name = "password", nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PENDING;

    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "userroles",
        joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id"))
    private List<Role> roles;

    public User() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getRolesAsStrings() {
        return roles.stream().map(role -> role.getName().toString()).collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }

        User other = (User) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", status=" + status + "]";
    }

}
