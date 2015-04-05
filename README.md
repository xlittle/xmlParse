# xmlParse
Parse XML Document into My own format using dom4j

# format：


	private String name; 
	
	private String value;

	//child nodes
	private List<XmlElement> childList;
	
        //node properties
	private Map<String,String> propMap = new HashMap<String,String>(); 


# Meaning
After transfer XML Document Object into this format object(XmlElement),It's provided a simple way to visit each element.
