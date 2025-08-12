package org.chintanpatel.springbootthymeleaf.status;

import java.util.List;

public interface StatusService {

    void addStatus(Status status);

    List<Status>getAllStatusList();

    Status getStatusById(Long statusId);

    void deleteStatusById(Long statusId);

    List<Status>searchByStatusType(String statusType);
}
