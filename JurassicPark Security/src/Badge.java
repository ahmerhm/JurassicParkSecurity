public class Badge {
    
    private final String badgeNum;

    public Badge() {
        badgeNum = assignEmployeeBadge();
    }

    public Badge(String s) {
        badgeNum = s;
    }

    private String assignEmployeeBadge() {
		double random = (int)(999999 * Math.random() + 100000);
		String id = random + "";
		return id;
	}

    public String getBadgeNum() {
        return badgeNum;
    }

    public boolean equals(Badge bN) {
        if(bN.getBadgeNum().equals(badgeNum)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return badgeNum + ""; 
    }
}
