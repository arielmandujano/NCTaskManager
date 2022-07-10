package mx.tc.j2se.tasks;

import java.util.ArrayList;
import java.util.Objects;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {

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
    AbstractTaskList incoming(int from, int to){
        if(to <= from) {
            throw new IllegalArgumentException("Error, the initial (from) moment cannot be greater than or equal to the final moment (to).");
        }
        AbstractTaskList incomingTask = this instanceof LinkedTaskListImpl ? TaskListFactory.createTaskList(ListTypes.types.LINKED) : TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        for(int i = 0 ; i < size() ; i++){
            Task task = getTask(i);
            if(task.isRepeated() && task.nextTimeAfter(from) < to && task.nextTimeAfter(from) != -1){
                incomingTask.add(task);
            } else if(!task.isRepeated() && from < task.getTime()  && task.getTime() < to && task.isActive()){
                incomingTask.add(task);
            }
        }
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
}
