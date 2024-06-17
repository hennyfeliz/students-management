package org.acme.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "Students")
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NamedQueries({
        @NamedQuery(name = "Student.findByStudentName",
                query = "FROM Student WHERE LOWER(studentName) LIKE LOWER(CONCAT('%', :studentName, '%'))"),
        @NamedQuery(name = "Student.findByGradeLevel",
                query = "FROM Student WHERE LOWER(gradeLevel) LIKE LOWER(CONCAT('%', :gradeLevel, '%'))"),
        @NamedQuery(name = "Student.findBySchool",
                query = "FROM Student WHERE school.id = :schoolId")
})
public class Student extends PanacheEntityBase {

    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "student_name", length = 100, nullable = false)
    public String studentName;

    @Column(name = "grade_level", length = 20)
    public String gradeLevel;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    @JsonIgnoreProperties("students")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public School school;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("student")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Fetch(FetchMode.SUBSELECT)
    public List<Enrollment> enrollments;

    public static PanacheQuery<Student> findByStudentName(String studentName) {
        return find("#Student.findByStudentName", Parameters.with("studentName", studentName));
    }

    public static PanacheQuery<Student> findByGradeLevel(String gradeLevel) {
        return find("#Student.findByGradeLevel", Parameters.with("gradeLevel", gradeLevel));
    }

    public static PanacheQuery<Student> findBySchool(Long schoolId) {
        return find("#Student.findBySchool", Parameters.with("schoolId", schoolId));
    }

}
