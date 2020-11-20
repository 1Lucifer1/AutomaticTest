import com.ibm.wala.classLoader.ShrikeBTMethod;

import java.util.ArrayList;

public class DependenceDigraph {

    // use this digraph, should call addNode first to store the element in digraph.
    /**
     * the node in digraph
     */
    class DigraphNode{
        String signature = "";
        ArrayList<Integer> next = new ArrayList<Integer>();
        ArrayList<ShrikeBTMethod> methods = new ArrayList<ShrikeBTMethod>();
        DigraphNode(String signature) {
            this.signature = signature;
        }
    }

    public ArrayList<DigraphNode> getDigraph() {
        return digraph;
    }

    private final ArrayList<DigraphNode> digraph = new ArrayList<DigraphNode>();

    /**
     * add node into digraph and ensure all elements in this list is unique
     * @param signature added node name
     * @return Have this string been in the ArrayList?
     */
    public int addNode(String signature){
        boolean isAdd = false;
        int index = getIndex(signature);
        if(index != -1) isAdd = true;
        if(!isAdd) {
            digraph.add(new DigraphNode(signature));
            return (digraph.size() - 1);
        }else {
            return index;
        }
    }

    /**
     * add method to a node
     * @param signature node name
     * @param method added method
     */
    public void addMethod(String signature, ShrikeBTMethod method){
        digraph.get(getIndex(signature)).methods.add(method);
    }

    /**
     * get the element index in digraph, if can't find, return -1
     * @param signature the element name
     * @return index
     */
    public int getIndex(String signature){
        int res = -1;
        for (DigraphNode digraphNode : digraph) {
            if (digraphNode.signature.equals(signature)) {
                res = digraph.indexOf(digraphNode);
                break;
            }
        }
        return res;
    }

    /**
     * add classes which call this class
     * @param signature the element name
     * @param next the index of the added classes
     * @return Have this string been in the ArrayList?
     */
    public boolean addNext(String signature, int next){
        boolean isAdd = false;
        DigraphNode node = digraph.get(getIndex(signature));
        for(int i : node.next){
            if(next == i){
                isAdd = true;
                break;
            }
        }
        if(!isAdd) node.next.add(next);
        return isAdd;
    }

    /**
     * output graph by dot file form
     * @return string to write in dot file
     */
    @Override
    public String toString() {
        StringBuilder res;
        if(Tools.TYPE == Tools.CLASS_TYPE){
            res = new StringBuilder("digraph class {\n");
        }else{
            res = new StringBuilder("digraph method {\n");
        }

        for(DigraphNode digraphNode : digraph){
            for(int i : digraphNode.next){
                res.append("\t\"").append(digraphNode.signature).append("\" -> \"").append(digraph.get(i).signature).append("\";\n");
            }
        }
        res.append("}\n");
        return res.toString();
    }
}