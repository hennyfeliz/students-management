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

@Entity
@Table(name = "Classes")
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NamedQueries({
        @NamedQuery(name = "Class.findByClassName",
                query = "FROM Class WHERE LOWER(className) LIKE LOWER(CONCAT('%', :className, '%'))"),
        @NamedQuery(name = "Class.findBySchedule",
                query = "FROM Class WHERE LOWER(schedule) LIKE LOWER(CONCAT('%', :schedule, '%'))"),
        @NamedQuery(name = "Class.findByTeacher",
                query = "FROM Class WHERE teacher.id = :teacherId")
})
public class Class extends PanacheEntityBase {

    @Id
    @Column(name = "class_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "class_name", length = 100)
    public String className;

    @Column(name = "schedule", length = 50)
    public String schedule;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    @JsonIgnoreProperties("classes")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public Teacher teacher;

    public static PanacheQuery<Class> findByClassName(String className) {
        return find("#Class.findByClassName", Parameters.with("className", className));
    }

    public static PanacheQuery<Class> findBySchedule(String schedule) {
        return find("#Class.findBySchedule", Parameters.with("schedule", schedule));
    }

    public static PanacheQuery<Class> findByTeacher(Long teacherId) {
        return find("#Class.findByTeacher", Parameters.with("teacherId", teacherId));
    }

}
