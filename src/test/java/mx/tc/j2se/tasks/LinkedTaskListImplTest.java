package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LinkedTaskListImplTest {

    @Test()
    void add() {
        AbstractTaskList list = new LinkedTaskListImpl();
        for(int i = 0 ; i < 10 ; i++){
            if(i%2 == 0) {
                list.add(new TaskImpl("Test: " + i , LocalDateTime.now().plusHours(i)));
            } else  {
                Exception exception = assertThrows(IllegalArgumentException.class, () -> list.add(null));
                assertEquals(IllegalArgumentException.class, exception.getClass());
            }
        }
    }

    @Test
    void remove() {
        AbstractTaskList list = new LinkedTaskListImpl();
        for(int i = 0 ; i < 10 ; i++){
            if(i%2 == 0) {
                list.add(new TaskImpl("Test: " + i , LocalDateTime.now().plusHours(i)));
            } else  {
                list.add(new TaskImpl("Test: " + i , LocalDateTime.now().plusHours(i), LocalDateTime.now().plusHours(i+10), 1));
            }
        }
        list.remove(list.getTask(0));
        assertEquals(9, list.size());
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> list.remove(null));
        assertEquals(IllegalArgumentException.class,exception.getClass());
    }

    @Test
    void size() {
        AbstractTaskList list = new LinkedTaskListImpl();
        for(int i = 0 ; i < 10 ; i++){
            if(i%2 == 0) {
                list.add(new TaskImpl("Test: " + i , LocalDateTime.now().plusHours(i)));
            } else  {
                list.add(new TaskImpl("Test: " + i , LocalDateTime.now().plusHours(i), LocalDateTime.now().plusHours(i+10), 1));
            }
        }
        list.remove(list.getTask(0));
        assertEquals(9, list.size());
    }

    @Test
    void getTask() {
        Task task = new TaskImpl("Test ", LocalDateTime.now());
        AbstractTaskList list = new LinkedTaskListImpl();
        list.add(task);
        assertEquals(task,list.getTask(0));
    }

    @Test
    void incoming() {
        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        System.out.println("----- Task List --------");
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
        }
        taskList.add(new TaskImpl("Test repetitive task 1",LocalDateTime.now().plusHours(5),LocalDateTime.now().plusHours(10),2));
        taskList.add(new TaskImpl("Test repetitive task 2",LocalDateTime.now().plusHours(1),LocalDateTime.now().plusHours(3),1));
        taskList.add(new TaskImpl("Test repetitive task 3",LocalDateTime.now().plusHours(8),LocalDateTime.now().plusHours(12),3));
        taskList.add(new TaskImpl("Test repetitive task 4",LocalDateTime.now().plusHours(10),LocalDateTime.now().plusHours(20),1));
        for(int i = 6 ; i <= 15 ; i++){
            taskList.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
        }
        // Active tasks
        for(int i = 0 ; i < taskList.size() ; i++) {
            if(i<12){
                taskList.getTask(i).setActive(true);
            }
            System.out.println("Index: " + i + ". " + taskList.getTask(i).getTitle());
        }
        System.out.println("----- Task list beyond 3 and 10 --------");
        LinkedTaskListImpl incoming = (LinkedTaskListImpl) taskList.incoming(LocalDateTime.now().minusHours(5),LocalDateTime.now().plusHours(5));
        for(int i = 0 ; i < incoming.size() ; i++) {
            System.out.println("Index: " + i + ". " + incoming.getTask(i).getTitle());
        }
        assertEquals(7,incoming.size());
    }

    @Test
    void forEachIterated(){
        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
        }
        for(Task task: taskList) {
            System.out.println(task.getTitle());
        }
    }
    @Test
    void forEachIteratedMultipleIterators(){
        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
        }
        for(Task task: taskList) {
            System.out.println(task.getTitle());
            System.out.println("~~~~~~~~~~~~~~");
            for(Task task2: taskList){
                System.out.println(task2.getTitle());
            }
            System.out.println("~~~~~~~~~~~~~~");
        }
    }

    @Test
    void Equals(){
        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        LinkedTaskListImpl taskList2 = new LinkedTaskListImpl();
        LinkedTaskListImpl taskList3 = new LinkedTaskListImpl();
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
            taskList2.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
            taskList3.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
        }
        taskList3.getTask(4).setActive(true);
        System.out.println("Same list: " + taskList.equals(taskList));
        System.out.println("List with same tasks: " + taskList.equals(taskList2));
        System.out.println("Different lists: " + taskList.equals(taskList3));
    }

    @Test
    void Hash() {
        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        LinkedTaskListImpl taskList2 = new LinkedTaskListImpl();
        LinkedTaskListImpl taskList3 = new LinkedTaskListImpl();
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
            taskList2.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
            taskList3.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
        }
        taskList3.getTask(4).setActive(true);
        System.out.println("Task List 1: " + taskList.hashCode());
        System.out.println("Task List 2: " + taskList2.hashCode());
        System.out.println("Task List 3: " + taskList3.hashCode());
    }

    @Test
    void listToString(){
        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
        }
        System.out.println(taskList.toString());
    }

    @Test
    void cloneTaskList() {
        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
        }
        LinkedTaskListImpl taskList2 = (LinkedTaskListImpl) taskList.clone();
        System.out.println("Same object: " + (taskList == taskList2));
        System.out.println("Same tasks: " + (taskList.equals(taskList2)));
    }

    @Test
    void stream(){
        AbstractTaskList taskList = new ArrayTaskListImpl();
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,LocalDateTime.now().plusHours(i)));
        }
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Repetitive Test task " + i,LocalDateTime.now().plusHours(i),LocalDateTime.now().plusHours(i+5),1));
        }
        List<Task> taskList2 = taskList.getStream().filter(Task::isRepeated).collect(Collectors.toList());
        taskList2.stream().forEach(System.out::println);
    }

    @Test
    void streamEmptyList(){
        AbstractTaskList taskList = new ArrayTaskListImpl();
        Exception exception = assertThrows(RuntimeException.class, () -> taskList.getStream().filter(Task::isRepeated).collect(Collectors.toList()));
        assertEquals(RuntimeException.class, exception.getClass());
    }
}