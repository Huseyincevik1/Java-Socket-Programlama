package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Client {

	   private Socket socket;
	   private BufferedReader bufferedReader;
	   private BufferedWriter bufferedWriter;
	public Client(Socket socket) {
		   try {
			   this.socket=socket;
			  this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			  this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		   } catch (IOException e) {
			System.out.println("Client hata verdi!");
			e.printStackTrace();
			closeMethod(socket,bufferedReader,bufferedWriter);
		}
	}

	public void receiveMessageFromServer(VBox vBox) {
		
		   new Thread(new Runnable() {

				@Override
				public void run() {
					while(socket.isConnected()) {
					
						try {
							String message = bufferedReader.readLine();
							SampleController.addMessage(message,vBox);
						} catch (IOException e) {
							System.out.println("Client mesaj alımında hata verdi!");
							e.printStackTrace();
							closeMethod(socket,bufferedReader,bufferedWriter);
							break;
						}
						
					}
					
				}}).start();
		   
	}

	public void sendMessageToServer(String message) {
		   try {
				  bufferedWriter.write(message);
				  bufferedWriter.newLine();
				  bufferedWriter.flush();
				
			   }catch(IOException e) {
				    System.out.println("Client mesaj gönderiminde hata verdi!");
					e.printStackTrace();
					closeMethod(socket,bufferedReader,bufferedWriter);
			   }
		
	}
	   public void closeMethod(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		   try {
			   if(socket != null)
			   {
				   socket.close();
			   }
			   if(bufferedReader != null)
			   {
				   bufferedReader.close();
			   }
			   if(bufferedWriter != null)
			   {
				   bufferedWriter.close();
			   }
			
		   }catch(IOException e) {
			   e.printStackTrace();
		   }
	   }

}
