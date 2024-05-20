package org.acme.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "Teachers")
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NamedQueries({
        @NamedQuery(name = "Teacher.findByTeacherName",
                query = "FROM Teacher WHERE LOWER(teacherName) LIKE LOWER(CONCAT('%', :teacherName, '%'))"),
        @NamedQuery(name = "Teacher.findBySubjectTaught",
                query = "FROM Teacher WHERE LOWER(subjectTaught) LIKE LOWER(CONCAT('%', :subjectTaught, '%'))"),
        @NamedQuery(name = "Teacher.findBySchool",
                query = "FROM Teacher WHERE school.id = :schoolId")
})
public class Teacher extends PanacheEntityBase {

    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "teacher_name", length = 100)
    public String teacherName;

    @Column(name = "subject_taught", length = 100)
    public String subjectTaught;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    @JsonIgnoreProperties("teachers")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public School school;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("teacher")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public List<Class> classes;

    public static PanacheQuery<Teacher> findByTeacherName(String teacherName) {
        return find("#Teacher.findByTeacherName", Parameters.with("teacherName", teacherName));
    }

    public static PanacheQuery<Teacher> findBySubjectTaught(String subjectTaught) {
        return find("#Teacher.findBySubjectTaught", Parameters.with("subjectTaught", subjectTaught));
    }

    public static PanacheQuery<Teacher> findBySchool(Long schoolId) {
        return find("#Teacher.findBySchool", Parameters.with("schoolId", schoolId));
    }

}
