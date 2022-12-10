package app;

public class Subject {
	private int id;
	private String subname;
    private String offernum;
    
    public Subject(int id, String subject_name,String offernum) {
    	this.id = id;
        this.subname = subject_name;
        this.offernum = offernum;
    }

	@Override
	public String toString() {
		return offernum+"-"+subname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubname() {
		return subname;
	}

	public void setSubject_name(String subject_name) {
		this.subname = subject_name;
	}

	public String getOffernum() {
		return offernum;
	}

	public void setOffernum(String offernum) {
		this.offernum = offernum;
	}
}
