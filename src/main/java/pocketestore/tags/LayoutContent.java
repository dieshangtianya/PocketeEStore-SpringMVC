package pocketestore.tags;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class LayoutContent extends BodyTagSupport {
    private static final long serialVersionUID = 5901780136314677968L;
    // prefix of tag
    public static final String PREFIX = "";
    // content name
    private String name;

    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        ServletRequest request = pageContext.getRequest();

        BodyContent bodyContent = getBodyContent();
        request.setAttribute(PREFIX + name, StringUtils.trim(bodyContent.getString()));
        return super.doEndTag();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
