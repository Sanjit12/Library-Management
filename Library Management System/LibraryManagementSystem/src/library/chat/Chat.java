package library.chat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.applet.*;
import javax.swing.*;

public class Chat extends JApplet implements ActionListener,Runnable
{
	JButton b1,b2;
 	JTextField tf1,tf;
   	JTextArea ta;
   	ServerSocket ss;
  	Socket s;
	static BufferedReader br;
  	PrintWriter pw;
   	Thread t;     

	public Chat()
	{
      		tf1=new JTextField(15);
      		b1=new JButton("Send");
      		b1.addActionListener(this);
      		b2=new JButton("Close");
      		b2.addActionListener(this);
     	 	tf=new JTextField(15);
     		ta=new JTextArea(12,20);
     		JFrame f=new JFrame("SERVER");
     		f.setLayout(new FlowLayout());
     		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      		f.add(tf1);
      		f.add(ta); 
      		f.add(tf);
      		f.add(b1);
      		f.add(b2);
      
       	 	try{
            			ss=new ServerSocket(7000);
            			tf1.setText("WAITING FOR CONNECTION");
            			s=ss.accept();
           			tf1.setText("CHAT ENABLED");
          			// System.out.println(s);
            			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            			pw=new PrintWriter(s.getOutputStream(),true);
           		   }catch(Exception e) { }

     		t=new Thread(this);
      		t.start();
      		f.setSize(300,350);
      		f.setVisible(true);
      		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	}

	public void actionPerformed(ActionEvent ae)
   	{
    		  if(ae.getSource()==b1)
      		{
         			ta.append("[YOU #]: "+tf.getText()+"\n");
         			pw.println("[SERVER #]: "+tf.getText());
        			tf.setText("");
      		}

      		else if(ae.getSource()==b2)
     		 {
           			tf1.setText("CHAT DISABLED");
			pw.println("BYE");
			try{
				s.close();
			     }catch(Exception e) { }
			//System.exit(0);
      			     
  		 }
	}
	public void run()
   	{
    		 while(true)
     		{
     			 try{
          				String s1=br.readLine();
          				ta.append(s1+"\n");
				if(s1.equals("BYE"))
				{
					break;
				}
         			    }catch(Exception e) { }
     		} 
   	}
} 