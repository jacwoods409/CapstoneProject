import java.rmi.NoSuchObjectException;
import java.util.EmptyStackException;
import java.util.LinkedList;

public class TaskService {

    private final DoubleLinkedList<Task1> tasks;
    protected DoubleLinkedList<Exception> serviceRunTimeErrors;
    private Exception exception;
    private Task1[] temp;

    public TaskService() {
        this.tasks = new DoubleLinkedList<>();  // creates list of tasks
        this.temp = new Task1[tasks.size()];

        this.serviceRunTimeErrors = new DoubleLinkedList<>();  // creates list of runtime errors

    }
    // the only way to add a task
    public boolean newTask(String taskID) throws Exception { // Default Constructor

        try {
            Task1 task = new Task1(taskID);      // If task initializer fails, an Illegal Argument Exception will be thrown due to an improper ID
            addTask(task);                  // If addTask fails an exception stating that the task ID was not unique will be thrown
            return true;
        } catch (Exception ex) {
            serviceRunTimeErrors.add(ex); // Saves exception to runTimeErrors list
            throw ex;

        }
    }

    public boolean newTask(String taskId, String taskName, String taskDescription) throws Exception { //Constructor
        Task1 task = null;
        if (taskId != null && taskId.length() == 10) {  // Checks taskID

            try {
                task = new Task1(taskId, taskName, taskDescription);  // If name and description inputs are improper, an IllegalArgumentException will be thrown
                addTask(task);
                return true;

            } catch (Exception ex) {
                serviceRunTimeErrors.add(ex);
                throw ex;
            }

        } else {
            IllegalArgumentException exc = new IllegalArgumentException("ERROR: The ID set for this task was invalid or NULL!");  // if taskID input fails, this will be thrown
            serviceRunTimeErrors.add(exc);
            throw exc;

        }


    }

    // making internal methods private further encapsulates the program from the user
    private boolean addTask(Task1 task) throws Exception {


        if (!checkTaskID(task)) {  // Checks if ID is unique

            if (tasks.add(task)) {  // Adds Task to task service list
                return true;

            } else {
                throw new Exception("ERROR: Task was unable to add to Task List");
            }
        } else {

            throw new NoSuchObjectException("ERROR: Task ID already exists in Task Service System!"); // If task ID already exists this will be thrown


        }
    }

    private boolean checkTaskID(Task1 task) { // Checks if TaskID is unique. This method is not in test class, but is tested every time a new task is added
        try {
            Task1 t = this.getTask(task.getID());
            return true; // Task already exists in the system
        } catch (NoSuchObjectException exc) {
            return false; // Task does not already exist in the system
        }
    }


    public boolean deleteTask(String taskID) throws Exception {
        try {
            Task1 t = getTask(taskID); // will throw noSuchObjectException if the Task Does not exist
            if (tasks.remove(t) && !tasks.contains(t)) {  // ensures that the task was properly deleted
                return true;
            } else {

                exception = new Exception("ERROR: Task was not deleted. Please Reference JAVA Linked List API for further details"); // Will throw an error message if task was not successfully deleted
                serviceRunTimeErrors.add(exception);
                throw exception;
            }
        } catch (NoSuchObjectException exc) {
            serviceRunTimeErrors.add(exc); // adds exception to serviceRunTimeError List
            throw exc;
        }

    } // retrieves all tasks fromt the service
    public DoubleLinkedList<Task1> getTasks()
    {
        DoubleLinkedList<Task1> t = tasks;
        return t;
    }

    public Task1 getTask(String id) throws NoSuchObjectException { // retrieves task from service list

        for (int i = 0; i<=tasks.size()-1; i++) {
            if (id.equalsIgnoreCase(tasks.get(i).getID())) {
                Task1 t = tasks.get(i);
                return t;
            }
        }
        throw new NoSuchObjectException("ERROR: Task does not exist!"); // if task is not found, a NoSuchObjectException Exception exists
    }

    //updates a task
    public boolean updateTask(String id, String name, String descript) throws Exception { // updates task info
        try {
            Task1 t = getTask(id);     // will throw an Exception if any of the set or get method fails. See corresponding method for Exception type.
            t.setName(name);
            t.setTaskDescript(descript);
            return true;
        } catch (Exception exc) {
            serviceRunTimeErrors.add(exc);
            throw exc;
        }

    }
    // Displays every every that occurred will program is running
    public String displayErrors() {  // Displays service runtime errors
        if (serviceRunTimeErrors.size() == 0) {   // Checks to make sure the list is not empty
            String s = "No Errors Have Been Thrown.";
            return s;
        }
        String title = "Errors Thrown During Runtime:";
        String elist="";
        for(int i= 0; i<=serviceRunTimeErrors.size()-1; i++)
        {
            elist= serviceRunTimeErrors.get(i).getMessage()+"\n";
        }
        elist= title +"\n"+serviceRunTimeErrors.toString();

        return elist;
    }
    // displays all tasks in the list
    public String displayTasks() {   // Displays Tasks in a list form
        if (tasks.size() == 0) {
            String s = "No Tasks Were Found In Service Module.";
            return s;
        }
        String task ="";
        String taskList="";
        String title = "Current Tasks:\n";
        int count =0;

        DLL_Iterator it = new DLL_Iterator(tasks);
        while(it.hasNext()) {

            Task1 te = (Task1) it.next().getElement();
            System.out.println(te.toString());


        }
        return title.concat(taskList);
    } // generic sort method that is called
    public void sort(String type) throws Exception {
        try {

            if (tasks.size() == 0) {
                throw new Exception("Task List is empty!");
            }
            temp = new Task1[tasks.size()];
            DLL_Iterator it = new DLL_Iterator(tasks);
            int i=0;
            while(it.hasNext()) {  // creates an array of tasks from the task list
                temp[i] = (Task1) it.next().getElement();
                i++;
            }
            if (temp.length == tasks.size()) {  // TaskList size>100 insertion sort is used
                if (temp.length >100) {
                    quickSort(temp, 0, temp.length - 1, type);
                } else {
                    insertionSort(type);
                }


                i=0;
                int k = tasks.size();
                tasks.clear();
                for(i=0; i<k;i++) {

                    this.tasks.add(temp[i]);  // adds sorted tasks back to the task list
                    // System.out.println(temp[i].getName()); used for testing purposes

                }

            }
            else
            {
                throw new Exception("Bad conversion");
            }
        } catch (Exception e) {
            throw e;
        }

    }
    // quick sort method when the number of tasks is over 100
    private void quickSort(Task1[] temp, int lowerIndex, int higherIndex, String type)
    {
        if(type.equalsIgnoreCase("name"))
        {
            int i = lowerIndex;
            int j =higherIndex;
            Task1 pivot = temp[lowerIndex+(higherIndex-lowerIndex)/2];
            while (i<=j) {

                while (temp[i].getName().compareToIgnoreCase(pivot.getName())<0) {
                    i++;
                }
                while (temp[j].getName().compareToIgnoreCase(pivot.getName())>0){
                    j--;
                }
                if (i<=j) {
                    Task1 t = temp[i];
                    temp[i]=temp[j];
                    temp[j]=t;
                    i++;
                    j--;
                }
            }

            if(lowerIndex <j) {
                quickSort(temp,lowerIndex,j,type);
            }
            if(i<higherIndex) {
                quickSort(temp,i, higherIndex, type);

            }}
        else if(type.equalsIgnoreCase("desc"))
        {
            int i = lowerIndex;
            int j =higherIndex;
            Task1 pivot = temp[lowerIndex+(higherIndex-lowerIndex)/2];
            while (i<=j) {

                while (temp[i].getTaskDescript().compareToIgnoreCase(pivot.getTaskDescript())<0) {
                    i++;
                }
                while (temp[j].getTaskDescript().compareToIgnoreCase(pivot.getTaskDescript())>0){
                    j--;
                }
                if (i<=j) {
                    Task1 t = temp[i];
                    temp[i]=temp[j];
                    temp[j]=t;
                    i++;
                    j--;
                }
            }

            if(lowerIndex <j) {
                quickSort(temp,lowerIndex,j,type);
            }
            if(i<higherIndex) {
                quickSort(temp,i, higherIndex, type);

            }}
    }


    // Insertion sort method
    private void insertionSort(String type)
    { if(type.equalsIgnoreCase("name"))
    {
        temp = new Task1[tasks.size()];
        for(int i =0; i<=tasks.size()-1; i++)
        {
            temp[i]=tasks.get(i);
        }
        for (int i = 0; i < temp.length-1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (temp[j].getName().compareTo(temp[j - 1].getName()) < 0) {
                    Task1 tempTask = temp[j];
                    temp[j] = temp[j - 1];
                    temp[j - 1] = tempTask;
                }
            }
        }
    }else if(type.equalsIgnoreCase("desc"))
    {
        for (int i = 0; i < temp.length-1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (temp[j].getTaskDescript().compareTo(temp[j - 1].getTaskDescript()) < 0) {
                    Task1 tempTask = temp[j];
                    temp[j] = temp[j - 1];
                    temp[j - 1] = tempTask;
                }
            }
        }
    }
    }
    public int tasksSize()
    {
        return tasks.size();
    }
}



