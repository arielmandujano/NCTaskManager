package mx.tc.j2se.tasks;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 */

public class TaskImpl implements Task{

    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private long interval;
    private boolean active;
    private boolean repeated;


    /**
     * Default constructor of the TaskImpl class.
     * This constructor creates an empty TaskImpl object with all its properties initialized as 0 or false, as appropriate.
     */
    public TaskImpl() {

    }

    /**
     * Constructor of the TaskImpl class.
     * It is recommended to use this constructor when you want to create a new inactive non-repetitive task.
     * The numerical properties of the task, specific to repetitive tasks (start, time and interval), not indicated in this constructor are initialized to 0.
     * @param title This parameter initializes the task title.
     * @param time This parameter initializes the execution time of the task.
     */
    public TaskImpl(String title, LocalDateTime time) {
        if(time == null || title == null){
            throw new IllegalArgumentException("Error, time cannot be be null. Title cannot also be null.");
        }
        this.title = title;
        this.time = LocalDateTime.from(time);
        this.active = false;
        this.repeated = false;
    }

    /**
     * Constructor of the TaskImpl class.
     * It is recommended to use this constructor when you want to create a new inactive repetitive task.
     * The numerical properties of the task, specific to non-repetitive tasks (time),
     * not indicated in this constructor are initialized to 0.
     * <p>Since it is necessary that the end time is greater than the start time,
     * if when creating the object the end time is less than the start time,
     * the end property will take the same value as start plus the value of the interval.</p>
     * @param title This parameter initializes the task title.
     * @param start This parameter initializes the first execution time of the task.
     * @param end This parameter initializes the time when the task will stop being executed.
     * @param interval This parameter initializes the time interval necessary to repeat the execution of the task.
     */
    public TaskImpl(String title, LocalDateTime start, LocalDateTime end, long interval) {
        if(start.isAfter(end) || interval < 0 || title == null){
            throw new IllegalArgumentException("Error the starting time (start) cannot be after the ending time (end). Time interval must be more than 0. None of the parameters can be null");
        }
        this.title = title;
        this.start = LocalDateTime.from(start);
        this.end = LocalDateTime.from(end);
        this.interval = interval;
        this.active = false;
        this.repeated = true;
    }

    /**
     * Returns a String object whose value is the title of the task when it has
     * a value specified for its title property, otherwise it returns null.
     * @return the title of the task.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * This method overwrites the title property of the task,
     *  it can be used to change the title of a task or initialize it if it was
     *  not specified at the time of creating it.
     * @param title the new title of the task.
     */
    @Override
    public void setTitle(String title) {
        if(title == null) {
            throw new IllegalArgumentException("Title cannot be null.");
        }
        this.title = title;
    }

    /**
     * Returns a boolean describing the status of the task, whether it is active or not.
     * @return the active value of the task (true = active, false = inactive).
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * This method overwrites the active property of the task,
     * it can be used to change the active status of a task.
     * @param active the new active status of the task (true = active, false = inactive).
     */
    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns an int describing the time the task will be executed,
     * if the task is repetitive it returns start.
     * @return the time the task will be executed.
     */
    @Override
    public LocalDateTime getTime() {
        return isRepeated() ? start : time;
    }

    /**
     * This method overwrites or initialize the time property of the task,
     * it is recommended to use for non-repetitive task,
     * if a repetitive task uses this method it will be converted into a non-repetitive task.
     * @param time the new time.
     */
    @Override
    public void setTime(LocalDateTime time) {
        if(time == null) {
            throw new IllegalArgumentException("Error. Time cannot be null.");
        }
        if (isRepeated()) {
            this.time = LocalDateTime.from(time);
            this.repeated = false;
            this.start = null;
            this.end = null;
            this.interval = 0;
        } else {
            this.time = LocalDateTime.from(time);
        }
    }

    /**
     * Returns an int describing the start time of a task,
     * it is recommended to use for repetitive task,
     * if a non-repetitive task uses this method, its execution time will be returned.
     * @return the start time of the task.
     */
    @Override
    public LocalDateTime getStartTime() {
        return isRepeated() ? start : time;
    }

    /**
     * Returns an int describing the end time of a task,
     * it is recommended to use for repetitive task,
     * if a non-repetitive task uses this method, its execution time will be returned.
     * @return the end time of the task.
     */
    @Override
    public LocalDateTime getEndTime() {
        return isRepeated() ? end : time;
    }

    /**
     * Returns an int describing the time interval of a task,
     * it is recommended to use for repetitive task,
     * if a non-repetitive task uses this method, 0 will be returned.
     * @return the time interval of the task.
     */
    @Override
    public long getRepeatInterval() {
        return isRepeated() ? interval : 0;
    }

    /**
     * This method overwrites or initialize the start, end and interval properties of the task,
     * it is recommended to use for repetitive task,
     * if a non-repetitive task uses this method it will be converted into a repetitive task.
     * @param start the start time of the task.
     * @param end the end time of the task.
     * @param interval the time interval of the task.
     */
    @Override
    public void setTime(LocalDateTime start, LocalDateTime end, long interval) {
        if(start.isAfter(end) || interval < 0){
            throw new IllegalArgumentException("Error the starting time (start) cannot be after the ending time (end). Time interval must be more than 0. Start and end time cannot be null.");
        }
        if(isRepeated()) {
            this.start = LocalDateTime.from(start);
            this.end = LocalDateTime.from(end);
            this.interval = interval;
        } else {
            this.start = LocalDateTime.from(start);
            this.end = LocalDateTime.from(end);
            this.interval = interval;
            this.time = null;
            this.repeated = true;
        }
    }

    /**
     * Returns a boolean describing the repetition status of the task,
     * whether it is a repetitive task or a non-repetitive task.
     * @return the repetition status of the task (true = repetitive task, false = non-repetitive task).
     */
    @Override
    public boolean isRepeated() {
        return this.repeated;
    }

    /**
     * This method revises the next time of task execution based on a given time.
     * <p>
     *     For completed or inactive tasks the method returns -1.
     * </p>
     * <p>
     *     For those active and non-repetitive tasks the method will return the task
     *     execution time if it has not yet occurred, otherwise it will be considered as
     *     a completed task and will return a -1.
     * </p>
     * <p>
     *     For those tasks that are active and repetitive there are three possibilities:
     *     <ul>
     *          <li> If the time at which the task is no longer to be repeated has been reached (end property),
     *          then it will be considered as a completed task and will return a -1. </li>
     *          <li> If the time being queried has not yet been reached the time at which
     *          the task should start will return the time of the first execution of the task. </li>
     *          <li> If the time being queried is within the interval between the start and end of the task,
     *          the next task execution time from the queried time will be returned. </li>
     *     </ul>
     * </p>
     * @param current the time from which the next execution will be found.
     * @return the next execution time of the task after a given time.
     */
    @Override
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (current == null){
            throw new IllegalArgumentException("Error. Current time cannot be null.");
        }
        LocalDateTime executionTime;
        if(!isActive()) {
            executionTime = LocalDateTime.MIN;
        } else if(!isRepeated()) {
            executionTime = current.isBefore(time) ? time : LocalDateTime.MIN ;
        } else {
            if(current.isAfter(end))
                executionTime = LocalDateTime.MIN;
            else if(current.isBefore(start))
                executionTime = start;
            else {
                LocalDateTime counter = start.plusHours(interval);
                while(counter.isBefore(current) || counter.isEqual(current)) {
                    counter = counter.plusHours(interval);
                }
                executionTime = counter.isBefore(end) ? counter : LocalDateTime.MIN;
            }
        }
        return executionTime;
    }

    /**
     * Compares if the two tasks are equals.
     * @param o The task to be compared.
     * @return true if they are the same or have the same values, false on the contrary.
     */
    @Override
    public boolean equals(Object o){
        boolean areEquals = false;
        Task task = (Task)o;
        if(task == null || this.getClass() != o.getClass()){
            areEquals = false;
        }
        if(this == task || (this.isActive() == task.isActive() && this.isRepeated() == task.isRepeated() && this.getStartTime() == task.getStartTime() && this.getEndTime() == task.getEndTime() && this.getRepeatInterval() == task.getRepeatInterval() && this.getTime() == task.getTime() && this.getTitle().equals(task.getTitle()))){
            areEquals = true;
        } else {
            areEquals = false;
        }
        return areEquals;
    }

    /**
     * Get the hash code for a task.
     * @return The int hash code for the task.
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.title,this.repeated,this.time,this.active,this.start,this.end,this.end);
    }

    /**
     * The string representation for the task.
     * @return A string with the information of the task.
     */
    @Override
    public String toString() {
        return this.isRepeated() ?
                "--> Task\n" +
                "Title: " + this.getTitle() +
                "\nState: " + (this.isActive() ? "Active" : "Inactive") +
                "\nType: Repetitive" +
                "\nStart time: " + this.getStartTime() +
                "\nEnd time: " + this.getEndTime() +
                "\nRepeat interval: " + this.getRepeatInterval():
                "--> Task\n" +
                "Title: " + this.getTitle() +
                "\nState: " + (this.isActive() ? "Active" : "Inactive") +
                "\nType: Non-Repetitive" +
                "\nExecution time: " + this.getTime();
    }

    /**
     * Clones to the task that executes the method.
     * @return A new object with the same values of the task.
     */
    @Override
    public TaskImpl clone(){
        TaskImpl newTask = null;
        try {
            newTask = (TaskImpl) super.clone();
        } catch (CloneNotSupportedException e){
            System.err.println("Error: It is not possible to create a copy of this task.");
        }
        return newTask;
    }
}
