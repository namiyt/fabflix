

public class Actor {

	private String firstName;

	private String dob;
	
	private String lastName;
	
	public Actor(){
		
	}
	
	public Actor(String firstName, String dob,String lastName) {
		this.firstName = firstName;
		this.dob = dob;
		this.lastName = lastName;
		
	}
	public String getDOB() {
		return dob;
	}

	public void setDOB(String dob) {
		this.dob = dob;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Actor Details - ");
		sb.append("firstName:" + getFirstName());
		sb.append(", ");
		sb.append("lastName:" + getLastName());
		sb.append(", ");
		sb.append("DOB:" + getDOB());
		sb.append(".");
		
		return sb.toString();
	}
}
