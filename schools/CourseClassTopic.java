/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.persistence.entities.schools;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;

/**
 * @author Carlos Gonçalves
 */
@Entity
@Table(name = "courseclasstopics")
@Cache(type = CacheType.FULL, isolation = CacheIsolationType.SHARED, alwaysRefresh = true, coordinationType = CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES)
@NamedQuery(name = CourseClassTopic.QUERY_FIND_ALL, query = "SELECT c from CourseClassTopic c")
public class CourseClassTopic implements Serializable {

    /**
     * Auto-generated by IDE
     */
    private static final long serialVersionUID = 8869247546710997068L;

    public static final String QUERY_FIND_ALL = "CourseClassTopic.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURSE_CLASS_TOPICS_SEQUENCE")
    @SequenceGenerator(name = "COURSE_CLASS_TOPICS_SEQUENCE",
        sequenceName = "courseclasses_id_seq",
        initialValue = 1,
        allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "ordering", nullable = false)
    private int order;

    @Column(name = "description")
    private Long descriptionContentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseclass", referencedColumnName = "id")
    private CourseClass courseClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public CourseClass getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
    }

    public Long getDescriptionContentId() {
        return descriptionContentId;
    }

    public void setDescriptionContentId(Long descriptionContentId) {
        this.descriptionContentId = descriptionContentId;
    }

}
