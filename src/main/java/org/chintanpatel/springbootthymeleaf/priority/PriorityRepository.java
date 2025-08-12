package org.chintanpatel.springbootthymeleaf.priority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("priorityRepository")
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    List<Priority>findByPriorityTypeContainingIgnoreCase(String priorityType);
}