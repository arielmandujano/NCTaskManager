package mx.tc.j2se.tasks;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Task extends Cloneable, Serializable {

    String getTitle();

    void setTitle(String title);

    boolean isActive();

    void setActive(boolean active);

    LocalDateTime getTime();

    void setTime(LocalDateTime time);

    LocalDateTime getStartTime();

    LocalDateTime getEndTime();

    long getRepeatInterval();

    void setTime(LocalDateTime start, LocalDateTime end, long interval);

    boolean isRepeated();

    LocalDateTime nextTimeAfter(LocalDateTime current);

}
