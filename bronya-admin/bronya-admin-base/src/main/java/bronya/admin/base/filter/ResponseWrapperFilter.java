package bronya.admin.base.filter;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 单次执行：OncePerRequestFilter 确保在一次请求的生命周期内，无论请求如何转发（forwarding）或包含（including），过滤器逻辑只执行一次。这对于避免重复处理请求或响应非常有用。
 * <p>
 * 内置支持：它内置了对请求和响应包装器的支持，使得开发者可以方便地对请求和响应进行包装和处理。
 * <p>
 * 简化代码：通过继承 OncePerRequestFilter，开发者可以减少重复代码，因为过滤器的执行逻辑已经由基类管理。
 * <p>
 * 易于扩展：开发者可以通过重写 doFilterInternal 方法来实现自己的过滤逻辑，而不需要关心过滤器的注册和执行次数。
 */
//@Component // 可以分开出来,但我一个就够了
public class ResponseWrapperFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }
}
