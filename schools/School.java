/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.persistence.entities.schools;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;
import pt.cmg.aeminium.knowledge.persistence.entities.identity.User;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.Country;

/**
 * @author Carlos Gonçalves
 */
@Entity
@Table(name = "schools")
@Cache(type = CacheType.FULL, isolation = CacheIsolationType.SHARED, alwaysRefresh = true, coordinationType = CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES)
@NamedQuery(name = School.QUERY_FIND_ALL, query = "SELECT s from School s")
@NamedQuery(name = School.QUERY_FIND_IN_IDS, query = "SELECT s from School s WHERE s.id IN :ids")
@NamedQuery(name = School.QUERY_FIND_BY_NAME,
    query = "SELECT s from School s WHERE s.nameTextContentId = (SELECT tc.id FROM TextContent tc WHERE tc.textValue = :name)")
@NamedQuery(name = School.QUERY_FIND_BY_TRANSLATED_NAME,
    query = "SELECT s from School s WHERE s.nameTextContentId = (SELECT tc.id FROM TranslatedText tc WHERE tc.language = :language AND tc.textValue = :name)")
public class School implements Serializable {

    /**
     * Auto-generated by IDE
     */
    private static final long serialVersionUID = -2771430763120052593L;

    public static final String QUERY_FIND_ALL = "School.findAll";
    public static final String QUERY_FIND_IN_IDS = "School.findInIds";
    public static final String QUERY_FIND_BY_NAME = "School.findByName";
    public static final String QUERY_FIND_BY_TRANSLATED_NAME = "School.findByTranslatedName";

    @Id
    @SequenceGenerator(
        sequenceName = "school_id_seq",
        allocationSize = 1,
        initialValue = 1,
        name = "SCHOOLS_SEQUENCE")
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "SCHOOLS_SEQUENCE")
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "country", referencedColumnName = "id")
    private Country country;

    @Column(name = "name")
    private Long nameTextContentId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "school")
    private List<Course> courses;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "createdby", referencedColumnName = "id")
    private User createdBy;

    public School() {
        this.createdAt = LocalDateTime.now();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNameTextContentId() {
        return nameTextContentId;
    }

    public void setNameTextContentId(Long nameTextContentId) {
        this.nameTextContentId = nameTextContentId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        School other = (School) obj;
        return Objects.equals(id, other.id);
    }

}
