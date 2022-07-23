package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.*;

class TasksTest {

    @Test
    void incoming() {
        ArrayTaskListImpl taskList = new ArrayTaskListImpl();
        System.out.println("----- Task List --------");
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i, LocalDateTime.now().plusHours((long)i)));
        }
        taskList.add(new TaskImpl("Test repetitive task 1",LocalDateTime.now().minusHours(10),LocalDateTime.now().minusHours(5),2));
        taskList.add(new TaskImpl("Test repetitive task 2",LocalDateTime.now(),LocalDateTime.now().plusHours(5),1));
        taskList.add(new TaskImpl("Test repetitive task 3",LocalDateTime.now().plusHours(5),LocalDateTime.now().plusHours(10),3));
        taskList.add(new TaskImpl("Test repetitive task 4",LocalDateTime.now().plusHours(3),LocalDateTime.now().plusHours(20),1));
        for(int i = 6 ; i <= 15 ; i++){
            taskList.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours((long)i)));
        }
        // Active tasks
        for(int i = 0 ; i < taskList.size() ; i++) {
            if(i<12){
                taskList.getTask(i).setActive(true);
            }
            System.out.println("Index: " + i + ". " + taskList.getTask(i).getTitle());
        }
        System.out.println("----- Task list beyond Now and Now plus 5 hours --------");
        Iterator<Task> incoming = Tasks.incoming(taskList.iterator(), LocalDateTime.now(),LocalDateTime.now().plusHours(5)) ;
        int tam = 0;
        while (incoming.hasNext()){
            tam++;
            Task task = incoming.next();
            System.out.println(task.getTitle());
        }
        assertEquals(6,tam);
    }

    @Test
    void calendar() {
        ArrayTaskListImpl taskList = new ArrayTaskListImpl();
        LocalDateTime now = LocalDateTime.now();
        for(long i = 0 ; i <= 10 ; i++){
            Task task = new TaskImpl("Test task " + i, now.plusHours(i));
            task.setActive(true);
            taskList.add(task);
        }
        for(long i = 4 ; i <= 7 ; i++){
            Task task = new TaskImpl("Test task double " + i, now.plusHours(i));
            task.setActive(true);
            taskList.add(task);
        }
        Task task1 = new TaskImpl("Test task repetitive 1 " , now, now.plusHours(10), 3);
        task1.setActive(true);
        taskList.add(task1);
        Task task2 = new TaskImpl("Test task repetitive 2 " , now.minusHours(3), now.plusHours(5), 2);
        task2.setActive(true);
        taskList.add(task2);
        Task task3 = new TaskImpl("Test task repetitive 3 " , now.plusHours(15), now.plusHours(20), 3);
        task3.setActive(true);
        taskList.add(task3);
        SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(taskList.iterator(), now, now.plusHours(7));
        System.out.println("Number of dates: " + calendar.keySet().size());
        for (LocalDateTime dateTime : calendar.keySet()){
            System.out.println("Date: " + dateTime);
            System.out.println("Number of tasks: " + calendar.get(dateTime).size());
            calendar.get(dateTime).stream().forEach(System.out::println);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
}