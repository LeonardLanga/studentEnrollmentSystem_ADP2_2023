
package za.ac.cput.studentenrolmentsystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Leonard
 */
public class StudentEnrolmentServer 
{
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static ServerSocket server;
    private static Socket socket;
    
    private Object receivedObject;
    private StudentEnrolmentDao dao = new StudentEnrolmentDao();
    
    
    public StudentEnrolmentServer()
    {
        try{
            server = new ServerSocket(5679,1);
            System.out.println("Server is listening..........");
            
            socket = server.accept();
            System.out.println("Now moving on to first step........");
            
            getStreams();
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
        
    }
    
    public void getStreams() throws IOException
    {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }
    
    public void processClient()
    {
        while(true)
        {
            try
            {
                receivedObject = in.readObject();
                if (receivedObject instanceof String && ((String)receivedObject).startsWith("1"))
                {
                    String userDetails = (String)receivedObject;
                    
                    String splitter[] =  userDetails.split("#");
                    
                    String choice = splitter[0];
                    String txtId = splitter[1];
                    String usersPassword = splitter[2];
                    String usersRole = splitter[3];
                    
                    int usersId = Integer.parseInt(txtId);
                    
                    boolean found = dao.verifyUser(usersId,usersPassword,usersRole);
                    out.writeObject(found);
                    out.flush();
                }
                else if(receivedObject instanceof String && ((String)receivedObject).equalsIgnoreCase("retrieveCourses"))
                {
                    ArrayList<Course> courseList = dao.getAllCourses();
                    out.writeObject(courseList);
                    out.flush();
                }
                else if(receivedObject instanceof String && ((String)receivedObject).startsWith("2"))
                {
                    String enrolDetails = (String)receivedObject;
                    
                    String splitter[] = enrolDetails.split("#");
                    
                    String choice = splitter[0];
                    String txtId = splitter[1];
                    String courseCode = splitter[2];
                    
                    int studentNumber = Integer.parseInt(txtId);
                    int response = dao.enrol(studentNumber,courseCode);
                    
                    out.writeObject(response);
                    out.flush();
                }
                else if(receivedObject instanceof String && ((String)receivedObject).startsWith("7")){
                    
                    String enrolDetails = (String)receivedObject;
                    
                    String splitter[] = enrolDetails.split("#");
                    
                    String choice = splitter[0];
                    String txtId = splitter[1];
                    String courseCode = splitter[2];
                    
                    int studentNumber = Integer.parseInt(txtId);
                    int response = dao.cancelEnrolment(studentNumber, courseCode);
                    
                    out.writeObject(response);
                    out.flush();
                }
                else if (receivedObject instanceof User) 
                {
                    User userObject = (User)receivedObject;
                    int response = dao.addUser(userObject);
                    out.writeObject(response);
                    out.flush();   
                }
                else if(receivedObject instanceof Course)
                {
                    Course courseObject = (Course)receivedObject;
                    int response = dao.addCourse(courseObject);
                    out.writeObject(response);
                    out.flush();
                }
                else if(receivedObject instanceof String && ((String)receivedObject).startsWith("3"))
                {
                    String retrieveCourse = (String)receivedObject;
                     
                    String splitter[] = retrieveCourse.split("#");
                    String choice = splitter[0];
                    String courseCode = splitter[1];
                    
                    ArrayList<Course> courseList = (ArrayList)dao.getCourse(courseCode);
                    out.writeObject(courseList);
                    out.flush();    
                }
                else if(receivedObject instanceof String && ((String)receivedObject).startsWith("4"))
                {
                    String retrieveCourse = (String)receivedObject;
                     
                    String splitter[] = retrieveCourse.split("#");
                    String choice = splitter[0];
                    String usersIdStr = splitter[1];
                    
                    int usersId = Integer.parseInt(usersIdStr);
                    
                    ArrayList<Course> userList = (ArrayList)dao.getUser(usersId);
                    out.writeObject(userList);
                    out.flush();    
                }
                else if(receivedObject instanceof String && ((String)receivedObject).equalsIgnoreCase("retrieveUsers"))
                {
                    ArrayList<User> userList = dao.getAllUsers();
                    out.writeObject(userList);
                    out.flush();
                }
                else if(receivedObject instanceof String && ((String)receivedObject).startsWith("5"))
                {
                    String retrieveUsersInCourse = (String)receivedObject;
                    
                    String splitter[] = retrieveUsersInCourse.split("#");
                    String choice = splitter[0];
                    String courseCode = splitter[1];
                    
                    ArrayList<User> userList = (ArrayList)dao.getAllUsersInCourse(courseCode);
                    out.writeObject(userList);
                    out.flush();
                }    
                else if(receivedObject instanceof String && ((String)receivedObject).startsWith("6"))
                {   
                    String retrieveStudEnrolCourseStr = (String)receivedObject;
                    
                    String splitter[] = retrieveStudEnrolCourseStr.split("#");
                    String choice = splitter[0];
                    String userNumber = splitter[1];
                    
                    int usersId = Integer.parseInt(userNumber);
                    
                    ArrayList<Course> courseList = dao.getUsersEnrolledCourse(usersId);
                    out.writeObject(courseList);
                    out.flush();
                }
                else if(receivedObject instanceof String && ((String)receivedObject).startsWith("8")){
                    String courseToDelete = (String)receivedObject;
                    
                    String splitter[] = courseToDelete.split("#");
                    String choice = splitter[0];
                    String courseCode = splitter[1];
                    
                    int response = dao.deteleCourse(courseCode);
                    
                    out.writeObject(response);
                    out.flush();
                }
                 else if(receivedObject instanceof String && ((String)receivedObject).startsWith("9")){
                    String userToDelete = (String)receivedObject;
                    
                    String splitter[] = userToDelete.split("#");
                    String choice = splitter[0];
                    String userId = splitter[1];
                    
                    int response = dao.deteleUser(Integer.parseInt(userId));
                    
                    out.writeObject(response);
                    out.flush();
                }
                else if (receivedObject instanceof String && ((String)receivedObject).startsWith("Exit"))
                {
                    closeConnection();
                    break;
                }

            }
            catch(ClassNotFoundException | IOException cnfe)
            {
                System.out.println(cnfe.getMessage());
            }
        }
    }
        
    public void closeConnection()
    {
        try
        {
            out.writeObject("Exit");
            out.flush();
            
            
            dao.closeConnection();
            out.close();
            in.close();
            socket.close();   
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
    
    public static void main(String[] args) {
        StudentEnrolmentServer serverObject = new StudentEnrolmentServer();
        serverObject.processClient();
    }
    
}
