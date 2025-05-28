package com.brunoandreotti.course.models;

import com.brunoandreotti.course.dtos.ModuleRecordDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TB_MODULES")
@Data
public class ModuleModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID moduleId;

    @Column(nullable = false, unique = true, length = 150)
    private String title;

    @Column(nullable = false)
    private String description;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId")
    private CourseModel course;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    private Set<LessonModel> lessons;

    public ModuleModel fromDTO(ModuleRecordDTO courseRecordDTO) {
        var moduleModel = new ModuleModel();
        BeanUtils.copyProperties(courseRecordDTO, moduleModel);
        return moduleModel;
    }
}
