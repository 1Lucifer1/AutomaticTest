import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.CallGraphStats;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.properties.WalaProperties;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.WalaException;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Tools {

    public static final int CLASS_TYPE = 0;
    public static final int METHOD_TYPE = 1;
    public static int TYPE = CLASS_TYPE;
    private static String projectName = "";

    public static ArrayList<String> changeInfoFile  = new ArrayList<String>();

    public static String getProjectName() {
        return projectName;
    }

    public static void setProjectName(String projectName) {
        Tools.projectName = projectName;
    }

    public static String getExPath(){
        try {
            Properties properties = WalaProperties.loadProperties();
            return properties.getProperty("exclusionPath") != null ? properties.getProperty("exclusionPath"):"exclusion.txt";
        } catch (WalaException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getScopePath(){
        try {
            Properties properties = WalaProperties.loadProperties();
            return properties.getProperty("scopePath") != null ? properties.getProperty("scopePath"):"scope.txt";
        } catch (WalaException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setChangeInfoFile(String path){
        try{
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str;
            while ((str = in.readLine()) != null) {
                changeInfoFile.add(str);
//                System.out.println(str);
            }
//            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void writeTxtFile(ArrayList<String> testSelection, String fileName){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName + ".txt"));
            for(String s : testSelection) out.write(s + "\n");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeDotFile(DependenceDigraph digraph, String fileName){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName + ".dot"));
            out.write(digraph.toString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generatePDF(String fileName){
        try {
            Runtime.getRuntime().exec("dot -T pdf -o " + fileName +".pdf " + fileName + ".dot");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadClasses(String rootPath, AnalysisScope scope) throws Exception {

        File file = new File(rootPath);
        String[] names = file.list();
        for(String name : names){
            name = rootPath + "\\" + name;
            if(name.endsWith(".class")){
                scope.addClassFileToScope(ClassLoaderReference.Application, new File(name));
//                System.out.println(name);
            }else{
                if(new File(name).isDirectory()){
                    loadClasses(name, scope);
                }
            }
        }
    }

    public static boolean addString(ArrayList<String> selectionTest, String s){
        boolean isAdd = false;
        for (String str : selectionTest) {
            if (str.equals(s)) {
                isAdd = true;
                break;
            }
        }
//        System.out.println(isAdd);
        if(!isAdd) selectionTest.add(s);
        return isAdd;
    }

    public static void testClassLoad(AnalysisScope scope) throws ClassHierarchyException {
        ClassHierarchy cha = ClassHierarchyFactory.makeWithRoot(scope);
        for (IClass iClass : cha) {
            if(iClass.toString().contains("Application"))
                System.out.println(iClass);
        }
    }

    public static void testCHACallGraph(CHACallGraph chaCG){
        String stats = CallGraphStats.getStats(chaCG);
        System.out.println(stats);
    }
}
