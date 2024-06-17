package org.acme.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Enrollments")
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NamedQueries({
        @NamedQuery(name = "Enrollment.findByStudent",
                query = "FROM Enrollment WHERE student.id = :studentId"),
        @NamedQuery(name = "Enrollment.findByEnrollmentDate",
                query = "FROM Enrollment WHERE enrollmentDate = :enrollmentDate")
})
public class Enrollment extends PanacheEntityBase {

    @Id
    @Column(name = "enrollment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @JsonIgnoreProperties("enrollments")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public Student student;

    @Basic
    @Column(name = "enrollment_date")
    public Timestamp enrollmentDate;

    public static PanacheQuery<Enrollment> findByStudent(Long studentId) {
        return find("#Enrollment.findByStudent", Parameters.with("studentId", studentId));
    }

    public static PanacheQuery<Enrollment> findByEnrollmentDate(Timestamp enrollmentDate) {
        return find("#Enrollment.findByEnrollmentDate", Parameters.with("enrollmentDate", enrollmentDate));
    }

}
