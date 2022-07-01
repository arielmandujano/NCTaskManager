package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedTaskListImplTest {

    @Test()
    void add() {
        AbstractTaskList list = new LinkedTaskListImpl();
        for(int i = 0 ; i < 10 ; i++){
            if(i%2 == 0) {
                list.add(new TaskImpl("Test: " + i , i));
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
                list.add(new TaskImpl("Test: " + i , i));
            } else  {
                list.add(new TaskImpl("Test: " + i , i, i+10, 1));
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
                list.add(new TaskImpl("Test: " + i , i));
            } else  {
                list.add(new TaskImpl("Test: " + i , i, i+10, 1));
            }
        }
        list.remove(list.getTask(0));
        assertEquals(9, list.size());
    }

    @Test
    void getTask() {
        Task task = new TaskImpl("Test ", 1);
        AbstractTaskList list = new LinkedTaskListImpl();
        list.add(task);
        assertEquals(task,list.getTask(0));
    }

    @Test
    void incoming() {
        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        System.out.println("----- Task List --------");
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,i));
        }
        taskList.add(new TaskImpl("Test repetitive task 1",5,10,2));
        taskList.add(new TaskImpl("Test repetitive task 2",1,3,1));
        taskList.add(new TaskImpl("Test repetitive task 3",8,12,3));
        taskList.add(new TaskImpl("Test repetitive task 4",10,20,1));
        for(int i = 6 ; i <= 15 ; i++){
            taskList.add(new TaskImpl("Test task " + i,i));
        }
        // Active tasks
        for(int i = 0 ; i < taskList.size() ; i++) {
            if(i<12){
                taskList.getTask(i).setActive(true);
            }
            System.out.println("Index: " + i + ". " + taskList.getTask(i).getTitle());
        }
        System.out.println("----- Task list beyond 3 and 10 --------");
        LinkedTaskListImpl incoming = (LinkedTaskListImpl) taskList.incoming(3,10);
        for(int i = 0 ; i < incoming.size() ; i++) {
            System.out.println("Index: " + i + ". " + incoming.getTask(i).getTitle());
        }
        assertEquals(7,incoming.size());
    }
}