package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.jsp.JspWriter;

//import jdk.nashorn.internal.ir.RuntimeNode.Request;


public class ShoppingCart {
	   
    ArrayList<Movie> movieList = new ArrayList<Movie>(); 
    public ShoppingCart() { }
    
    public void empty() {
    	movieList.clear();
    }

    public void add(Movie movie) {
    	for (int i = 0; i < movieList.size(); i++) {
    		Movie m = movieList.get(i);
    		if (m.getId() == movie.getId()) {
    			m.incQuantity();
    			return;
    		}
    	}
    	movieList.add(movie);
    }
    
    public void remove(Movie movie) {
    	for (int i = 0; i < movieList.size(); i++) {
    		Movie m = movieList.get(i);
    		if (m.getId() == movie.getId()) {
    			movieList.remove(m);
    			break;
    		}
    	}
    }
    
    public void update(Movie movie) {
    	for (int i = 0; i < movieList.size(); i++) {
    		Movie m = movieList.get(i);
    		if (m.getId() == movie.getId()) {
    			m.setQuantity(movie.getQuantity());
    			break;
    		}
    	}
    }

    public void display(JspWriter out) throws IOException {
    	out.println("<h2>Shopping Cart</h2>");
    	out.println("<table border=1>");
    	out.println("<tr><th>ID</th><th>Title</th><th>Quantity</th><th>Add</th><th>Sub</th><th>Remove</th></tr>");
    	
    	for (int i = 0; i < movieList.size(); i++) {
    		Movie movie = (Movie)movieList.get(i);
    		int quantityAdd = movie.getQuantity() + 1;
    		int quantitySub = movie.getQuantity() - 1;
    		out.println("<tr><td>"+movie.getId()+"</td>"+"<td>"+movie.getTitle()+"</td>"+
    				 "<td><input id='qty' value="+movie.getQuantity()+"></td><td>"
    						+ "<a href='updateQuantity.jsp?id="+movie.getId()+"&quantity="+quantityAdd+"'><input type='button' value='Add'/></a>"
    								+ "</td>"
    								+ "<td><a href='updateQuantity.jsp?id="+movie.getId()+"&quantity="+quantitySub+"'><input type='button' value='Subtract'/></a></td><td><a href='removeFromCart.jsp?id="+movie.getId()+"'>"
    										+ "<input type='button' value='Remove'/></a>"
    										+ "</td></tr>");
    	}
    	out.println("</table>");    
        out.print("<a href='checkOut.jsp'><input type='button' value='Check Out'/></a>");
        out.print("<a href='clearCart.jsp'><input type='button' value='Clear'/></a>");

    }
}