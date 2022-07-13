package mx.tc.j2se.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ArrayTaskListImpl extends  AbstractTaskList{

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
        if(task == null) {
            throw new IllegalArgumentException("Error. Task cannot be null");
        }
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
        if(task == null) {
            throw new IllegalArgumentException("Error. Task cannot be null");
        }
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
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Error. Index is out of range");
        }
        return this.tasks[index];
    }

    @Override
    public Stream<Task> getStream() throws RuntimeException {
        if(this.size() == 0) {
            throw new RuntimeException("Error. The list to stream cannot be empty.");
        }
        Stream.Builder<Task> taskStreamBuilder = Stream.builder();
        for (Task task: this) {
            taskStreamBuilder.add(task);
        }
        Stream<Task> stream = taskStreamBuilder.build();
        return stream;
    }


    /**
     * Returns an iterator over elements of type {@code Task}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Task> iterator() {
        return new Itr();
    }

    /**
     * This class allows us to create multiple iterators for the same list.
     */
    private class Itr implements Iterator<Task> {
        int index = 0;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return index < size();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Task next() {
            if(index >= size()){
                throw new NoSuchElementException("There is not a next element.");
            }
            return tasks[index++];
        }
    }
}
