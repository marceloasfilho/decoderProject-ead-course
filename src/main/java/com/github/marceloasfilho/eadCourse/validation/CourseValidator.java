package com.github.marceloasfilho.eadCourse.validation;

import com.github.marceloasfilho.eadCourse.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {
    @Autowired
    @Qualifier("defaultValidator")
    Validator validator;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDTO courseDTO = (CourseDTO) target;
        this.validator.validate(courseDTO, errors);
        if (!errors.hasErrors()) {
            this.validateUserInstructor(courseDTO.getUserInstructor(), errors);
        }
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors) {
        // todo verificações de state transfer
        /* ResponseEntity<UserDTO> userById;
        try {
            userById = authuserClient.getUserById(userInstructor);
            if (Objects.requireNonNull(userById.getBody()).getUserType().equals(UserType.STUDENT)) {
                errors.rejectValue("userInstructor", "UserInstructorError", "ERROR: User must be an INSTRUCTOR or an ADMIN");
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                errors.rejectValue("userInstructor", "UserInstructorError", "ERROR: User not found");
            }
        }
        */
    }
}
