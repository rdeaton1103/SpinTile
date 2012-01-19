public class Tile {
  
  int type;
  String label;
  BindingDomain north;
  BindingDomain east;
  BindingDomain south;
  BindingDomain west;

  public Tile() {
  }

  public Tile(int t, String l) {
    type = t;
    label = l;
  }

  public Tile( Tile t) {
    this(t.getType(),t.getLabel(),t.getNorth(),t.getEast(),t.getSouth(),t.getWest());
  }

  public Tile(int t, String l, BindingDomain n, BindingDomain e, BindingDomain s, BindingDomain w) {
    type = t;
    label = l;
    north = n;
    east = e;
    south = s;
    west = w;
  }

  public void setNorth(BindingDomain g) {
    north = g;
  }

  public void setEast(BindingDomain g) {
    east = g;
  }

  public void setSouth(BindingDomain g) {
    south = g;
  }

  public void setWest(BindingDomain g) {
    west = g;
  }

  public BindingDomain getNorth() {
    return north;
  }

  public BindingDomain getEast() {
    return east;
  }

  public BindingDomain getSouth() {
    return south;
  }

  public BindingDomain getWest() {
    return west;
  }

  public int getType() {
    return type;
  }

  public String getLabel() {
    return label;
  }

  public boolean isNorth(Tile t) {
    boolean compatible = false;
    BindingDomain s = t.getSouth();

    if(north.equals(s)) {
      compatible = true;
    } 

    return compatible;
  }

  public boolean isWest(Tile t) {
    boolean compatible = false;

    if(west.equals(t.getEast())) {
      //(t.getEast()).setTile(this);
      //west.setTile(t);
      compatible = true;
    } 

    return compatible;
  }

  public boolean isCompatible(Tile t) {
    boolean compatible = false;

    if(north.equals(t.getSouth())) {
      //(t.getSouth()).setTile(this);
      //north.setTile(t);
      compatible = true;
    } 
    if(east.equals(t.getWest())) {
      //(t.getWest()).setTile(this);
      //east.setTile(t);
      compatible = true;
    } 
    if(south.equals(t.getNorth())) {
      //(t.getNorth()).setTile(this);
      //south.setTile(t);
      compatible = true;
    } 
    if(west.equals(t.getEast())) {
      //(t.getEast()).setTile(this);
      //west.setTile(t);
      compatible = true;
    }

    return compatible;
  }

  public String toString() {
    return type + " [" + label + "] " + north + "-" + east + "-" + south + "-" + west;
  }

}

