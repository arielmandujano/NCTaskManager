package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskImplTest {

    @Test
    void voidConstructor() {
        TaskImpl task = new TaskImpl();
        assertEquals(0,task.getTime());
    }

    @Test
    void getTitle() {
        TaskImpl task = new TaskImpl("Test task 1", 5);
        assertEquals("Test task 1", task.getTitle());
    }

    @Test
    void setTitle() {
        TaskImpl task = new TaskImpl("Test task 1", 5);
        task.setTitle("Title 2");
        assertEquals("Title 2", task.getTitle());
    }

    @Test
    void isActive() {
        TaskImpl task = new TaskImpl("Test task 1", 5);
        assertFalse(task.isActive());
    }

    @Test
    void setActive() {
        TaskImpl task = new TaskImpl("Test task 1", 5);
        task.setActive(true);
        assertTrue(task.isActive());
    }

    @Test
    void getTime() {
        TaskImpl task = new TaskImpl("Test task 1", 5, 10, 4);
        assertEquals(5, task.getTime());
    }

    @Test
    void setTimeRepeated() {
        TaskImpl task = new TaskImpl("Example title", 5);
        task.setActive(true);
        task.setTime(6,30, 325);
        assertEquals(6, task.nextTimeAfter(5));
    }

    @Test
    void getStartTime() {
        TaskImpl task = new TaskImpl("Test task 1", 5, 10, 4);
        assertEquals(5, task.getStartTime());
    }

    @Test
    void getEndTime() {
        TaskImpl task = new TaskImpl("Test task 1", 5, 10, 4);
        assertEquals(10, task.getEndTime());
    }

    @Test
    void getRepeatInterval() {
        TaskImpl task = new TaskImpl("Test task 1", 8);
        assertEquals(0, task.getRepeatInterval());
    }

    @Test
    void testSetTime() {
        TaskImpl task = new TaskImpl("Test task 1", 5, 10, 4);
        task.setTime(2);
        assertEquals(2, task.getTime());
    }

    @Test
    void isRepeated() {
        TaskImpl task = new TaskImpl("Test task 1", 5, 10, 4);
        task.setTime(2);
        assertFalse(task.isRepeated());
    }

    @Test
    void nextTimeAfterRepetitiveInInterval() {
        TaskImpl repetitiveTask = new TaskImpl("Test repeated task",6, 20, 3);
        repetitiveTask.setActive(true);
        assertEquals(-1, repetitiveTask.nextTimeAfter(19));
    }

    @Test
    void nextTimeAfterRepetitiveBeforeStart() {
        TaskImpl repetitiveTask = new TaskImpl("Test repeated task",6, 20, 3);
        repetitiveTask.setActive(true);
        assertEquals(-1, repetitiveTask.nextTimeAfter(19));
    }

    @Test
    void nextTimeAfterNonRepetitive() {
        TaskImpl repetitiveTask = new TaskImpl("Test repeated task",10);
        repetitiveTask.setActive(true);
        assertEquals(10, repetitiveTask.nextTimeAfter(2));
    }
}