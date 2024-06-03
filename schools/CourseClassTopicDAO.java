/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.dao.schools;

import javax.ejb.Stateless;
import pt.cmg.aeminium.knowledge.dao.JPACrudDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.CourseClassTopic;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class CourseClassTopicDAO extends JPACrudDAO<CourseClassTopic> {

    public CourseClassTopicDAO() {
        super(CourseClassTopic.class);
    }

}
