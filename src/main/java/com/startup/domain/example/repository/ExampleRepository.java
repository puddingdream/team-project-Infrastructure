package com.startup.domain.example.repository;

import com.startup.domain.example.entity.Example;
import com.startup.domain.example.enums.ExampleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExampleRepository extends JpaRepository<Example, Long> {

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);

    List<Example> findAllByOrderByCreatedAtDesc();

    List<Example> findAllByStatusOrderByCreatedAtDesc(ExampleStatus status);
}
