package org.chintanpatel.springbootthymeleaf.priority;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("priorityService")
public class PriorityServiceImpl implements PriorityService{

    private final PriorityRepository priorityRepository;

    public PriorityServiceImpl(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public void addPriority(Priority priority) {
        priorityRepository.save(priority);
    }

    @Override
    public List<Priority> getAllPriorityList() {
        return priorityRepository.findAll();
    }

    @Override
    public Priority getPriorityById(Long priorityId) {
        return priorityRepository.findById(priorityId).orElse(null);
    }

    @Override
    public void deletePriorityById(Long priorityId) {
        priorityRepository.deleteById(priorityId);
    }

    @Override
    public List<Priority> searchByPriorityType(String priorityType) {
        return priorityRepository.findByPriorityTypeContainingIgnoreCase(priorityType);
    }
}
