import edu.uga.dawgtrades.control;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.control.LoginControl;
import edu.uga.dawgtrades.model.Item;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateAuctionCtrl{

	private Connection conn = null;
        private ObjectModel objectModel = null;
        private Persistence persistence = null;
        private String error="Error Unknown";
        private boolean hasError = false;
        private LoginControl ctrl = new LoginControl();
        
	private void connect() throws DTException{
                conn = DbUtils.connect();
                objectModel = new ObjectModelImpl();
                persistence = new PersistenceImpl(conn,objectModel);
                objectModel.setPersistence(persistence);
        }
        private void close(){
                try{
                        conn.close();
                }catch(Exception e){
                        System.err.println("Exception: "+e);
                }
        }
	
	

	public Item getItem(long id){
		try{
			this.connect();
			Item item = this.objectModel.createItem();
			item.setId(id);
			Iterator<Item> result = this.objectModel.findItem(item);
			while(result.hasNext()){
				item = result.next();
			}
			return item;				
		}catch(DTException e){
			this.error = e;
			this.hasError = true;
			return null;
		}finally{
			this.close();
		}
	
	}
	public boolean hasError(){
		return hasError;
	}
	public String getError(){
		return error;
	}

}
