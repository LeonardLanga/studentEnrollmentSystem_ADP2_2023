package za.ac.cput.studentenrolmentsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class StudentEnrolmentDao {

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public StudentEnrolmentDao() 
    {
        try 
        {
            this.con = DBConnection.getConection();

        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());

        }

    }

    public boolean verifyUser( int userid, String password, String role) {
        boolean found = false;
        
        String sql = "SELECT * FROM systemusers WHERE usersid=? AND password=? AND role=?";
        
        try {
            System.out.println("we are in the verify user method");
            pstmt = con.prepareStatement(sql);
            
            System.out.println("The statement has been prepared");
            pstmt.setInt(1, userid);
            System.out.println("Entered userid: " + userid);
            
            pstmt.setString(2, password);
            System.out.println("Entered password:" + password);
            
            pstmt.setString(3, role);
            System.out.println("Entered role:" + role);
            
            rs = pstmt.executeQuery();
            
            found = rs.next();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                closePreparedStatement();
                closeResultSet();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        return found;
    }
    
    public int enrol(int studentNumber, String courseCode)
    {
        int ok = 0;
        
        String query = "SELECT * FROM studentcourse WHERE studentnumber=? AND coursecode=?";
        
        try
        {
            pstmt = con.prepareStatement(query);
            
            pstmt.setInt(1, studentNumber);
            System.out.println("Entered userid: " + studentNumber);
            
            pstmt.setString(2, courseCode);
            System.out.println("Selected courseCode: " + courseCode);
            
            rs = pstmt.executeQuery();
            
            if (!rs.next()) 
            { 
                System.out.println("We are in the insert if block.");
                String sql = "INSERT INTO StudentCourse (StudentNumber,CourseCode) VALUES (?,?)";
                pstmt = con.prepareStatement(sql);
                
                pstmt.setInt(1, studentNumber);
                pstmt.setString(2, courseCode);
                
                ok = pstmt.executeUpdate();
            }
            else
            {
                System.out.println("We are in the insert else block.");
                ok = 0; 
            }
        }
        catch(SQLException e)
        {
             System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                closePreparedStatement();
                closeResultSet();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        System.out.println("The value of ok : " + ok);
        return ok;
    }
    
    public ArrayList<Course> getAllCourses()
    {
        ArrayList<Course> courseList = new ArrayList<>();
        
        String query = "SELECT * FROM Course";
        
        try
        {
           pstmt = this.con.prepareStatement(query);
           rs = pstmt.executeQuery();  
           
            if(rs != null)
            {
                while(rs.next()){
                  courseList.add(new Course(rs.getString("CourseCode"),rs.getString("CourseTitle"), rs.getString("CourseDescription")));
                }
            }
           
        }
        catch(SQLException e)
        {
           System.out.println( e.getMessage());
        }
        finally
        {
            try
            {
                closePreparedStatement();
                closeResultSet();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        return courseList;
    }
    
    public int addUser(User userObject)
    {
        int ok = 0;
        String query = "INSERT INTO SystemUsers (UsersId,Name,LastName,Password,Role)VALUES (?,?,?,?,?)";
        
        try
        {
            pstmt = this.con.prepareStatement(query);
            
            pstmt.setInt(1, userObject.getUsersId());
            pstmt.setString(2, userObject.getUsersName());
            pstmt.setString(3, userObject.getUsersLastName());
            pstmt.setString(4, userObject.getUsersPassword());
            pstmt.setString(5, userObject.getRole());
            
            ok = pstmt.executeUpdate();
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                closePreparedStatement();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        }
        return ok;
    }
    
    public int addCourse(Course courseObject)
    {
        int ok = 0;
        String query = "INSERT INTO Course (CourseCode,CourseTitle,CourseDescription)VALUES (?,?,?)";
        
        try
        {
            pstmt = this.con.prepareStatement(query);
            
            pstmt.setString(1, courseObject.getCourseCode());
            pstmt.setString(2, courseObject.getCourseTitle());
            pstmt.setString(3, courseObject.getCourseDescription());
            
            ok = pstmt.executeUpdate();
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                closePreparedStatement();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        return ok;
    }
    
    public ArrayList<Course> getCourse(String courseCode)
    {
        ArrayList<Course> courseList = new ArrayList<>();
        
        String query = "SELECT * FROM Course WHERE CourseCode=?";
        
        try
        {
            pstmt = this.con.prepareStatement(query);
            pstmt.setString(1,courseCode);
            rs = pstmt.executeQuery();  
           
            if(rs != null)
            {
                while(rs.next()){
                  courseList.add(new Course(rs.getString("CourseCode"),rs.getString("CourseTitle"), rs.getString("CourseDescription")));
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println( e.getMessage());
        }
        finally
        {
            try
            {
                closePreparedStatement();
                closeResultSet();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        }
        return courseList;
    }
    
    public ArrayList<User> getUser(int userId)
    {
        ArrayList<User> userList = new ArrayList<>();
        
        String query = "SELECT * FROM SystemUsers WHERE UsersId=?";
        
        try
        {
            pstmt = this.con.prepareStatement(query);
            pstmt.setInt(1,userId);
            rs = pstmt.executeQuery();  
           
            if(rs != null)
            {
                while(rs.next()){
                  userList.add(new User(rs.getInt("UsersId"),rs.getString("Name"), rs.getString("LastName"),rs.getString("Password"),rs.getString("Role")));
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println( e.getMessage());
        }
        finally
        {
            try
            {
                closePreparedStatement();
                closeResultSet();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        return userList;
    }
    
    public ArrayList<User> getAllUsers()
    {
        ArrayList<User> userList = new ArrayList<>();
        
        String query = "SELECT * FROM SystemUsers";
        
        try
        {
            pstmt = this.con.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            if (rs != null)
            {
                while(rs.next())
                {
                    userList.add(new User(rs.getInt("UsersId"),rs.getString("Name"),rs.getString("LastName"),rs.getString("Password"),rs.getString("Role")));
                } 
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                closePreparedStatement();
                closeResultSet();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        return userList;
    }
    
    public ArrayList<User>  getAllUsersInCourse(String courseCode)
    {
        ArrayList<User> userList = new ArrayList<>();
        ArrayList<Integer> userIds = new ArrayList<>();
        
        String query = "SELECT * FROM StudentCourse WHERE CourseCode=?";
        
        try
        {
            pstmt = this.con.prepareStatement(query);
            pstmt.setString(1, courseCode);
            rs = pstmt.executeQuery();
            
            if (rs != null) {
                while(rs.next())
                {
                    userIds.add(rs.getInt("studentnumber"));
                }
                
            }
            
            System.out.println("UserId List : " + userIds.toString());
            
            for (int i = 0; i < userIds.size(); i++) 
            {
                int retrievedUserId = userIds.get(i);
                
                String sql = "SELECT * FROM SystemUsers WHERE UsersId=?";
                
                pstmt = this.con.prepareStatement(sql);
                pstmt.setInt(1, retrievedUserId);
                rs = pstmt.executeQuery();
                
                if (rs != null) 
                {
                    while(rs.next())
                    {
                        userList.add(new User(rs.getInt("usersId"),rs.getString("Name"),rs.getString("LastName"),rs.getString("Password"),rs.getString("Role")));
                    }
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                closePreparedStatement();
                closeResultSet();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        System.out.println("UserList" + userList.toString());
        return userList;
    }
    
    public ArrayList<Course> getUsersEnrolledCourse(int usersId)
    {
        ArrayList<Course> courseList = new ArrayList<>();
        ArrayList<String> courseCode = new ArrayList<>();
        
        String query = "SELECT * FROM StudentCourse WHERE StudentNumber=?";
        
        try
        {
            pstmt = this.con.prepareStatement(query);
            pstmt.setInt(1, usersId);
            rs = pstmt.executeQuery();
            
            if (rs != null)
            {
                while(rs.next())
                {
                    courseCode.add(rs.getString("CourseCode"));
                }
            }
            
            for (int i = 0; i < courseCode.size(); i++) 
            {
                String retrievedeCourseCode = courseCode.get(i);   
                
                String sql = "SELECT * FROM Course WHERE CourseCode=?";
                
                pstmt = this.con.prepareStatement(sql);
                pstmt.setString(1, retrievedeCourseCode);
                rs = pstmt.executeQuery();
                
                if(rs != null)
                {
                    while(rs.next())
                    {
                        courseList.add(new Course(rs.getString("CourseCode"),rs.getString("CourseTitle"),rs.getString("CourseDescription")));
                    }
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                closePreparedStatement();
                closeResultSet();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        return courseList;
    }
    
    public int cancelEnrolment(int userId, String couserCode){
        
        String query = "DELETE FROM StudentCourse WHERE StudentNumber=? AND CourseCode=?";
        int ok = 0;
        
        try
        {
            pstmt = this.con.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.setString(2, couserCode);
             
            ok = pstmt.executeUpdate();
              
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        finally
        {
            try
            {
                closePreparedStatement();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        return ok;
    }
    
    public int deteleCourse(String courseCode)
    {
        String query = "DELETE FROM Course WHERE CourseCode=?";
        int ok = 0;
        
        try
        {
            pstmt = this.con.prepareStatement(query);
            pstmt.setString(1, courseCode);
             
            ok = pstmt.executeUpdate();
              
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        finally
        {
            try
            {
                closePreparedStatement();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        return ok;
    }
    
    public int deteleUser(int userId)
    {
        String query = "DELETE FROM SystemUsers WHERE UsersId=?";
        int ok = 0;
        
        try
        {
            pstmt = this.con.prepareStatement(query);
            pstmt.setInt(1, userId);
             
            ok = pstmt.executeUpdate();
              
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        finally
        {
            try
            {
                closePreparedStatement();
            } 
            catch (SQLException e) 
            {
               System.out.println(e.getMessage());
            }
        
        }
        return ok;
    }
    
    public void closePreparedStatement() throws SQLException
    {
        if (pstmt != null) 
        {
            pstmt.close();
        }
    }
    
    public void closeResultSet() throws SQLException
    {
        rs.close();
    }
    
    public void closeConnection()
    {
        try{
        
            if(con != null)
            {
                con.close();
            }
        }
        catch (SQLException e) 
        {
           System.out.println(e.getMessage());
        }
    }
}
