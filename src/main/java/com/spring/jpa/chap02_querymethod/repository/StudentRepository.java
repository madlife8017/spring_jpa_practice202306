package com.spring.jpa.chap02_querymethod.repository;

import com.spring.jpa.chap02_querymethod.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository  extends JpaRepository<Student, String> {
    
    List<Student> findByName(String name);
    List<Student> findByCityAndMajor(String city, String major);

    List<Student> findByMajorContaining(String major);

    @Query(value = "SELECT * FROM tbl_student WHERE stu_name= :nm", nativeQuery = true)
    Student findNameWithSQL(@Param("nm") String name);

    //JPQL
    //SELECT 별칭 FROM 엔터티클래스명 AS 별칭
    //WHERE 별칭.필드명 = ?

    // native-sql : SELECT * FROM tbl_student
    //              WHERE stu_name = ?

    // jpql: SELECT st FROM Student AS st
    //       WHERE st.name = ?

    @Query("SELECT s FROM Student s WHERE s.city= ?1") //AND s.city=?2
    List<Student> getByCityWithJPQL(String city);

    @Query("SELECT st FROM Student st WHERE st.name LIKE %:nm%")
    List<Student> searchByNamesWithJPQL(@Param("nm") String name);

    //JPQL로 수정 삭제 쿼리 쓰기

    @Modifying //조회가 아닌경우 무조건 붙여야함
    @Query("DELETE FROM Student s WHERE s.name=?1")
    void deleteByNameWithJPQL(String name);

}