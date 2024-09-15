package model;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ByteBigBoss
 */
@WebFilter(filterName = "FilterSignInCheck", urlPatterns = {"/register-products.html","/Profile.html","/WishList.html","/PurchasingHistory.html"})
public class FilterSignInCheck implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Process when Filter init
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) res;

        if (httpServletRequest.getSession().getAttribute("user") != null) {
            chain.doFilter(req, res);

        } else {
            httpServletResponse.sendRedirect("login.html");
        }

    }

    @Override
    public void destroy() {
        //Process when Filter destroy
    }

}
