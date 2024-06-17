package org.acme.entities;

import com.fasterxml.jackson.annotation.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "School")
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NamedQuery(name = "School.findBySchoolName",
        query = "FROM School WHERE LOWER(schoolName) LIKE LOWER(CONCAT('%', :schoolName, '%'))")
@NamedQuery(name = "School.findByAddress",
        query = "FROM School WHERE LOWER(address) LIKE LOWER(CONCAT('%', :address, '%'))")
@NamedQuery(name = "School.findByPhoneNumber",
        query = "FROM School WHERE LOWER(phoneNumber) LIKE LOWER(CONCAT('%', :phoneNumber, '%'))")
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
    @Fetch(FetchMode.SUBSELECT)
    public List<Teacher> teachers;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("school")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Fetch(FetchMode.SUBSELECT)
    public List<Student> students;

    public static PanacheQuery<School> findBySchoolName(String schoolName) {
        return find("#School.findBySchoolName", Parameters.with("schoolName", schoolName));
    }

    public static PanacheQuery<School> findByAddress(String address) {
        return find("#School.findByAddress", Parameters.with("address", address));
    }

    public static PanacheQuery<School> findByPhoneNumber(String phoneNumber) {
        return find("#School.findByPhoneNumber", Parameters.with("phoneNumber", phoneNumber));
    }

}
