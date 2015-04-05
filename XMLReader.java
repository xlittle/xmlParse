package com.taobao.top.tasp.biz.doc.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

public class XMLReader {
	
	public static void main(String[] args) throws Exception{	    
	    XmlElement root = getXmlElementFromXmlFile("/Users/xt/Downloads/t.xml",true);
	    
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
//	    System.out.println(root.getValue());
	}
	/**
	 * 从文件中读取
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static XmlElement getXmlElementFromXmlFile(String filePath,boolean isSimplify) throws IOException, DocumentException{
		Document doc=null;
	    SAXReader reader=new SAXReader();
	    doc=reader.read(new File(filePath));
	    return getXmlElementFromDocument(doc,isSimplify);
	}
	/**
	 * 从Document中读取
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static XmlElement getXmlElementFromDocument(Document doc,boolean isSimplify) throws IOException, DocumentException{
		
		XmlElement rootXmlElement = new XmlElement();
		Element root = doc.getRootElement();
		
		rootXmlElement.setName(root.getName());
		fillElementPropsList(root,rootXmlElement);
		
		if(root.elements().size() > 0){
			List<XmlElement> childXmlElementList = getXmlElementList(getSimplifyChildList(rootXmlElement,root,isSimplify),isSimplify);
			rootXmlElement.setChildList(childXmlElementList);
		}else{
			rootXmlElement.setValue(root.getText());
		}
		return rootXmlElement;
	}
	
	/**
	 * 将Dom节点转化成XmlElement节点
	 * @param childList
	 * @return
	 */
	public static List<XmlElement> getXmlElementList(List<Element>childList,boolean isSimplify){
		if(childList == null || childList.size() == 0)
			return null;
		
		List<XmlElement> xmlElementList = new ArrayList<XmlElement>();
				
		Iterator<Element>ite =  childList.iterator();
		while(ite.hasNext()){
			Element element= ite.next();
			XmlElement xmlElement = new XmlElement();
			
			xmlElement.setName(element.getName());
			fillElementPropsList(element,xmlElement);
	    	
			if(element.elements().size() > 0){
	    		List<XmlElement> childXmlElementList = getXmlElementList(getSimplifyChildList(xmlElement,element,isSimplify),isSimplify);
	    		xmlElement.setChildList(childXmlElementList);
	    	}else{
	    		xmlElement.setValue(element.getText());
	    	}
	    	xmlElementList.add(xmlElement);
		}
		return xmlElementList;
	}
	
	/**
	 * 填充节点属性
	 * @param element
	 * @param xmlElement
	 */
	private static void fillElementPropsList(Element element,XmlElement xmlElement){
	    Iterator<?>ite = element.attributeIterator();
	    while(ite.hasNext()){
	    	Attribute attr = (Attribute)ite.next(); 
	    	xmlElement.addProp(attr.getName(), attr.getValue());
	    }
	}

	/**
	 * 将字符串解析为Document对象
	 * @param in
	 * @return
	 */
	public static Document parseDocument(String in) {
		try {
			SAXReader reader=new SAXReader();
			InputSource is = new InputSource();
			is.setByteStream(new ByteArrayInputStream(in.getBytes("UTF-8")));
			return reader.read(is);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 获取精简化的子属性
	 * 假如节点只有一个子节点，则隐藏这一层。
	 * @param parent
	 * @param element
	 * @param simplify
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Element> getSimplifyChildList(XmlElement parent,Element element,boolean simplify){
		if(simplify){
			//节点只有一个子节点
			if(element.elements().size() == 1){
				//将子属性写到父节点
				fillElementPropsList(element,parent);
				return getSimplifyChildList(parent,(Element)element.elements().get(0),simplify);
			}
		}
		return element.elements();
	}
	
}
