package mx.tc.j2se.tasks;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {

    abstract void add(Task task);

    abstract boolean remove(Task task);

    abstract int size();

    abstract Task getTask(int index);

    /**
     * This function returns an AbstractTaskList object with the tasks that are
     * scheduled and active between the time interval from "from" to "to"
     * that are in the list that calls the function.
     * <p>
     *     If none of the tasks in the list are in the given interval then an empty task list is returned.
     * </p>
     * @param from The starting point for the search for scheduled and active tasks.
     * @param to The ending point for the search for scheduled and active tasks.
     * @return A new AbstractTaskList object with the tasks between from and to.
     */
    final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to){
        if(to.isBefore(from)) {
            throw new IllegalArgumentException("Error, the initial (from) moment cannot be after the final moment (to).");
        }
        AbstractTaskList incomingTask = this instanceof LinkedTaskListImpl ? TaskListFactory.createTaskList(ListTypes.types.LINKED) : TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        this.getStream().filter(task -> task.nextTimeAfter(from).isBefore(to)).filter(task -> !task.nextTimeAfter(from).isEqual(LocalDateTime.MIN)).forEach(incomingTask::add);
        return incomingTask;
    }

    /**
     * Compares if the two tasks list are equals.
     * @param o The task list to be compared.
     * @return true if they are the same or have the same values, false on the contrary.
     */
    @Override
    public boolean equals(Object o){
        boolean areEquals = false;
        AbstractTaskList taskList = (AbstractTaskList) o;
        if(taskList == null || this.getClass() != o.getClass()){
            areEquals = false;
        } else if(this == taskList){
            areEquals = true;
        } else {
            if(this.size() == taskList.size()){
                for(int i = 0 ; i < this.size() ; i++){
                    if(!this.getTask(i).equals(taskList.getTask(i))){
                        areEquals = false;
                        break;
                    }
                    areEquals = true;
                }
            } else {
                areEquals = false;
            }
        }
        return areEquals;
    }

    /**
     * Get the hash code for a task list.
     * @return The int hash code for the task list.
     */
    @Override
    public int hashCode(){
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task: this) {
            tasks.add(task);
        }
        return Objects.hash(this.size(),tasks);
    }

    /**
     * The string representation for the task list.
     * @return A string with the information of the task list.
     */
    @Override
    public String toString() {
        String taskList = "--> Task List\nSize: " + this.size();
        taskList += this instanceof ArrayTaskListImpl ? "\nType: Array task list \nTasks:\n" : "\nType: Linked array list \nTasks:\n";
        for (Task task: this) {
            taskList += task.toString() + "\n~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        }
        return taskList;
    }

    /**
     * Clones to the task list that executes the method.
     * @return A new object with the same values of the task list.
     */
    @Override
    public AbstractTaskList clone(){
        AbstractTaskList newTaskList = null;
        try {
            newTaskList = (AbstractTaskList) super.clone();
        } catch (CloneNotSupportedException e){
            System.err.println("Error: It is not possible to create a copy of this task list.");
        }
        return newTaskList;
    }

    /**
     * Streams the list of task. If the list is empty it throws a RuntimeException.
     * @return A stream for the task list.
     * @throws RuntimeException
     */
    public abstract Stream<Task> getStream() throws RuntimeException;
}
