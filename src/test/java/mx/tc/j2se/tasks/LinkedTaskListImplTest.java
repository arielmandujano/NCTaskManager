package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedTaskListImplTest {

    @Test()
    void add() {
        LinkedTaskList list = new LinkedTaskListImpl();
        for(int i = 0 ; i < 10 ; i++){
            if(i%2 == 0) {
                list.add(new TaskImpl("Test: " + i , i));
            } else  {
                Exception exception = assertThrows(NullPointerException.class, () -> list.add(null));
                assertEquals(NullPointerException.class, exception.getClass());
            }
        }
    }

    @Test
    void remove() {
        LinkedTaskList list = new LinkedTaskListImpl();
        for(int i = 0 ; i < 10 ; i++){
            if(i%2 == 0) {
                list.add(new TaskImpl("Test: " + i , i));
            } else  {
                list.add(new TaskImpl("Test: " + i , i, i+10, 1));
            }
        }
        list.remove(list.getTask(0));
        assertEquals(9, list.size());
        Exception exception = assertThrows(NullPointerException.class, ()-> list.remove(null));
        assertEquals(NullPointerException.class,exception.getClass());
    }

    @Test
    void size() {
    }

    @Test
    void getTask() {
    }

    @Test
    void incoming() {
    }
}