package com.brunoandreotti.course.specifications;


import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.CourseUserModel;
import com.brunoandreotti.course.models.LessonModel;
import com.brunoandreotti.course.models.ModuleModel;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And(
            {
                    @Spec(path = "courseLevel", spec = Equal.class),
                    @Spec(path = "courseStatus", spec = Equal.class),
                    @Spec(path = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "userInstructor", spec = Equal.class)
            }
    )
    public interface CourseSpec extends Specification<CourseModel> {}

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface ModuleSpec extends Specification<ModuleModel> {}

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface LessonSpec extends Specification<LessonModel> {}

    public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<CourseModel> course = query.from(CourseModel.class);
            Expression<Collection<ModuleModel>> courseModules = course.get("modules");
            return criteriaBuilder.and(criteriaBuilder.equal(course.get("courseId"), courseId), criteriaBuilder.isMember(root, courseModules));
        };
    }

    public static Specification<LessonModel> lessonModuleId(final UUID moduleId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<ModuleModel> module = query.from(ModuleModel.class);
            Expression<Collection<LessonModel>> moduleLessons = module.get("lessons");
            return criteriaBuilder.and(criteriaBuilder.equal(module.get("moduleId"), moduleId), criteriaBuilder.isMember(root, moduleLessons));
        };
    }

    public static Specification<CourseModel> courseUserId(final UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<CourseModel, CourseUserModel> userJoin = root.join("courseUsers");
            return cb.equal(userJoin.get("userId"), userId);
        };
    }
}
