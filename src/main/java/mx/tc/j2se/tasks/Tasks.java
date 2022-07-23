package mx.tc.j2se.tasks;

import java.time.LocalDateTime;
import java.util.*;

public class Tasks {
    /**
     * This function returns an Iterator &lt Task &gt  object with the tasks that are
     * scheduled and active between the time interval from "from" to "to"
     * that are in the list that calls the function.
     * <p>
     *     If none of the tasks in the list are in the given interval then an empty task list is returned.
     * </p>
     * @param tasks An Iterable<Task> object.
     * @param start The starting point for the search for scheduled and active tasks.
     * @param end The ending point for the search for scheduled and active tasks.
     * @return A new AbstractTaskList object with the tasks between from and to.
     */
    public static Iterator<Task> incoming(Iterator<Task> tasks, LocalDateTime start, LocalDateTime end){
        if(end.isBefore(start) || tasks == null) {
            throw new IllegalArgumentException("Error, the initial (start) moment cannot be after the final moment (to). None of the parameters can be null.");
        }

        AbstractTaskList incomingTask = tasks instanceof LinkedTaskListImpl ? TaskListFactory.createTaskList(ListTypes.types.LINKED) : TaskListFactory.createTaskList(ListTypes.types.ARRAY);

        while (tasks.hasNext()){
            Task task = tasks.next();
            if(task.nextTimeAfter(start).isBefore(end) && !task.nextTimeAfter(start).isEqual(LocalDateTime.MIN)) {
                incomingTask.add(task);
            }
        }
        return incomingTask.iterator();
    }

    /**
     * This function returns a natural order sorted map, representing a calendar of task, where the keys
     * of the map are the dates when task are executed and for each date there are a set of task that are
     * executed at that time.
     * @param tasks an iterator of a list of task.
     * @param start a LocalDateTime object indicating the start time of the calendar.
     * @param end a LocalDateTime object indicating the end time of the calendar.
     * @return a SortedMap with the set of tasks executed on a set of specifics LocalDateTime objects.
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterator<Task> tasks, LocalDateTime start, LocalDateTime end){
        if(end.isBefore(start) || tasks == null) {
            throw new IllegalArgumentException("Error, the initial (from) moment cannot be after the final moment (to). None of the parameters can be null.");
        }

        Comparator<Task> taskComparator = Comparator.comparing(Task::getTitle);

        Iterator<Task> incomingTasks = Tasks.incoming(tasks,start,end);
        SortedMap<LocalDateTime, Set<Task>> calendar = new TreeMap<>();

        while (incomingTasks.hasNext()){
            Task task = incomingTasks.next();
            if(!task.isRepeated()){
                if(calendar.containsKey(task.getTime())) {
                    calendar.get(task.getTime()).add(task);
                } else {
                    Set<Task> newSet = new TreeSet<>(taskComparator);
                    newSet.add(task);
                    calendar.put(task.getTime(),newSet);
                }
            } else {
                for(LocalDateTime dateTime = task.nextTimeAfter(start) ; (dateTime.isBefore(end) || dateTime.isEqual(end)) && (dateTime.isBefore(task.getEndTime()) || dateTime.isEqual(task.getEndTime())) ; dateTime = dateTime.plusHours(task.getRepeatInterval())){
                    System.out.println(dateTime + " - " + task.getTitle());
                    if(calendar.containsKey(dateTime)) {
                        calendar.get(dateTime).add(task);
                    } else {
                        Set<Task> newSet = new TreeSet<>(taskComparator);
                        newSet.add(task);
                        calendar.put(dateTime,newSet);
                    }
                }
            }
        }
        return calendar;
    }
}
