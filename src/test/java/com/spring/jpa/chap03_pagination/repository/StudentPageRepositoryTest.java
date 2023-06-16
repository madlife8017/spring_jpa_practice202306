package com.spring.jpa.chap03_pagination.repository;

import com.spring.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
//JPA는 insert, update, delete시에 트렌젝션을 기준으로 동작하는 경우가 많음.
//기능을 보장받기 위해 웬만하면 트랜잭션 기능을 함께 사용해야 합니다
// 나중에 MVC 구조에서 Service 클래스에 아노테이션을 첨부하면 됩니다.
@Transactional
@Rollback(false)
class StudentPageRepositoryTest {

    @Autowired
    StudentPageRepository studentPageRepository;

    @BeforeEach
    void bulkInsert(){
        //학생 147명 저장
        for(int i =1 ; i<=147;i++){
            Student s = Student.builder()
                    .name("김테스트" + i)
                    .city("도시시" + i)
                    .major("전공공" + i)
                    .build();

            studentPageRepository.save(s);
        }
    }

    @Test
    @DisplayName("기본 페이징 테스트")
    void testBasicPagination() {
        //given
        int pageNo = 15;
        int amount = 10;

        //페이지 정보 생성
        Pageable pageInfo = PageRequest.of(pageNo-1
                , amount
//                , Sort.by("name").ascending()); //정렬기준 필드명!
                ,Sort.by(
                        Sort.Order.desc("name"),
                        Sort.Order.asc("city")
                )
        );


        //when
        Page<Student> students= studentPageRepository.findAll(pageInfo);

        //페이징이 완료된 데이터셋
        List<Student> studentList = students.getContent();

        //총페이지
        int totalPages = students.getTotalPages();

        //총학생수
        long totalElements = students.getTotalElements();
        boolean next = students.hasNext();
        boolean prev = students.hasPrevious();



        //then
        System.out.println("\n\n\n");

        System.out.println("totalPages = " + totalPages);
        System.out.println("totalElements = " + totalElements);
        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");
    }
    
    @Test
    @DisplayName("이름검색+페이징")
    void testSearchAndPagination() {
        //given
        int pageNo=1;
        int size =10;
        Pageable pageInfo = PageRequest.of(pageNo-1,size);

        //when
        Page<Student> students = studentPageRepository.findByNameContaining("3", pageInfo);

        int totalPages = students.getTotalPages();
        long totalElements = students.getTotalElements();

        //then
        System.out.println("\n\n\n");
        students.getContent().forEach(System.out::println);
        System.out.println("totalElements = " + totalElements);
        System.out.println("totalPages = " + totalPages);
        System.out.println("\n\n\n");
    }

}