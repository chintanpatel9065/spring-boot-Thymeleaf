package org.chintanpatel.springbootthymeleaf.priority;

import java.util.List;

public interface PriorityService {

    void addPriority(Priority priority);

    List<Priority>getAllPriorityList();

    Priority getPriorityById(Long priorityId);

    void deletePriorityById(Long priorityId);

    List<Priority>searchByPriorityType(String priorityType);
}
