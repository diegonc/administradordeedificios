package fancytags.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractClosingTag;

import fancytags.components.FieldSet;

import com.opensymphony.xwork2.util.ValueStack;

public class FieldSetTag extends AbstractClosingTag {

	private static final long serialVersionUID = 4977185444979361727L;

	protected String legend;
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new FieldSet(stack, req, res);
	}
	
	@Override
	protected void populateParams() {
		super.populateParams();
		
		FieldSet fieldSet = (FieldSet)component;
		fieldSet.setLegend(legend);
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}
}
