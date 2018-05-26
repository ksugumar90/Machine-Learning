import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;
import java.util.*;

public class StructTree {

    String _colName;
    int _cBrans;
    public String _lab;
    public String _varb;
    public static String _tPut = "";
    public static int it = 0;
    public static int _ctr = 0;
    Vector<String> _child = new Vector<String>();
    String _par;
    String Leaf;
    public HashMap<String, StructTree> nodeTree;

    
    public Collection<StructTree> getChildren() {
        return this.nodeTree.values();
    }

        StructTree(String Attribute, String Leaf, String label) {
        this._colName = Attribute;
        this.nodeTree = new HashMap<String, StructTree>();
        this.Leaf = "yes";
        this._lab = label;
        this._varb = "";
    }
    
    StructTree(String Attribute, String label) {
        this._colName = Attribute;
        this.nodeTree = new HashMap<String, StructTree>();
        this.Leaf = "no";
        this._lab = label;
        this._varb = "da";
    }
public void addChildNode(String label, StructTree child) {

        if (child != null) {
            this.nodeTree.put(label, child);
        }
    }    

    public String classifyDatapoint(Vector<String> _atbs, String[] _mdata) {
        int z = 0;
        int _atbId = 0;
        // Start at current node
        StructTree present = this;

        // While current node != null
        while (present != null) {

            // If the current node is a leaf, we make our decision based on its value
            if (present.Leaf.equals("yes")) {
                return present._colName;
            } // Otherwise, update current to be decision value
            else {
                for (int i = 0; i < _atbs.size(); i++) {
                    if (_atbs.get(i).equals(present._colName)) {
                        _atbId = i;
                    }
                }
                String _tLab = _mdata[_atbId];


                int flag = 0;
                z = 0;
                BreaktheLoop:
                if (flag == 0) {
                    int m = present.nodeTree.values().size();
                    // System.out.println("m="+m);
                    for (StructTree child : present.nodeTree.values()) {
                        z++;
                        // System.out.println(child._colName+" "+child._lab);
                        if (child._lab.equals(_tLab)) {
                            present = child;
                            flag = 1;

                        }
                        //System.out.print(child.labels+ " " + child._colName);
                        if (flag == 1) {
                            break BreaktheLoop;
                        }
                    }
                    if (z >= m) {
                        return "Not Classified";
                    }


                }
            }
        }

        return "Wrong";
    }

    

    

    

    public static void main(String[] args) {
    }
}
