public class MainEntry {

    /**
     *
     * @param args args[0]: -m or -c, args[1]: target directory path, args[2]: change_info.txt path
     */
    public static void main(String[] args){
        String targetPath = args[1];
        String changeInfoPath = args[2];
        Tools.setChangeInfoFile(changeInfoPath);
        Tools.setProjectName(targetPath);
        switch (args[0].charAt(1)){
            case 'm':
                Tools.TYPE = Tools.METHOD_TYPE;
                TestSelection testSelection1 = new TestSelection();
                testSelection1.initGraph(testSelection1.getScope(targetPath));
                testSelection1.methodAnalysis();
                break;
            case 'c':
                Tools.TYPE = Tools.CLASS_TYPE;
                TestSelection testSelection = new TestSelection();
                testSelection.initGraph(testSelection.getScope(targetPath));
                testSelection.classAnalysis();
                break;
            default:
                System.out.println("Please input right parameter.");
                break;
        }



    }
}
