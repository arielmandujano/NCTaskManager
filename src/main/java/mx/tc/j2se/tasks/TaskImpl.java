package mx.tc.j2se.tasks;

/**
 *
 */
public class TaskImpl implements Task{

    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
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
    public TaskImpl(String title, int time) {
        this.title = title;
        this.time = time;
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
    public TaskImpl(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        // This is only to avoid problems when calculating the next execution of the task.
        this.end = end > start ? end : start + interval;
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
    public int getTime() {
        return isRepeated() ? start : time;
    }

    /**
     * This method overwrites or initialize the time property of the task,
     * it is recommended to use for non-repetitive task,
     * if a repetitive task uses this method it will be converted into a non-repetitive task.
     * @param time the new time.
     */
    @Override
    public void setTime(int time) {
        if (isRepeated()) {
            this.time = time;
            this.repeated = false;
            this.start = 0;
            this.end = 0;
            this.interval = 0;
        } else {
            this.time = time;
        }
    }

    /**
     * Returns an int describing the start time of a task,
     * it is recommended to use for repetitive task,
     * if a non-repetitive task uses this method, its execution time will be returned.
     * @return the start time of the task.
     */
    @Override
    public int getStartTime() {
        return isRepeated() ? start : time;
    }

    /**
     * Returns an int describing the end time of a task,
     * it is recommended to use for repetitive task,
     * if a non-repetitive task uses this method, its execution time will be returned.
     * @return the end time of the task.
     */
    @Override
    public int getEndTime() {
        return isRepeated() ? end : time;
    }

    /**
     * Returns an int describing the time interval of a task,
     * it is recommended to use for repetitive task,
     * if a non-repetitive task uses this method, 0 will be returned.
     * @return the time interval of the task.
     */
    @Override
    public int getRepeatInterval() {
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
    public void setTime(int start, int end, int interval) {
        if(isRepeated()) {
            this.start = start;
            this.end = end;
            this.interval = interval;
        } else {
            this.start = start;
            this.end = end;
            this.interval = interval;
            this.time = 0;
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
    public int nextTimeAfter(int current) {
        int executionTime;
        if(!isActive()) {
            executionTime = -1;
        } else if(!isRepeated()) {
            executionTime = current < time ? time : -1 ;
        } else {
            if(current >= end)
                executionTime = -1;
            else if(current < start)
                executionTime = start;
            else {
                int counter = start + interval;
                while(counter <= current) {
                    counter += interval;
                }
                executionTime = counter <= end ? counter : -1;
            }
        }
        return executionTime;
    }
}
