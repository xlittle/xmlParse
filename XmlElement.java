package com.taobao.top.tasp.biz.doc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlElement {
	
	private String name;
	
	private String value;
	
	private List<XmlElement> childList;

	private Map<String,String> propMap = new HashMap<String,String>();
	
	public void removeProp(String name){
		if(propMap != null)
			propMap.remove(name);
	}
	
	public void addProp(String name,String value){
		if(propMap == null)
			propMap = new HashMap<String, String>();
		propMap.put(name, value);
	}
	
	public void addChild(XmlElement child){
		if(childList == null)
			childList = new ArrayList<XmlElement>();
		childList.add(child);
	}
	
	public XmlElement getChildXmlElementByName(String name){
		if(childList == null)
			return null;
		for(XmlElement xmlElement : childList){
			if(xmlElement.getName().equals(name)){
				return xmlElement;
			}
		}
		return null;
	}
	
	public String getPropsValue(String key){
		return propMap.get(key);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<XmlElement> getChildList() {
		return childList;
	}

	public Map<String, String> getPropMap() {
		return propMap;
	}

	public void setChildList(List<XmlElement> childList) {
		this.childList = childList;
	}

	public void setPropMap(Map<String, String> propMap) {
		this.propMap = propMap;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
