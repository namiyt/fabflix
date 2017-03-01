package com.AutoComplete.sample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.json.JSONArray;
 
import com.DataSource.mysql.DataSource;
 
/**
 * Servlet implementation class AutoComplete
 */
 
public class AutoComplete extends HttpServlet {
private static final long serialVersionUID = 1L;

	public AutoComplete() 
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ArrayList<String> al=new ArrayList<String>();
		try
		{
			String[] title = request.getParameter("title").split(" ");
			String sql = "";
			DataSource ds=DataSource.getInstace();
			Connection conn = ds.getConnection();
			Statement stmt=conn.createStatement();
			
			if (title.length == 1) {
				sql="select title from movies where title LIKE '%"+title[0]+"%'";
			} else {
				sql="select title from movies where ";
				for (int i = 0; i < title.length-1; i++) {
					sql+= "title LIKE '%"+title[i]+"%' and ";
				}
				sql+= "title LIKE '"+title[title.length-1]+"%'";
			}
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(sql);
			while(rs.next())
			{
				al.add(rs.getString("title"));
			}
			rs.close();
			stmt.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		JSONArray json = new JSONArray(al);
		response.setContentType("application/json");
		response.getWriter().print(json);
	}
}
