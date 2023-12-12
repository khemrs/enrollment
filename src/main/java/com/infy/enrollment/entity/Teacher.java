package com.infy.enrollment.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Transactional
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Integer id ;
    @Column(length = 45)
    private String name;
    @Column(name = "last_name",length = 45)
    private String lastName;
    @Column(length = 45)
    private String email;
   @OneToOne (cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "teacher")
   @JsonIgnore
   @ToString.Exclude
   private Course course;

}
