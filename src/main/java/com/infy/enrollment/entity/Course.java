package com.infy.enrollment.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "course")
@Setter
@Getter
@Data
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer id;
    @Column(length = 90)
    private String title;
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name="teacher_id")
    @ToString.Exclude

    private Teacher teacher;
    @ManyToMany(mappedBy = "courses",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonIgnore
    @ToString.Exclude
    private Set<Student>students;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof  Course))
            return false;
        Course other =(Course)o;
        return id != null && id.equals(other.getId());
    }
    @Override
    public int hashCode(){
        return getClass().hashCode();

    }

    public void removeTeacher(Teacher teacher){
        this.setTeacher(null);
        teacher.setCourse(null);
    }
    public void replaceTeacher(Teacher teacher){
        this.setTeacher(teacher);
        teacher.setCourse(this);
    }
}