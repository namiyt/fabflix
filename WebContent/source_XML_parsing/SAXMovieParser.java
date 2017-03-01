

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class SAXMovieParser extends DefaultHandler{

	ArrayList<Movie> myMovies = new ArrayList<Movie>();
	ArrayList<Movie> moviesForActors = new ArrayList<Movie>();
	ArrayList<Actor> actorsForMovies = new ArrayList<Actor>();
	ArrayList<Actor> myActors = new ArrayList<Actor>();
	ArrayList<String> genres = new ArrayList<String>();
	
	private String tempVal;
	
	//to maintain context
	private Movie tempMovie;
	private Actor tempActor;

	private char type;
	
	
	public SAXMovieParser(){
		myMovies = new ArrayList<Movie>();
		moviesForActors = new ArrayList<Movie>();
		myActors = new ArrayList<Actor>();
		actorsForMovies = new ArrayList<Actor>();
		genres = new ArrayList<String>();
	}
	
	public void runExample() {
		type='m';
		parseDocument("mains243.xml");
		insertData('g');
		insertData('m');
		type='a';
		parseDocument("actors63.xml");
		insertData('a');
		parseDocument("casts124.xml");
		insertData('c');
	}

	private void parseDocument(String infile) {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse(infile, this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * Iterate through the list and print
	 * the contents
	 */
	private void insertData(char table){
		try {
			// Incorporate mySQL driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();	
			// Connect to the database	
			Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb_project3_grading","classta","classta");
			Statement insert = connection.createStatement();
			Statement selectMID = connection.createStatement();
			Statement selectGID = connection.createStatement();
			Statement selectSID = connection.createStatement();

			switch(table) {

				case 'g':
					for (String genre : genres) {
						insert.executeUpdate("insert into genres (name) values ('" + genre + "')");
					}
					break;
				case 'm': 
					System.out.println("No of Movies '" + myMovies.size() + "'.");
					for (Movie movie : myMovies) {
						ResultSet resultMID = selectMID.executeQuery("select id from movies where title='" + movie.getTitle() + "' limit 1");
						ResultSet resultGID = selectGID.executeQuery("select id from genres where name='" + movie.getGenre() + "' limit 1");
						try {
							resultMID.next();
							resultGID.next();
							insert.executeUpdate("insert into genres_in_movies values(" + resultGID.getInt(1) + "," + resultMID.getInt(1) + ")");
						} catch(Exception e) {
						}
						insert.executeUpdate("insert into movies (title, year, director) values ('" + movie.getTitle() + "'," + movie.getYear() + ",'" + movie.getDirector() + "')");
					}
					break;
				case 'a':
					System.out.println("No of Actors '" + myActors.size() + "'.");
					for (Actor actor : myActors) {
						if (actor.getDOB() == null) {
							actor.setDOB("1900-01-01");
						}
						insert.executeUpdate("insert into stars (first_name, last_name, dob) values ('" + actor.getFirstName() + "','" + actor.getLastName() + "','" + actor.getDOB() + "')");
					}
					break;
				case 'c':
					String first, last, title;
					for (int i = 0; i < actorsForMovies.size(); ++i) {
						first = actorsForMovies.get(i).getFirstName();
						last = actorsForMovies.get(i).getLastName();
						title = moviesForActors.get(i).getTitle();

						ResultSet resultMID = selectMID.executeQuery("select id from movies where title='" + title + "' limit 1");
						ResultSet resultSID = selectSID.executeQuery("select id from stars where first_name='" + first + "' and last_name='" + last + "' limit 1");
						try {
							resultMID.next();
							resultSID.next();
							insert.executeUpdate("insert into stars_in_movies values(" + resultSID.getInt(1) + "," + resultMID.getInt(1) + ")");
						} catch (Exception e) {}
					}
					break;
				default:
					break;
			}

		} catch (Exception e) {
			System.out.println("error: " + e);
		}
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase("Film")) {
			//create a new instance of employee
			tempMovie = new Movie();
		} else if (qName.equalsIgnoreCase("Actor")) {
			tempActor = new Actor();
		} else if (qName.equalsIgnoreCase("filmc")) {
			tempMovie = new Movie();
			tempActor = new Actor();
		}
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equalsIgnoreCase("filmc")) {
			moviesForActors.add(tempMovie);
			actorsForMovies.add(tempActor);
		}

		// Adding Movies
		if(qName.equalsIgnoreCase("Film")) {
			//add it to the list
			myMovies.add(tempMovie);
			
		}else if (qName.equalsIgnoreCase("t")) {
			if (tempVal.contains("'")) {
				tempVal = tempVal.replaceAll("\\\\","");
				tempVal = tempVal.replaceAll("'","\\\\'");
			}
			tempMovie.setTitle(tempVal);
		}else if (qName.equalsIgnoreCase("dirn")) {
			tempMovie.setDirector(tempVal);	
		}else if (qName.equalsIgnoreCase("Year")) {
			tempVal = tempVal.replaceAll("\\s","");
			if (tempVal.equals("19yy")) { 
				tempMovie.setYear(1900); 
			} else if (tempVal.matches("19([0-9])x")) {
				String temp = "19" + tempVal.charAt(2) + "0";
				tempMovie.setYear(Integer.parseInt(temp));
			} else {
				tempMovie.setYear(Integer.parseInt(tempVal));
			}
		}

		// Adding Actors
		if (qName.equalsIgnoreCase("Actor")) {
			myActors.add(tempActor);
		} else if (qName.equalsIgnoreCase("firstname")) {
			if (tempActor.getFirstName().length() < 1) {
				tempActor.setFirstName(tempVal);
			}
			if (tempActor.getFirstName().contains("'")) {
				tempActor.setFirstName(tempActor.getFirstName().replaceAll("\\\\",""));
				tempActor.setFirstName(tempActor.getFirstName().replaceAll("'","\\\\'"));
			}
		} else if (qName.equalsIgnoreCase("familyname")) {
			if (tempActor.getLastName().length() < 1) {
				tempActor.setLastName(tempVal);
			}
			if (tempActor.getLastName().contains("'")) {
				tempActor.setLastName(tempActor.getLastName().replaceAll("\\\\",""));
				tempActor.setLastName(tempActor.getLastName().replaceAll("'","\\\\'"));
			}
		} else if (qName.equalsIgnoreCase("stagename")) {
			tempActor.setFirstName(tempVal.split(" ")[0]);
			tempActor.setLastName(tempVal.split(" ")[tempVal.split(" ").length - 1]);
		} else if (qName.equalsIgnoreCase("dob")) {
			if (tempVal.length() > 1 && !tempVal.matches("([a-zA-Z.]*)")) {
				if (tempVal.matches("19([0-9])x")) {
					String temp = "19" + tempVal.charAt(2) + "0";
					tempVal = temp + "-01-01";
				} else if (tempVal.matches("19([a-zA-Z]*)") || tempVal.contains("[")){
					tempVal = "1900";
				} else if (tempVal.matches("18([a-zA-Z]*)") || tempVal.contains("[")){
					tempVal = "1800";
				}
				tempVal = tempVal + "-01-01";
			} else {
				tempVal = "1900-01-01";
			}
			tempVal = tempVal.replace("~","");
			tempActor.setDOB(tempVal);
		}

		// For stars_in_movies
		if (qName.equalsIgnoreCase("a")) {
			if (tempVal.contains("'")) {
				tempVal = tempVal.replaceAll("\\\\","");
				tempVal = tempVal.replaceAll("'","\\\\'");
			}
			tempActor.setFirstName(tempVal.split(" ")[0]);
			tempActor.setLastName(tempVal.split(" ")[tempVal.split(" ").length - 1]);
		}

		// Adding genres
		if (qName.equalsIgnoreCase("cat")) {
			tempMovie.setGenre(tempVal);
			if (!genres.contains(tempVal)) {
				genres.add(tempVal);
			}
		}
		
	}
	
	public static void main(String[] args){
		SAXMovieParser spe = new SAXMovieParser();
		spe.runExample();
	}
	
}




