package org.chintanpatel.springbootthymeleaf.status;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("statusService")
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public void addStatus(Status status) {
        statusRepository.save(status);
    }

    @Override
    public List<Status> getAllStatusList() {
        return statusRepository.findAll();
    }

    @Override
    public Status getStatusById(Long statusId) {
        return statusRepository.findById(statusId).orElse(null);
    }

    @Override
    public void deleteStatusById(Long statusId) {
        statusRepository.deleteById(statusId);
    }

    @Override
    public List<Status> searchByStatusType(String statusType) {
        return statusRepository.findByStatusTypeContainingIgnoreCase(statusType);
    }
}
