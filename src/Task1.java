import java.util.LinkedList;

public class Task1 {
    private String taskID;
    private String taskDescript;
    private String taskName;
    private final DoubleLinkedList<Exception> errors;


    public Task1(String ID) throws IllegalArgumentException {
        this.errors = new DoubleLinkedList<>();
        try {                                          // Default Constructor and only creates a Task with an I
            if (setID(ID)) {                          // Set ID will throw an Illegal Argument Exception if the ID does not meet the requirements
                this.taskDescript = "UNKNOWN";
                this.taskName = "UNKNOWN";     // The description and name will automatically be set to null
            }
        } catch (IllegalArgumentException ex) {
            throw ex;
        }

    }


    public Task1(String ID, String taskName, String descript) throws IllegalArgumentException {      // Complete Constructor
        this.errors = new DoubleLinkedList<>();                              // Initializes the error list, so we can see what Task has thrown which errors
        if (ID != null && ID.length() == 10) {
            try {
                setID(ID);
            } catch (Exception exc) {
                throw exc;
            }
            try {
                setName(taskName);
            } catch (IllegalArgumentException exc) {
            }
            try {
                setTaskDescript(descript);
            } catch (IllegalArgumentException exc) {
            }

            if (this.taskName.equalsIgnoreCase("UNKNOWN") || this.taskDescript.equalsIgnoreCase("UNKNOWN")) {
                throw new IllegalArgumentException("ERROR: One or more variables did not meet requirements!");
            }
        }
    }

    private boolean setID(String ID) throws IllegalArgumentException {   // Setter is private to limit access to task ID

        if (ID != null && ID.length() == 10) {     // Checks ID against requirements
            this.taskID = ID;
            return true;
        } else {
            IllegalArgumentException exc = new IllegalArgumentException("Error: The ID set for this task was invalid!");  // or throws an exception if it does not meet the requirements
            errors.add(exc);                               // adds the thrown exception to the list of errors thrown for this task

            throw exc;

        }
    }

    public String getID() {         // returns ID
        String id = this.taskID;    // stores to a second hand variable to protect it from back door entries
        return id;
    }

    public boolean setName(String name) throws IllegalArgumentException {  // Checks name against requirements

        if (name != null && name.length() <= 20) {

            this.taskName = name;
            return true;               // returns true so you know that the task name was successfully sent

        } else {
            this.taskName = "UNKNOWN";   // sets name as unknown if the one entered fails
            IllegalArgumentException e = new IllegalArgumentException("ERROR: Task Name entered does not meet requirements!"); // or throws an exception if it does not meet the requirements
            errors.add(e);       // adds exception to the list of errors
            throw (e);
        }

    }


    public String getName(){
        String name = this.taskName;    // returns name and uses temp variables to prevent back door entry
        return name;
    }

    public boolean setTaskDescript(String descript) {

        if (descript != null && descript.length() <= 50) {     // checks input against requirements and throws an Illegal Argument Exception if it fails
            this.taskDescript = descript;
            return true;
        } else {
            this.taskDescript = "UNKNOWN";        // Sets task as unknown if input fails
            IllegalArgumentException exc = new IllegalArgumentException("ERROR: The Description set for this task does not meet requirements!");
            errors.add(exc);    // saves exception and reason
            throw exc;
        }
    }

    public String getTaskDescript() {
        String descript = taskDescript;
        return descript;                  // returns Task Description, uses temp variable to prevent backdoor access
    }

    @Override
    public String toString() {  // Displays a task in an organized fashion
        String tsk = "Task ID: " + getID() + "\n" + "TASK NAME: " + getName() + "\n" + "TASK DESCRIPTION:" + getTaskDescript();

        return tsk;
    }
    public Task1 getTask()
    {
        Task1 tmp = this;
        return this;
    }

    public DoubleLinkedList<Exception> getErrorLog() {
        DoubleLinkedList<Exception> exceptionList = new DoubleLinkedList<>();
        for (int i =0; i<=errors.size()-1; i++)
        {
            exceptionList.add(errors.get(i));
        }
        return exceptionList;
    }


}
