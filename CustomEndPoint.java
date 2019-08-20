package websocket.server;

import java.io.IOException;
import java.util.*;
import javax.websocket.*;
import javax.websocket.server.*;


@ServerEndpoint("/server")
public class CustomEndPoint 
{
 
     
  static List<Session> list = new ArrayList<Session>();
  static Map<Session,String[]> map = new HashMap<>();
   
   
  static
  {
     Thread1 rateThread=new Thread1();
     rateThread.start();
  }

@OnOpen
public void open(Session session) 
{
  list.add(session);
  System.out.println("session opened: "+session.getId());
}
 
@OnMessage
public void onMessage(Session session, String msg) throws IOException 
{
  String[] curs = msg.split("\\_");

    System.out.println("onMessage : "+msg);
   map.put(session, curs);
   System.out.println("onMessage : "+session.getId());
}


@OnError
public void error(Session session, Throwable t) 
{
  list.remove(session);
  map.remove(session);
  System.out.println("Error on session "+session.getId());  
}


@OnClose
public void closedConnection(Session session) 
{ 
  list.remove(session);
  map.remove(session);
  System.out.println("session closed: "+session.getId());
}
 


    public static void sendAll(String text1,String text2,String text3,String text4) throws IOException
    {
       
        Set<Session> sessions = map.keySet();
        Iterator sessionitr = sessions.iterator();
        while(sessionitr.hasNext())
        {
            Session sess = (Session)sessionitr.next();
            String[] curs = map.get(sess);
            for(String cur:curs)
            {
                if(cur.equalsIgnoreCase("USD"))
                {
                  sess.getBasicRemote().sendText(text1);
                }
                if(cur.equalsIgnoreCase("EUR"))
                {
                  sess.getBasicRemote().sendText(text2);
                }
                if(cur.equalsIgnoreCase("JPY"))
                {
                  sess.getBasicRemote().sendText(text3);
                }
                if(cur.equalsIgnoreCase("HKD"))
                {
                  sess.getBasicRemote().sendText(text4);
                }
            }            
        }                                         

    }
    
}





