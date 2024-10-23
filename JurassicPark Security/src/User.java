public class User {

  private final String username;
  private String password;
  private Badge employeeBadge;

  // default constructor creates a user object with the parameters of 2 strings
  public User(String u, String p) {
    username = u;
    password = p;

    Badge possibleBadge = new Badge();
    
    for(int i = 0; i < Login.allCreatedBadges.size(); i++) {
      if(Login.allCreatedBadges.equals(possibleBadge)) {
        possibleBadge = new Badge();
      }
    }

    employeeBadge = possibleBadge;

  }

  public boolean equals(User u) {
    if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
      return true;
    }
    return false;
  }

  public String getUsername() { // Can't change the user set username therefore no setUser method added
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Badge getEmployeeBadge() {
    return employeeBadge;
  }

  public void setPassword(String p) {
    password = p;
  }

  public void setEmployeeBadge(Badge eB) {
    employeeBadge = eB;
  }

  public String toString() {
    return username + " " + password;
  }

}
