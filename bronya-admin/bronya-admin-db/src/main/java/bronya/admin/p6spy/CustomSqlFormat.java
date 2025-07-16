package bronya.admin.p6spy;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * 解决输出两次sql的问题
 */
public class CustomSqlFormat implements MessageFormattingStrategy {

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
		return STR."\{now}|\{elapsed}|\{category}|connection \{connectionId}|\{P6Util.singleLine(prepared)}|\{P6Util.singleLine(sql)}";
	}
}