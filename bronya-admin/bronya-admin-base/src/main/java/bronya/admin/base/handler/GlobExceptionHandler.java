package bronya.admin.base.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.SysException;

import bronya.admin.base.exception.ParamException;
import bronya.shared.module.common.vo.ResultVO;
import cn.dev33.satoken.exception.NotLoginException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * 统一异常处理类
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public ResultVO<?> notLoginException(NotLoginException e) {
        // 业务异常，无需记录日志
        // return Response.buildFailure(e.getErrCode(), e.getMessage());
        ResultVO<?> failed = ResultVO.failed(e.getMessage());
        failed.setStatus(HttpStatus.UNAUTHORIZED.value());
        return failed;
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFeignException(FeignException ex) {
        log.error("feign异常处理信息", ex);
        return ex.contentUTF8();
    }

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler(value = ValidRuntimeException.class)
    // public ResultVO<?> validRuntime(ValidRuntimeException e) {
    // return ResultVO.failed(e.getMessage());
    // }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BizException.class)
    public ResultVO<?> bizException(BizException e) {
        // 业务异常，无需记录日志
        // return Response.buildFailure(e.getErrCode(), e.getMessage());
        return ResultVO.failed(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResultVO<?> noResourceFoundException(NoResourceFoundException e) {
        return ResultVO.failed(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultVO<?> noHandlerFoundException(NoResourceFoundException e) {
        return ResultVO.failed(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SysException.class)
    public ResultVO<?> sysException(SysException e) {
        // 已知系统异常，打印报错信息
        log.warn(STR."已知系统异常:\{e.getErrCode()}", e);
        return ResultVO.failed(e.getMessage());
    }

    /**
     * 捕获 {@code ParamException} 异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = ParamException.class)
    public ResultVO<?> paramException(ParamException ex) {
        log.info("接口调用异常：{}", ex.getMessage());
        // 返回统一处理类
        return ResultVO.failed(ex.getMessage());
    }

    /**
     * 1.完整性约束的概念 在数据库中，完整性约束是用于确保数据一致性和完整性的规则。常见的完整性约束包括： 主键约束（Primary Key Constraint）：确保表中某列或某几列的值唯一且非空。 外键约束（Foreign
     * Key Constraint）：确保一个表中的数据必须与另一个表中的数据相关联。 唯一约束（Unique Constraint）：确保表中某列或某几列的值唯一。 检查约束（Check
     * Constraint）：确保表中某列的值满足特定条件。 非空约束（Not Null Constraint）：确保表中某列的值不能为 null。
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResultVO<?> duplicateKeyException(DuplicateKeyException ex) {
        log.info(STR."违反了唯一约束：\{ex.getMessage()}", ex);
        // 返回统一处理类
        return ResultVO.failed("违反了唯一约束,请联系管理员");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResultVO<?> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.warn(STR."数据库操作异常：\{ex.getMessage()}", ex);
        // 返回统一处理类
        return ResultVO.failed("数据异常,请联系管理员");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultVO<?> paramException(MissingServletRequestParameterException ex) {
        String msg = STR."缺少参数:\{ex.getParameterName()},类型:\{ex.getParameterType()}";
            log.info(msg);
        // 返回统一处理类
        return ResultVO.failed(msg);
    }

    /**
     * 顶级异常捕获并统一处理，当其他异常无法处理时候选择使用
     */
    @ExceptionHandler(value = Exception.class)
    public ResultVO<?> exception(Exception ex) {
        log.error("未知系统异常", ex);
        // 返回统一处理类
        return ResultVO.failed(ex.getMessage());
    }
}
