package pocketestore.tags;


import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class LayoutBlock extends BodyTagSupport {

    private String name;

    private static final long serialVersionUID = 1425068108614007667L;

    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        ServletRequest request = pageContext.getRequest();

        String defaultContent = (getBodyContent() == null) ? "" : getBodyContent().getString();

        String bodyContent = (String) request.getAttribute(LayoutContent.PREFIX + name);

        bodyContent = StringUtils.isEmpty(bodyContent) ? defaultContent : bodyContent;

        try {
            pageContext.getOut().write(bodyContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doEndTag();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
