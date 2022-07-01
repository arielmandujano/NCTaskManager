package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTaskListImplTest {

    @Test
    void add() {
        ArrayTaskListImpl taskList = new ArrayTaskListImpl();
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,i));
        }
        assertEquals(5,taskList.size());
    }

    @Test
    void remove() {
        ArrayTaskListImpl taskList = new ArrayTaskListImpl();
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,i));
        }
        boolean canRemove =  taskList.remove(taskList.getTask(0));
        System.out.println(taskList.size());
        assertTrue(canRemove);
    }

    @Test
    void removeFailed() {
        ArrayTaskListImpl taskList = new ArrayTaskListImpl();
        for(int i = 1 ; i <= 5 ; i++){
            taskList.add(new TaskImpl("Test task " + i,i));
        }
        boolean canRemove =  taskList.remove(new TaskImpl("Out of array",1));
        assertFalse(canRemove);
    }

    @Test
    void size() {
        ArrayTaskListImpl taskList = new ArrayTaskListImpl();
        for(int i = 1 ; i <= 50 ; i++){
            taskList.add(new TaskImpl("Test task " + i,i));
        }
        for(int i = 10 ; i <= 19 ; i++){
            taskList.remove(taskList.getTask(i));
        }
        assertEquals(40,taskList.size());
    }

    @Test
    void getTask() {
        ArrayTaskListImpl taskList = new ArrayTaskListImpl();
        for(int i = 1 ; i <= 50 ; i++){
            taskList.add(new TaskImpl("Test task " + i,i));
        }
        assertEquals("Test task 37",taskList.getTask(36).getTitle());
    }

    @Test
    void printTaskList() {
        ArrayTaskListImpl taskList = new ArrayTaskListImpl();
        System.out.println("----- List before remove --------");
        for(int i = 1 ; i <= 15 ; i++){
            taskList.add(new TaskImpl("Test task " + i,i));
        }
        for(int i = 0 ; i < taskList.size() ; i++) {
            System.out.println("Index: " + i + ". " + taskList.getTask(i).getTitle());
        }
        System.out.println("----- List after remove --------");
        for(int i = 6 ; i <= 11 ; i++){
            taskList.remove(taskList.getTask(6));
        }
        for(int i = 0 ; i < taskList.size() ; i++) {
            System.out.println("Index: " + i + ". " + taskList.getTask(i).getTitle());
        }
        assertEquals(9,taskList.size());
    }

    @Test
    void incoming() {
        ArrayTaskListImpl taskList = new ArrayTaskListImpl();
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
        AbstractTaskList incoming = (ArrayTaskListImpl) taskList.incoming(3,10);
        for(int i = 0 ; i < incoming.size() ; i++) {
            System.out.println("Index: " + i + ". " + incoming.getTask(i).getTitle());
        }
        assertEquals(7,incoming.size());
    }
}