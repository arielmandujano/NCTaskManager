package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskImplTest {

    @Test
    void voidConstructor() {
        TaskImpl task = new TaskImpl();
        assertNull(task.getTime());
    }

    @Test
    void getTitle() {
        TaskImpl task = new TaskImpl("Test task 1", LocalDateTime.now());
        assertEquals("Test task 1", task.getTitle());
    }

    @Test
    void setTitle() {
        TaskImpl task = new TaskImpl("Test task 1", LocalDateTime.now());
        task.setTitle("Title 2");
        assertEquals("Title 2", task.getTitle());
    }

    @Test
    void isActive() {
        TaskImpl task = new TaskImpl("Test task 1", LocalDateTime.now());
        assertFalse(task.isActive());
    }

    @Test
    void setActive() {
        TaskImpl task = new TaskImpl("Test task 1", LocalDateTime.now());
        task.setActive(true);
        assertTrue(task.isActive());
    }

    @Test
    void getTime() {
        TaskImpl task = new TaskImpl("Test task 1", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(10), 4);
        assertEquals(LocalDateTime.now().plusHours(5), task.getTime());
    }

    @Test
    void setTimeRepeated() {
        TaskImpl task = new TaskImpl("Example title", LocalDateTime.now().plusHours(5));
        task.setActive(true);
        task.setTime(LocalDateTime.now().plusHours(6),LocalDateTime.now().plusHours(30), 325);
        assertEquals(LocalDateTime.now().plusHours(6), task.nextTimeAfter(LocalDateTime.now().plusHours(5)));
    }

    @Test
    void getStartTime() {
        TaskImpl task = new TaskImpl("Test task 1", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(10), 4);
        assertEquals(LocalDateTime.now().plusHours(5), task.getStartTime());
    }

    @Test
    void getEndTime() {
        TaskImpl task = new TaskImpl("Test task 1", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(10), 4);
        assertEquals(LocalDateTime.now().plusHours(10), task.getEndTime());
    }

    @Test
    void getRepeatInterval() {
        TaskImpl task = new TaskImpl("Test task 1", LocalDateTime.now().plusHours(8));
        assertEquals(0, task.getRepeatInterval());
    }

    @Test
    void testSetTime() {
        TaskImpl task = new TaskImpl("Test task 1", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(10), 4);
        task.setTime(LocalDateTime.now().plusHours(2));
        assertEquals(LocalDateTime.now().plusHours(2), task.getTime());
    }

    @Test
    void isRepeated() {
        TaskImpl task = new TaskImpl("Test task 1", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(10), 4);
        task.setTime(LocalDateTime.now().plusHours(2));
        assertFalse(task.isRepeated());
    }

    @Test
    void nextTimeAfterRepetitiveInInterval() {
        LocalDateTime now = LocalDateTime.now();
        TaskImpl repetitiveTask = new TaskImpl("Test repeated task",now.plusHours(6), now.plusHours(21), 3);
        repetitiveTask.setActive(true);
        assertEquals(now.plusHours(9), repetitiveTask.nextTimeAfter(now.plusHours(6)));
    }

    @Test
    void nextTimeAfterRepetitiveBeforeStart() {
        TaskImpl repetitiveTask = new TaskImpl("Test repeated task",LocalDateTime.now().plusHours(6), LocalDateTime.now().plusHours(20), 3);
        repetitiveTask.setActive(true);
        assertEquals(LocalDateTime.MIN, repetitiveTask.nextTimeAfter(LocalDateTime.now().plusHours(19)));
    }

    @Test
    void nextTimeAfterNonRepetitive() {
        TaskImpl repetitiveTask = new TaskImpl("Test repeated task",LocalDateTime.now().plusHours(10));
        repetitiveTask.setActive(true);
        assertEquals(LocalDateTime.now().plusHours(10), repetitiveTask.nextTimeAfter(LocalDateTime.now().plusHours(2)));
    }

    @Test
    void equals() {
        TaskImpl task1 = new TaskImpl("Test task", LocalDateTime.now().plusHours(10));
        TaskImpl task2 = new TaskImpl("Test task", LocalDateTime.now().plusHours(10));
        TaskImpl task3 = new TaskImpl("Test task", LocalDateTime.now().plusHours(10),LocalDateTime.now().plusHours(15),1);
        System.out.println("Same object: " + task1.equals(task1));
        System.out.println("Same task: " + task1.equals(task2));
        System.out.println("Different task: " + task1.equals(task3));
    }

    @Test
    void hash() {
        TaskImpl task1 = new TaskImpl("Test task", LocalDateTime.now().plusHours(10));
        TaskImpl task2 = new TaskImpl("Test task", LocalDateTime.now().plusHours(10));
        TaskImpl task3 = new TaskImpl("Test task", LocalDateTime.now().plusHours(10),LocalDateTime.now().plusHours(15),1);
        System.out.println("Task 1: " + task1.hashCode());
        System.out.println("Task 2: " + task2.hashCode());
        System.out.println("Task 3: " + task3.hashCode());
    }

    @Test
    void taskToString() {
        TaskImpl task1 = new TaskImpl("Test task 1", LocalDateTime.now().plusHours(10));
        TaskImpl task2 = new TaskImpl("Test task 2", LocalDateTime.now().plusHours(15));
        TaskImpl task3 = new TaskImpl("Test task 3", LocalDateTime.now().plusHours(10),LocalDateTime.now().plusHours(15),1);
        task2.setActive(true);
        System.out.println(task1);
        System.out.println(task2);
        System.out.println(task3);
    }

    @Test
    void cloneTask() {
        TaskImpl task1 = new TaskImpl("Test task 1", LocalDateTime.now().plusHours(10), LocalDateTime.now().plusHours(15), 2);
        TaskImpl task2 = task1.clone();
        System.out.println(task1.toString());
        System.out.println(task2.toString());
    }
}