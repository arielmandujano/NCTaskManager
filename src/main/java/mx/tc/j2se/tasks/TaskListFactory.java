package mx.tc.j2se.tasks;

public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        return type == ListTypes.types.ARRAY ? new ArrayTaskListImpl() : new LinkedTaskListImpl();
    }
}
