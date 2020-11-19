import com.ibm.wala.classLoader.Language;
import com.ibm.wala.classLoader.ShrikeBTMethod;
import com.ibm.wala.ipa.callgraph.*;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.ipa.callgraph.impl.AllApplicationEntrypoints;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.types.annotations.Annotation;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.viz.viewer.CgPanel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class TestSelection {

    private CHACallGraph chaCallGraph;
    private final DependenceDigraph digraph = new DependenceDigraph();
    private final ArrayList<String> selectionTest = new ArrayList<String>();
    private final ArrayList<String> selectHis = new ArrayList<String>();

    public AnalysisScope getScope(String targetPath){
        try{
            String exPath = Tools.getExPath();
            String scopePath = Tools.getScopePath();
            AnalysisScope scope = AnalysisScopeReader.readJavaScope(scopePath, new File(exPath), TestSelection.class.getClassLoader());
            Tools.loadClasses(targetPath, scope);
            return scope;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void initGraph(AnalysisScope scope){
        try{
            // 1.生成类层次关系对象
            ClassHierarchy classHierarchy = ClassHierarchyFactory.makeWithRoot(scope);
            // 2.生成进入点
            Iterable<Entrypoint> entrypoints = new AllApplicationEntrypoints(scope, classHierarchy);
            // 3.利用CHA算法构建调用图
            chaCallGraph = new CHACallGraph(classHierarchy, true);
            chaCallGraph.init(entrypoints);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public void initMethodGraph(AnalysisScope scope){
//        try{
//            // 1.生成类层次关系对象
//            classHierarchy = ClassHierarchyFactory.makeWithRoot(scope);
//            // 2.生成进入点
//            entrypoints = new AllApplicationEntrypoints(scope, classHierarchy);
//            // 3.利用0-CHA算法构建调用图
//            AnalysisOptions option = new AnalysisOptions(scope, entrypoints);
//            SSAPropagationCallGraphBuilder builder = Util.makeZeroCFABuilder(Language.JAVA, option, new AnalysisCacheImpl(), classHierarchy, scope);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

    public void classAnalysis(){
        // 4.遍历cg中所有的节点
        for (CGNode node : chaCallGraph){

            // node中包含了很多信息，包括类加载器、方法信息等，这里只筛选出需要的信息
            if (node.getMethod() instanceof ShrikeBTMethod){
                // node.getMethod()返回一个比较泛化的IMethod实例，不能获取到我们想要的信息
                // 一般地，本项目中所有和业务逻辑相关的方法都是ShrikeBTMethod对象
                ShrikeBTMethod method = (ShrikeBTMethod) node.getMethod();

                if ("Application".equals(method.getDeclaringClass().getClassLoader().toString())){
//                    System.out.println(method.getSignature());
                    drawClassGraph(node);
//                    classTestSelection(method, node);
                }
            }
        }
        classTestSelection();
//        System.out.println(digraph.toString());
//        String fileName = "class";
//        Tools.writeDotFile(digraph, fileName);
//        Tools.generatePDF(fileName);
//        for(String s : selectionTest) System.out.println(s);
        Tools.writeTxtFile(selectionTest, "selection-class");
//        System.out.println();
    }

    private void drawClassGraph(CGNode node){
        Iterator<CGNode> it =  chaCallGraph.getPredNodes(node);
        ShrikeBTMethod method = (ShrikeBTMethod) node.getMethod();
        digraph.addNode(method.getDeclaringClass().getName().toString());
        digraph.addMethod(method.getDeclaringClass().getName().toString(), method);

        while(it.hasNext()){
            CGNode preNode = it.next();

            ShrikeBTMethod preMethod = (ShrikeBTMethod) preNode.getMethod();
            int index = digraph.addNode(preMethod.getDeclaringClass().getName().toString());
            digraph.addNext(method.getDeclaringClass().getName().toString(), index);

//            drawClassGraph(preNode);
//            System.out.println(method.getDeclaringClass().getName().toString() + " " + preMethod.getDeclaringClass().getName().toString());
        }
    }

    public void methodAnalysis(){
        for (CGNode node : chaCallGraph){

            // node中包含了很多信息，包括类加载器、方法信息等，这里只筛选出需要的信息
            if (node.getMethod() instanceof ShrikeBTMethod){
                // node.getMethod()返回一个比较泛化的IMethod实例，不能获取到我们想要的信息
                // 一般地，本项目中所有和业务逻辑相关的方法都是ShrikeBTMethod对象
                ShrikeBTMethod method = (ShrikeBTMethod) node.getMethod();
//                System.out.println(method.getSignature());
                if ("Application".equals(method.getDeclaringClass().getClassLoader().toString())){
                    drawMethodGraph(node);
                    methodTestSelection(method, node);
                }
            }
        }
//        System.out.println(digraph.toString());
//        String fileName = "method";
//        Tools.writeDotFile(digraph, fileName);
//        Tools.generatePDF(fileName);
//        for(String s : selectionTest) System.out.println(s);
        Tools.writeTxtFile(selectionTest, "selection-method");
//        System.out.println();
    }

    private void drawMethodGraph(CGNode node){
        Iterator<CGNode> it =  chaCallGraph.getPredNodes(node);
        ShrikeBTMethod method = (ShrikeBTMethod) node.getMethod();
        while(it.hasNext()){
            CGNode preNode = it.next();
            ShrikeBTMethod preMethod = (ShrikeBTMethod) preNode.getMethod();
            int index = digraph.addNode(preMethod.getSignature());
            digraph.addNode(method.getSignature());
            digraph.addNext(method.getSignature(), index);

//            drawMethodGraph(preNode);
//            System.out.println(method.getDeclaringClass().getName().toString() + " " + preMethod.getDeclaringClass().getName().toString());
        }
    }

    private void methodTestSelection(ShrikeBTMethod method, CGNode node){
//        String classInnerName = method.getDeclaringClass().getName().toString();
        // 获取方法签名
//        String signature = method.getSignature();
        for(String s : Tools.changeInfoFile){
            if((method.getDeclaringClass().getName().toString() + " " + method.getSignature()).equals(s)){
//                System.out.println(node.getMethod().getSignature());
                methodSelection(node);
            }
        }
//        System.out.println(classInnerName + " " + signature);
    }

    private void methodSelection(CGNode node){
//        System.out.println(((ShrikeBTMethod) node.getMethod()).getSignature());
        if(Tools.addString(selectHis, ((ShrikeBTMethod) node.getMethod()).getSignature())) return;
        Iterator<CGNode> it =  chaCallGraph.getPredNodes(node);
        while (it.hasNext()){
            CGNode preNode = it.next();
            ShrikeBTMethod preMethod = (ShrikeBTMethod) preNode.getMethod();
//            System.out.println(preMethod.getDeclaringClass().getName().toString() + " " + preMethod.getSignature());
            if(isTest(preMethod)) Tools.addString(selectionTest, preMethod.getDeclaringClass().getName().toString() + " " + preMethod.getSignature());
            methodSelection(preNode);
        }
    }

    private void classTestSelection(){
        for(String s : Tools.changeInfoFile){
            String[] str = s.split(" ");
//            if(Tools.addString(selectHis, str[0])) continue;
            for(DependenceDigraph.DigraphNode node : digraph.getDigraph()){
                if(node.signature.equals(str[0])){
                    classSelection(node);
                }
            }
        }
    }

    private void classSelection(DependenceDigraph.DigraphNode node){

        for(int i : node.next){
//            System.out.println(i + digraph.getDigraph().get(i).signature + digraph.getDigraph().get(i).methods.size());
            for(ShrikeBTMethod method : digraph.getDigraph().get(i).methods){
//                System.out.println(i + ":" + method.getDeclaringClass().getName().toString() + " " + node.signature);
                if(isTest(method)) {
                    Tools.addString(selectionTest, method.getDeclaringClass().getName().toString() + " " + method.getSignature());
                }
            }
//            classSelection(digraph.getDigraph().get(i));
        }
    }



    /**
     * If method have test annotation, return true.
     * @param method
     * @return
     */
    private boolean isTest(ShrikeBTMethod method){
        Collection<Annotation> annotations = method.getAnnotations();
        for(Annotation annotation : annotations){
            if(new String(annotation.getType().getName().getClassName().getValArray()).equals("Test"))return true;
        }
        return false;
    }
}
