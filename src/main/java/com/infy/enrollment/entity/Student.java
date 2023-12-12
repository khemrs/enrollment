package com.infy.enrollment.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Table(name = "student")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer id;
    @Column(length = 45)
    private String name;
    @Column(length = 45)
    private String  email;
    @Column(length = 45)
    private String lastName;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "enrollment",
            joinColumns = {
                    @JoinColumn(name = "student_id",updatable = true,insertable = true,nullable = true)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id",updatable = true,insertable = true,nullable = false)
            }
    )
   @ToString.Exclude
    private Set<Course>courses;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof  Student))
            return false;
        Student other =(Student)o;
        return id != null && id.equals(other.getId());
    }
    @Override
    public int hashCode(){
        return getClass().hashCode();

    }

    public void removeCourse(Course course){
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    public void enrollToCourse(Course course){
        this.courses.add(course);
        course.getStudents().add(this);
    }
}