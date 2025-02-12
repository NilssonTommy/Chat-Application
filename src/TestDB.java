public class TestDB{
public static void main(String[] args) {
    try{
       PortalConnection c = new PortalConnection();
       
       System.out.println(c.login("Test")); 
       pause();

    } catch (ClassNotFoundException e) {
        System.err.println("ERROR!\nYou do not have the Postgres JDBC driver (e.g. postgresql-42.5.1.jar) in your runtime classpath!");
     } catch (Exception e) {
        e.printStackTrace();
     }
  }
  
  
  
  public static void pause() throws Exception{
    System.out.println("PRESS ENTER");
    while(System.in.read() != '\n');
  }

}

