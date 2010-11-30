package fancytags.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import fancytags.components.DateTimePicker;

import com.opensymphony.xwork2.util.ValueStack;

public class DateTimePickerTag extends AbstractUITag {

	private static final long serialVersionUID = 5592643084353590718L;
	
	protected String changefunc;
	protected String pickerfunc;
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new DateTimePicker(stack, req, res);
	}
	
	@Override
	protected void populateParams() {
		super.populateParams();
		
		DateTimePicker dtp = (DateTimePicker)component;
		dtp.setPickerfunc(pickerfunc);
		dtp.setChangefunc(changefunc);
	}

	public void setChangefunc(String changefunc) {
		this.changefunc = changefunc;
	}

	public void setPickerfunc(String pickerfunc) {
		this.pickerfunc = pickerfunc;
	}
}
