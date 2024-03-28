package org.acme.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "Classes")
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
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

    /*

    @OneToMany(mappedBy = "classs", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("classs")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public List<Enrollment> enrollments;
     */

}
