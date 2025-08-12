package org.chintanpatel.springbootthymeleaf.status;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("statusRepository")
public interface StatusRepository extends JpaRepository<Status, Long> {

    List<Status>findByStatusTypeContainingIgnoreCase(String statusType);
}