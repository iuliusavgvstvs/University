import java.net.*;
import java.io.*;

public class server
{

  public static void main (String args[]) throws Exception
  {
    ServerSocket s = new ServerSocket (1234);
    byte b[] = new byte[100];

    while (true)
      {
	Socket c = s.accept ();
	  System.out.println ("Client connected!");
	  c.getInputStream ().read (b);
	  //System.out.println (new String (b));
	  String sir = new String(b);
	  System.out.println(sir.length());
	  int i, spatii=0;
	  for(i=0;i<sir.length();i++){
	  		if(sir.charAt(i)==' '){
				spatii++;
			}
		}
	  System.out.println(sir+ spatii);
	  c.close ();
      }
  }

}
