package fadidev.spigotspleef.utils.enums;

public enum Variable {

	MSG("%msg%");
	
	private String stringVariable;
	
	Variable(String stringVariable){
		this.stringVariable = stringVariable;
	}
	
	public String getVariable() {
		return stringVariable;
	}
}
