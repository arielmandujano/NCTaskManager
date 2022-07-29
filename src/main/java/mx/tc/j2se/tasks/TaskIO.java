package mx.tc.j2se.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDateTime;

public class TaskIO {
    /**
     *  Writes using the specified format a list of task on an output stream.
     * @param tasks the list of task.
     * @param out the output stream.
     */
    public static void writeBinary(AbstractTaskList tasks, OutputStream out) {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {
            objectOutputStream.writeInt(tasks.size());
            for (Task task : tasks){
                objectOutputStream.writeInt(task.getTitle().length());
                objectOutputStream.writeChars(task.getTitle());
                objectOutputStream.writeBoolean(task.isActive());
                objectOutputStream.writeLong(task.getRepeatInterval());
                if(task.isRepeated()) {
                    objectOutputStream.writeObject(task.getStartTime());
                    objectOutputStream.writeObject(task.getEndTime());
                } else {
                    objectOutputStream.writeObject(task.getTime());
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing the task list in the output stream.");
        }
    }

    /**
     *  Read a list of task from an input stream using the specified format of the practice.
     * @param tasks the list of task.
     * @param in the input stream.
     */
    public static void readBinary(AbstractTaskList tasks, InputStream in) {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(in)) {
            Task newTask;
            int size = objectInputStream.readInt();
            for(int task = 0 ; task < size ; task++) {
                int length = objectInputStream.readInt();
                char[] title = new char[length];
                for(int i = 0 ; i < length ; i++) {
                    title[i] = objectInputStream.readChar();
                }
                boolean isActive = objectInputStream.readBoolean();
                long repeatInterval = objectInputStream.readLong();
                if(repeatInterval == 0L) {
                    LocalDateTime executionTime = (LocalDateTime) objectInputStream.readObject();
                    newTask = new TaskImpl(new String(title), executionTime);
                } else {
                    LocalDateTime startTime = (LocalDateTime) objectInputStream.readObject();
                    LocalDateTime endTime = (LocalDateTime) objectInputStream.readObject();
                    newTask = new TaskImpl(new String(title), startTime, endTime, repeatInterval);
                }
                newTask.setActive(isActive);
                tasks.add(newTask);
            }
        } catch (IOException e) {
            System.err.println("Error reading from stream.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error while reading object from stream.");
        }
    }

    /**
     * Writes a list of tasks on a given file.
     * @param tasks the list of tasks.
     * @param file the file to be writed.
     */
    public static void write(AbstractTaskList tasks, File file) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            writeBinary(tasks, fileOutputStream);
        } catch (FileNotFoundException e) {
            System.err.println("Error. File not found.");
        } catch (IOException e) {
            System.err.println("Error while writing.");
        }
    }

    /**
     * Read a list of tasks from a given file.
     * @param tasks the list of task.
     * @param file the file to be readed.
     */
    public static void read(AbstractTaskList tasks, File file) {
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            readBinary(tasks, fileInputStream);
        } catch (FileNotFoundException e) {
            System.err.println("Error. File not found.");
        } catch (IOException e) {
            System.err.println("Error while reading.");
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(BufferedWriter bufferedWriter = new BufferedWriter(out)) {
            gson.toJson(tasks,bufferedWriter);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        Gson gson = new GsonBuilder().create();
        AbstractTaskList auxTaskList = tasks instanceof LinkedTaskListImpl ? TaskListFactory.createTaskList(ListTypes.types.LINKED) : TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        try(BufferedReader bufferedReader = new BufferedReader(in)){
            auxTaskList = gson.fromJson(bufferedReader,tasks instanceof LinkedTaskListImpl ? LinkedTaskListImpl.class : ArrayTaskListImpl.class);
            auxTaskList.getStream().forEach(tasks::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        try(FileWriter fileWriter = new FileWriter(file)) {
            write(tasks, fileWriter);
        } catch (FileNotFoundException e) {
            System.err.println("Error. File not found.");
        } catch (IOException e) {
            System.err.println("Error while writing.");
        }
    }

    public static void readText(AbstractTaskList tasks, File file) {
        try(FileReader fileReader = new FileReader(file)) {
            read(tasks, fileReader);
        } catch (FileNotFoundException e) {
            System.err.println("Error. File not found.");
        } catch (IOException e) {
            System.err.println("Error while reading.");
        }
    }
}
