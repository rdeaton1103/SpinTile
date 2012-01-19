import java.util.*;

public class CellNodes {
  boolean mark;
  int row;
  int column;
  ArrayList<Tile> contents;

  public CellNodes() {
    super();
    mark = false;
    contents = new ArrayList<Tile>();
  }

  public CellNodes(int i, int j) {
    super();
    mark = false;
    row = i;
    column = j;
    contents = new ArrayList<Tile>();
  }

  public void setMark() {
    mark = true;
  }

  public boolean getMark() {
    return mark;
  }

  public String toString() {
    return row + " " + column + " " + super.toString();
  }

  public void add(Tile t) {
    contents.add(t);
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public Iterator<Tile> iterator() {
    return contents.iterator();
  }

  public Tile isTileUnique(Tile t) {
    Iterator<Tile> iter = contents.iterator();
    Tile test;
    while(iter.hasNext()) {
      test = iter.next(); 
      if(t.getType() == test.getType()) {
        return test;
      }
    }

    return null;
  }
}
