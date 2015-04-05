import java.util.List;
import java.util.Set;
import java.util.Map.Entry;


public class Test {
	
	public static void main(String[] args) throws Exception{	    
	    XmlElement root = XMLReader.getXmlElementFromXmlFile("/data.xml",true);
	    
	    disPlayXmlElement(root,0);
	}

	public static void disPlayXmlElement(XmlElement root,int level) {
		for(int i = 0; i < level; i++){
			System.out.print("-");
		}
		System.out.print(root.getName()+"  ");
	    //输出属性
	    if(root.getPropMap() != null){
	    	Set<Entry<String,String>>entrySet =  root.getPropMap().entrySet();
	    	for(Entry<String,String> entry : entrySet){
	    		System.out.print(entry.getKey()+"--->"+entry.getValue()+"  ");
	    	}
	    }
	    System.out.println();
	    if(root.getChildList() != null){
	    	List<XmlElement> childXmlElementList = root.getChildList();
	    	for(XmlElement xmlElement : childXmlElementList){
	    		disPlayXmlElement(xmlElement,level+1);
	    	}
	    }
	}
}
