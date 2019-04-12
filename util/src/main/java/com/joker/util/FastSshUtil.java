

import com.jcraft.jsch.*;
import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FastSshUtil {

    private final static Logger logger = LoggerFactory.getLogger(FastSshUtil.class);

    /**
     * 获取SSH连接
     * @param host
     * @param port
     * @param user
     * @param password
     * @return
     * @throws JSchException
     */
    public static Session getConnection(String host, int port, String user, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications", "password"); // 跳过手动输入
        session.setPassword(password);
        session.connect();
        return session;
    }

    /**
     * 关闭连接
     * @param session
     * @param channelExec
     */
    public static void close(Session session, ChannelExec channelExec) {
        if(session != null && session.isConnected()) {
            session.disconnect();
        }
        if(channelExec != null && channelExec.isConnected()) {
            channelExec.disconnect();
        }
    }

    /**
     * 执行SSH命令
     * @param host
     * @param port
     * @param user
     * @param password
     * @param command
     * @return 结束行
     * @throws JSchException
     * @throws IOException
     */
    public static String execute(String host, int port, String user, String password, String command) throws JSchException, IOException {
        long startTime = System.currentTimeMillis();
        Session session = null;
        ChannelExec channelExec = null;
        try {
            session = getConnection(host, port, user, password);
            @Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();
            channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(command);
            channelExec.setErrStream(os);
            channelExec.connect();
            @Cleanup InputStream in = channelExec.getInputStream();
            @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf, result = null;
            int status;
            while ((buf = reader.readLine()) != null) {
                if (buf.length() == 0) continue;
                result = buf;
            }
            while (true) {
                if (channelExec.isClosed()) {
                    status = channelExec.getExitStatus();
                    break;
                }
            }
            logger.info("结束行:{} >> 退出码:{}  ", result, status);
            logger.info(new String(os.toByteArray(), "gbk"));
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("运行时间:{}", endTime - startTime);
            close(session, channelExec);
        }
    }

}
