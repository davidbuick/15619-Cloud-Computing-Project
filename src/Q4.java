
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Servlet implementation class Q2
 */
@WebServlet("/Q4")
public class Q4 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Q4() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
/*			 request format: 	GET /q4?userid=133710000
			 Example:			http://webservice_public_dns/q4?userid=133710000
			 
			 response format:	TEAMID,AWS_ACCOUNT_ID
								userid1
				                userid2	 
			 Example: 			WeCloud,1234-5678-9012
								1000205192
				 				1117007780
				 				*/
		Connection conn=null;
		try {
			/*
			 * parse request
			 * 
			 */
			String createrId=request.getParameter("userid");
			
			/*
			 * JDBC connection and query
			 */
			//Connection conn = getConnection();
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext
					.lookup("java:/comp/env");
			DataSource datasource = (DataSource) envContext
					.lookup("jdbc/LocalTestDB");
			conn = datasource.getConnection();
			
			Statement st = (Statement) conn.createStatement();
			
			String sql = "select distinct retweeter_id from q4 where creater_id="+createrId+" order by retweeter_id";
			ResultSet rs = st.executeQuery(sql);
			
		
			/*
			 * Response to the client
			 */
			response.setContentType("text/plain");
			PrintWriter pr = new PrintWriter(response.getOutputStream());
			pr.println("sun,7459-7584-3458");

			while (rs.next()) {
				long retweeter_id = rs.getLong("retweeter_id");
				pr.println(retweeter_id);
			}		

			pr.flush();
			pr.close();
			st.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			if (conn!=null)
			{
				try
				{
					conn.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
		}
	}

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/sun",
							"root", "root");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return con;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
