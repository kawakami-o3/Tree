package tree;

public enum Color {
  RED("r"),BLACK("B");
 
  private String shortName; 
  private Color(String shortName) {
    this.shortName = shortName;
  }

  public String getShortName() {
    return shortName;
  }
}


