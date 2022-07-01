package mx.tc.j2se.tasks;

public abstract class AbstractTaskList {

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
}
