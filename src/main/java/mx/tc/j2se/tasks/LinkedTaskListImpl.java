package mx.tc.j2se.tasks;

public class LinkedTaskListImpl implements LinkedTaskList{

    private Node start;
    private Node end;
    private int size;

    /**
     * The Node class is an Inner Non-Static Class that allows us
     * to create the nodes for the implementation of a double-linked list of tasks.
     */
    private class Node {
        private Node previous;
        private Node next;
        private Task task;

        /**
         * Default constructor of a Node object.
         */
        public Node(){

        }

        /**
         * This method sets the previous node for the node that executes it.
         * @param previous The new previous node.
         */
        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        /**
         * This method sets the next node for the node that executes it.
         * @param next The new next node.
         */
        void setNext(Node next) {
            this.next = next;
        }

        /**
         * This method sets up the task for the node that executes it.
         * @param task The new task for the node.
         */
        void setTask(Task task) {
            this.task = task;
        }

        /**
         * This method gets the previous node for the node that executes it.
         * @return The previous node in the list.
         */
        Node getPrevious() {
            return previous;
        }

        /**
         * This method gets the next node for the node that executes it.
         * @return The next node in the list.
         */
        Node getNext() {
            return next;
        }

        /**
         * This method gets the task in the node that executes it
         * @return The task in the node.
         */
        Task getTask() {
            return task;
        }
    }

    /**
     * Default constructor for the LinkedTaskListImpl class.
     */
    public LinkedTaskListImpl() {

    }

    /**
     * This method creates a new node at the end of the task list and sets an existing task for this new node.
     * @param task the task to be added.
     */
    @Override
    public void add(Task task) {
        if(task == null) {
            throw new IllegalArgumentException("Error. Task cannot be null");
        }
        Node node = new Node();
        node.setTask(task);
        if(start == null) {
            this.start = node;
        } else {
            this.end.setNext(node);
            node.setPrevious(this.end);
        }
        this.end = node;
        size++;
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
        boolean inTheList = false;
        for(Node node = start ; node != null ; node = node.getNext()){
            if(node.getTask() == task) {
                inTheList = true;
                if(node == start) {
                    this.start = node.getNext();
                } else if(node == end) {
                    this.end = node.getPrevious();
                } else {
                    node.getPrevious().setNext(node.getNext());
                    node.getNext().setPrevious(node.getPrevious());
                }
                this.size--;
                break;
            }
        }
        return inTheList;
    }

    /**
     * This function returns an int representing the number of
     * tasks in the list, if the list is empty then it returns 0.
     * @return The int type size of the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     *  This function returns the task at the int index given as parameter.
     * @param index The index of the task to be returned.
     * @return The task at the given position.
     */
    @Override
    public Task getTask(int index){
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Error. Index is out of range");
        }
        Node auxiliar = start;
        for(int i = 0 ; i < index ; i++) {
            auxiliar = auxiliar.getNext();
        }
        return auxiliar.getTask();
    }

    /**
     * This function returns an LinkedTaskList object with the tasks that are
     * scheduled and active between the time interval from "from" to "to"
     * that are in the list that calls the function.
     * <p>
     *     If none of the tasks in the list are in the given interval then an empty task list is returned.
     * </p>
     * @param from The starting point for the search for scheduled and active tasks.
     * @param to The ending point for the search for scheduled and active tasks.
     * @return A new LinkedTaskList object with the tasks between from and to.
     */
    @Override
    public LinkedTaskList incoming(int from, int to) throws IllegalArgumentException {
        if(from >= to) {
            throw new IllegalArgumentException("Error, the initial (from) moment cannot be greater than or equal to the final moment (to).");
        }
        LinkedTaskList incomingTask = new LinkedTaskListImpl();
        if(this.start != null){
            for(Node node = start ; node != null ; node = node.getNext()){
                Task task = node.getTask();
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
