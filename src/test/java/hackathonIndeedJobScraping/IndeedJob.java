package hackathonIndeedJobScraping;


public class IndeedJob {
	
	String JobTitle;
	String JobCategory;
	String JobCompName;
	String JobLocation;
	String JobPostedDate;
	String JobDesc;
	String PositionId;
	String JobLink;
	String DateScrapedOn;
	

	public String getJobTitle() {
		return JobTitle;
	}
	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}
//	
//	public String getJobCategory() {
//		return JobCategory;
//	}
//	public void setJobCategory(String jobCategory) {
//		JobCategory = jobCategory;
//	}
	public String getJobCompName() {
		return JobCompName;
	}
	public void setJobCompName(String jobCompName) {
		JobCompName = jobCompName;
	}
	public String getJobLocation() {
		return JobLocation;
	}
	public void setJobLocation(String jobLocation) {
		JobLocation = jobLocation;
	}
	public String getJobPostedDate() {
		return JobPostedDate;
	}
	public void setJobPostedDate(String jobPostedDate) {
		JobPostedDate = jobPostedDate;
	}

		
	public String getJobDesc() {
		return JobDesc;
	}
	public void setJobDesc(String jobDesc) {
		JobDesc = jobDesc;
	}

	public void setPositionId(String positionId) {
		PositionId = positionId;
	}
	public String getPositionId() {
		return PositionId;
	}

	public void setJobLink(String jobLink) {
		JobLink = jobLink;
	}
	public String getJobLink() {
		return JobLink;
	}
	
	
	
	public String getDateScrapedOn() {
		return DateScrapedOn;
	}
	public void setDateScrapedOn(String dateScrapedOn) {
		DateScrapedOn = dateScrapedOn;
	}
	@Override
	public String toString() {
		return  JobTitle+","+JobCompName+"," + JobLocation+"," +JobPostedDate+","+PositionId+ ","+JobLink+","+JobDesc+","+DateScrapedOn;
	}
	
	


}
