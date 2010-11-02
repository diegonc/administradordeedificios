package fancytags.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(
	name="fieldset",
	tldTagClass = "fancytags.views.jsp.ui.FieldSetTag",
	description = "Render a fieldset with an optional legend.")
public class FieldSet extends ClosingUIBean {

	final public static String TEMPLATE = "fieldset";
	final public static String TEMPLATE_CLOSE = "fieldset-close";
	
	protected String legend;
	
	public FieldSet(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE_CLOSE;
	}

	@Override
	public String getDefaultOpenTemplate() {
		return TEMPLATE;
	}

	protected void evaluateExtraParams() {
		if(legend != null) {
			addParameter("legend", findString(legend));
		}
	}
	
	@StrutsTagAttribute(description="The legend for this field set.")
	public void setLegend(String legend) {
		this.legend = legend;
	}

}
