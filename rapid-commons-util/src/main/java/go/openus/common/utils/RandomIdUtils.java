package go.openus.common.utils;

import java.util.Date;
import java.util.Random;

/**
 * 随机编码工具类 (一般情况下都能保证生成的编码唯一)
 */
public class RandomIdUtils {

    /**
     *  按机器应用编号和和前缀生成唯一编码
     * @param prefix 前缀
     * @param seqNoSeg 序列号分段，一般是机器编号等标识，以适应集群部署
     * @return 唯一编码
     */
    public static String generate(String prefix, String seqNoSeg){
        Date date = new Date();
        String randStr1 =  String.format("%04d", new Random(new Date().getTime()).nextInt(10000));//生成4位随机数
        String randStr2 =  String.format("%05d", new Random(new Date().getTime()).nextInt(100000));//生成5位随机数
        return prefix + DateUtils.getFormatedDate(date,"yyyyMMddHHmmssSSS")
                + seqNoSeg + randStr1 + randStr2;//网关充值订单号
    }
}
