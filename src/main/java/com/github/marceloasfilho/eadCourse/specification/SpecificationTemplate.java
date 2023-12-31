package com.github.marceloasfilho.eadCourse.specification;

import com.github.marceloasfilho.eadCourse.model.CourseModel;
import com.github.marceloasfilho.eadCourse.model.CourseUserModel;
import com.github.marceloasfilho.eadCourse.model.LessonModel;
import com.github.marceloasfilho.eadCourse.model.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {
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
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Join<CourseModel, CourseUserModel> userProd = root.join("coursesUsers");
            return criteriaBuilder.equal(userProd.get("userId"), userId);
        };
    }

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface CourseSpec extends Specification<CourseModel> {
    }

    @And({
            @Spec(path = "title", spec = Like.class)
    })
    public interface ModuleSpec extends Specification<ModuleModel> {
    }

    @And({
            @Spec(path = "title", spec = Like.class)
    })
    public interface LessonSpec extends Specification<LessonModel> {
    }
}
