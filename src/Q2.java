
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
@WebServlet("/Q2")
public class Q2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Q2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/*
		 * REST format: GET /q2?time=2013-10-02+00:00:00 Example:
		 * http://webservice_public_dns/q2?time=2013-10-02+00:00:00 response
		 * format: TEAMID,AWS_ACCOUNT_ID tweetid1:tweet_text1
		 * tweetid2:tweet_text2
		 * 
		 * Example: WeCloud,1234-5678-9012 999636450534195200:Happy New Year!
		 * 999636450534211584:Merry Christmas!
		 */
		Connection conn=null;
		try {
			/*
			 * parse request
			 */
			String time = request.getParameter("time");
			
			Timestamp ts = java.sql.Timestamp.valueOf(time);
			int second=(int) (ts.getTime()/1000);

			/*
			 * JDBC connection and query
			 */

			Context initContext = new InitialContext();
			Context envContext = (Context) initContext
					.lookup("java:/comp/env");
			DataSource datasource = (DataSource) envContext
					.lookup("jdbc/LocalTestDB");
			conn = datasource.getConnection();

			
			Statement st = (Statement) conn.createStatement();

			String sql = "select tweet_id, text from q2 where created_at="+ second+" order by tweet_id";
			ResultSet rs = st.executeQuery(sql);

			/*
			 * Response to the client
			 */
			response.setContentType("text/plain");
			PrintWriter pr = new PrintWriter(response.getOutputStream());
			pr.println("sun,7459-7584-3458");

			while (rs.next()) {
				long tweet_id = rs.getLong("tweet_id");
				String text = rs.getString("text");
				pr.println(tweet_id + ":" + text);

			}
			pr.flush();
			pr.close();
			st.close();
			rs.close();
			//conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
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
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sun", "root", "root");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		
		//con=Manager.getConnection();
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
