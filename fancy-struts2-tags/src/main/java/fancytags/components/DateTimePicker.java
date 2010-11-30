package fancytags.components;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(
	name="datetimepicker",
	tldTagClass = "fancytags.views.jsp.ui.DateTimePickerTag",
	description = "Render a date field with a date picker.")
public class DateTimePicker extends UIBean {

	final public static String TEMPLATE = "datetimepicker";

	protected String changefunc;
	protected String pickerfunc;
	
	public DateTimePicker(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	protected void evaluateExtraParams() {
		if(changefunc != null) {
			addParameter("changefunc", findString(changefunc));
		}
		if(pickerfunc != null) {
			addParameter("pickerfunc", findString(pickerfunc));
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected Class getValueClassType(){
		return Date.class;
	}
	
	@StrutsTagAttribute(description="The function called to open the date picker.")
	public void setPickerfunc(String pickerfunc) {
		this.pickerfunc = pickerfunc;
	}
	
	@StrutsTagAttribute(description="The function called when a date is entered.")
	public void setChangefunc(String changefunc) {
		this.changefunc = changefunc;
	}

}
