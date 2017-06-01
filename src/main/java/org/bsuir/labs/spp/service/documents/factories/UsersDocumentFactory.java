package org.bsuir.labs.spp.service.documents.factories;

import org.bsuir.labs.spp.domain.Task;
import org.bsuir.labs.spp.domain.User;
import org.bsuir.labs.spp.domain.enumeration.TaskStatus;
import org.bsuir.labs.spp.repository.TaskRepository;
import org.bsuir.labs.spp.service.documents.dto.UserStatisticDTO;

import java.util.ArrayList;
import java.util.List;

public class UsersDocumentFactory implements DocumentDataFactory<UserStatisticDTO> {
    private final TaskRepository taskRepository;
    private List<User> users;

    public UsersDocumentFactory(List<User> users, TaskRepository taskRepository){
        this.users = users;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<UserStatisticDTO> getData(){
        List<UserStatisticDTO> result = new ArrayList<>();
        for (User user : this.users){
            result.add(getUserStatistic(user));
        }
        return result;
    }

    private UserStatisticDTO getUserStatistic(User user){
        UserStatisticDTO result = new UserStatisticDTO();
        result.setId(user.getId());
        result.setFullName(getFullName(user));

        List<Task> tasks = taskRepository.findByAssignee(user);
        result.setOpenTasks(getStatusTaskCount(tasks, TaskStatus.OPEN));
        result.setProgressTasks(getStatusTaskCount(tasks, TaskStatus.IN_PROGRESS));
        result.setCompletedTasks(getStatusTaskCount(tasks, TaskStatus.CLOSED));

        return result;
    }

    private String getFullName(User user){
        return user.getFirstName() + " " + user.getLastName();
    }

    private Integer getStatusTaskCount(List<Task> tasks, TaskStatus status){
        Integer count = 0;
        for (Task task : tasks){
            if (task.getStatus().equals(status)){
                count++;
            }
        }
        return count;
    }
}
