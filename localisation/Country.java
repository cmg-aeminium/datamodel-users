/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.persistence.entities.localisation;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;

@Entity
@Table(name = "countries")
@Cache(type = CacheType.FULL, isolation = CacheIsolationType.SHARED, alwaysRefresh = true, coordinationType = CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES)
@NamedQuery(name = Country.QUERY_FIND_ALL, query = "SELECT c from Country c")
public class Country implements Serializable {

    public static final String QUERY_FIND_ALL = "Country.findAll";
    /**
     * Auto-generated by IDE
     */
    private static final long serialVersionUID = 414674185345586611L;

    @Id
    @SequenceGenerator(
        sequenceName = "country_id_seq",
        allocationSize = 1,
        initialValue = 1,
        name = "COUNTRIES_SEQUENCE")
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "COUNTRIES_SEQUENCE")
    private Long id;

    @Column
    private String alpha2Code;

    // I don't need the actual entity as translations are loaded elsewhere
    // @OneToOne(optional = false, fetch = FetchType.LAZY)
    // @JoinColumn(name = "name", referencedColumnName = "id")
    // private TextContent nameContent;

    @Column(name = "name")
    private Long nameTextContentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public Long getNameTextContentId() {
        return nameTextContentId;
    }

    public void setNameTextContentId(Long nameTextContentId) {
        this.nameTextContentId = nameTextContentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alpha2Code, id);
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
        Country other = (Country) obj;
        return Objects.equals(alpha2Code, other.alpha2Code) && Objects.equals(id, other.id);
    }

}
