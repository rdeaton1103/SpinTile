import java.util.*;
import java.io.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import org.jgrapht.ext.*;

public class SpinGraph {

//Particles
  ArrayList<Tile> tiles;
  Queue<CellNodes> queue = new LinkedList<CellNodes>();
  CellNodes[][] nodes;
  Tile seed;
  int depth;

  public SimpleGraph<Tile, DefaultEdge> spinGraph;
  public SimpleGraph<CellNodes, DefaultEdge> templateGraph;
  
  Random rand = new Random();

  public SpinGraph(String filename, int d) throws IOException {
    depth = d;
    nodes = new CellNodes[depth][depth];
    templateGraph = new SimpleGraph<CellNodes, DefaultEdge>(DefaultEdge.class);


    tiles = new ArrayList<Tile>();

    spinGraph = new SimpleGraph<Tile, DefaultEdge>(DefaultEdge.class);
    readTilesFromFile(filename);
  }

  public void initializeTemplateGraph() {
    for(int i = 0; i < depth; i++) {
      for(int j = 0; j < depth; j++) {
        nodes[i][j] = new CellNodes(i,j);
        templateGraph.addVertex(nodes[i][j]);
        //System.out.println(nodes[i][j]);
      }
    }
        //System.out.println();

    for(int i = 0; i < depth; i++) {
      for(int j = 0; j < depth; j++) {
        //System.out.println(nodes[i][j]);
        if(i !=depth-1) {
          //System.out.println(nodes[i+1][j]);
          templateGraph.addEdge(nodes[i][j], nodes[i+1][j]);
        }
        if(j !=depth-1) {
          templateGraph.addEdge(nodes[i][j], nodes[i][j+1]);
        }
      }
    }
  }
    
  public void generatePotentialMatches()
  {
    Tile t1, t2, t3;
    int size = tiles.size();
    DefaultEdge de = new DefaultEdge();
    CellNodes cell;
    Iterator<Tile> iter;

    nodes[0][0].add(seed);
    spinGraph.addVertex(seed);

    for(int i = 0; i < depth; i++) {
      for(int j = 0; j < depth; j++) {
        //System.out.println(i + " " + j);
        cell = nodes[i][j];
        iter = cell.iterator();
        while(iter.hasNext()) {
          t1 = iter.next();
          for(int k = 0; k < size; k++) {
            t2 = new Tile(tiles.get(k));
            if(i != depth-1) {
              if(t1.isNorth(t2)) {
                t3 = nodes[i+1][j].isTileUnique(t2);
                if(t3 == null) {
	          nodes[i+1][j].add(t2);
                  spinGraph.addVertex(t2);
                  de = new DefaultEdge();
		  //System.out.println(t1 + " added N " + t2);
	          spinGraph.addEdge(t1,t2,de);
		  //System.out.println(de + " added N");
                } else {
                  de = new DefaultEdge();
		  //System.out.println(t1 + " added N " + t2);
	          spinGraph.addEdge(t1,t3,de);
		  //System.out.println(de + " added N");
                }
              }
            }

	    if(j != depth-1) {
              if(t1.isWest(t2)) {
                t3 = nodes[i][j+1].isTileUnique(t2);
                if(t3 == null) {
                  nodes[i][j+1].add(t2);
                  spinGraph.addVertex(t2);
                  de = new DefaultEdge();
		  //System.out.println(t1 + " added W " + t2);
	          spinGraph.addEdge(t1,t2,de);
		  //System.out.println(de + " added W");
                } else {
                  de = new DefaultEdge();
		  //System.out.println(t1 + " added W " + t2);
	          spinGraph.addEdge(t1,t3,de);
		  //System.out.println(de + " added W");
                }
              }
            }
          }
        }
      }
    }
  }

  public void printNodes() {
    Iterator<Tile> iter;
    Tile t;
  
    for(int i = 0; i < depth; i++) {
      for(int j = 0; j < depth; j++) {
        iter = nodes[i][j].iterator();
        while(iter.hasNext()) {
          t = iter.next();
          System.out.println("(" + i + "-" + j + ") " + t);
        }
      }
    }
  }
 

  public void readTilesFromFile(String filename) throws IOException {
    String[] tokens;
    String sentence;
    Tile tile = new Tile();
    BindingDomain bd;
    String glue;
    int type = 0;

    Scanner input = new Scanner(new File(filename));
    
    while(input.hasNext()) {
      sentence = input.nextLine();       
      tokens = sentence.split("@");

      for(int i = 0; i < tokens.length; i++) {
        glue = tokens[i];
        if(i == 0) {
          tile = new Tile(type,glue);          
      	  type++;
        } else {
          bd = new BindingDomain(glue);
	  if(i == 1) {
            tile.setNorth(bd);
          } else if (i == 2) {
            tile.setEast(bd);
          } else if (i == 3) {
            tile.setSouth(bd);
          } else {
            tile.setWest(bd);
          }
        }
      }
      if((tile.getLabel()).equals("S"))
        seed = tile;
      tiles.add(tile);
    }

  }

  public void writeSpinGraph() throws IOException {
    VertexNameProvider<Tile> snp = new StringNameProvider<Tile>();
    VertexNameProvider<Integer> inp = new IntegerNameProvider<Integer>();
    FileWriter fw = new FileWriter("bd.dot");
    DOTExporter dot = new DOTExporter(inp, snp, null);
    dot.export(fw, spinGraph);
  }
  
  public void writeTemplateGraph() throws IOException {
    VertexNameProvider<Integer> snp = new IntegerNameProvider<Integer>();
    VertexNameProvider<Integer> inp = new IntegerNameProvider<Integer>();
    FileWriter fw = new FileWriter("template.dot");
    DOTExporter dot = new DOTExporter(inp, snp, null);
    dot.export(fw, templateGraph);
  }
  

  public static void main(String[] args) throws IOException
  {
    int depth = Integer.parseInt(args[1]);
    SpinGraph assembly = new SpinGraph(args[0], depth);
    assembly.initializeTemplateGraph();
    assembly.generatePotentialMatches();
    assembly.writeTemplateGraph();
    assembly.writeSpinGraph();
    assembly.printNodes();
  }


}
/*
  public void generateBFS() {
    DefaultEdge dwe;
    
    CellNodes cell = new CellNodes();;
    CellNodes temp;
    DefaultEdge de;
    Set<DefaultEdge> targets;
    Iterator<DefaultEdge> iter;

    cell.add(seed);
    cell.setMark();
    queue.add(cell);

    while(!queue.isEmpty()) {
      cell = queue.remove();
      targets = templateGraph.edgesOf(cell);
      iter = targets.iterator();
      while(iter.hasNext()) {
        temp = iter.next();
      
        if(!temp.getMark()) {
          temp.setMark();
          queue.add(temp);
        }
      }
    }
  }
*/
