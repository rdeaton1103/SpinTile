public class BindingDomain {
  Tile tile;
  int direction;
  String glue;
  boolean filled = false;

  public BindingDomain(String g) {
    glue = g;
  }

  public void setTile(Tile t) {
    tile = t;
  }

  public Tile getTile() {
    return tile;
  }

  public String getGlue() {
    return glue;
  }

  public void setFilled() {
    filled = true;
  }

  public boolean getFilled() {
    return filled;
  }

  public boolean equals(BindingDomain bd) {
    String temp = bd.getGlue();

    if(glue.equals("x") || temp.equals("x"))
      return false;

    if(glue.equals(temp))
      return true;
   
    return false;
  }

  public String toString() {
    return glue;
  }
}
