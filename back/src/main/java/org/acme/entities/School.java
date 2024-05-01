package org.acme.entities;

import com.fasterxml.jackson.annotation.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "School")
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
public class School extends PanacheEntityBase {

    @Id
    @Column(name = "school_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "school_name", length = 100, nullable = false)
    public String schoolName;

    @Column(length = 255)
    public String address;

    @Column(name = "phone_number", length = 20)
    public String phoneNumber;


    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("school")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public List<Teacher> teachers;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("school")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public List<Student> students;


    public static List<School> findBySchoolNameContaining(String nameFragment) {
        return list("schoolName LIKE ?1", "%" + nameFragment + "%");
    }

    public static List<School> schoolFilter(String schoolName, String address, String phoneNumber) {

    }

}
