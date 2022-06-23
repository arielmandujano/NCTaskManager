package mx.tc.j2se.tasks;

public class ArrayTaskListImpl implements ArrayTaskList{
    private Task[] tasks;

    /**
     * Default constructor of the ArrayTaskListImpl class.
     * This constructor creates an empty ArrayTaskListImpl object with no one task on the list.
     */
    public ArrayTaskListImpl() {
        this.tasks = null;
    }

    /**
     * This method adds an existing task to the end of the task list.
     * @param task the task to be added.
     */
    @Override
    public void add(Task task) {
        if(this.tasks == null){
            this.tasks = new Task[1];
            this.tasks[0] = task;
        } else {
            Task[] auxiliar = new Task[size()+1];
            System.arraycopy(this.tasks, 0, auxiliar, 0, size());
            auxiliar[size()] = task;
            this.tasks = auxiliar;
        }
    }

    /**
     * This method removes an existing task from the task list.
     * If the task specified as a parameter appears more than once,
     * then the first occurrence of the task in the list will be deleted.
     * @param task The task that is expected to be removed from the list.
     * @return A boolean value, where true indicates that the task was in
     * the list and was deleted and false indicates that the task did not
     * exist in the list and therefore could not be deleted.
     */
    @Override
    public boolean remove(Task task) {
        int indexTask = -1;
        boolean canRemove = false;
        for(int i = 0 ; i < size() ; i++){
            if(this.tasks[i] == task){
                indexTask = i;
                break;
            }
        }
        if(indexTask != -1) {
            canRemove = true;
            Task[] auxiliar = new Task[size()-1];
            System.arraycopy(this.tasks,0,auxiliar,0,indexTask);
            System.arraycopy(this.tasks,indexTask+1,auxiliar,indexTask,size()-indexTask-1);
            this.tasks = auxiliar;
        }
        return canRemove;

    }

    /**
     * This function returns an int representing the number of
     * tasks in the list, if the list is empty then it returns 0.
     * @return The int type size of the list.
     */
    @Override
    public int size() {
        return this.tasks == null ? 0 : this.tasks.length;
    }

    /**
     *  This function returns the task at the int index given as parameter,
     *  if the list is empty or the index is greater than the size of the
     *  list minus one then it returns null.
     * @param index The index of the task to be returned.
     * @return The task at the given position.
     */
    @Override
    public Task getTask(int index) {
        return this.tasks == null || index >= size() || index < 0 ? null : this.tasks[index];
    }

    /**
     * This function returns an ArrayTaskList object with the tasks that are
     * scheduled and active between the time interval from "from" to "to"
     * that are in the list that calls the function.
     * <p>
     *     If none of the tasks in the list are in the given interval then an empty task list is returned.
     * </p>
     * @param from The starting point for the search for scheduled and active tasks.
     * @param to The ending point for the search for scheduled and active tasks.
     * @return A new ArrayTaskList object with the tasks between from and to.
     */
    @Override
    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList incomingTask = new ArrayTaskListImpl();
        if(this.tasks != null){
            for(Task task: this.tasks){
                if(task.isRepeated() && task.nextTimeAfter(from) < to && task.nextTimeAfter(from) != -1){
                    incomingTask.add(task);
                } else if(!task.isRepeated() && from < task.getTime()  && task.getTime() < to && task.isActive()){
                    incomingTask.add(task);
                }
            }
        }
        return incomingTask;
    }
}
