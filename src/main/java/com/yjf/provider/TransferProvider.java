package com.yjf.provider;

/**
 * @author 余俊锋
 * @date 2020/10/28 17:36
 * @Description
 */
public class TransferProvider {
    public String selectTransfer(Integer id){
        String sql="SELECT\n" +
                "\ttr.*,\n" +
                "\tsu.`name`,\n" +
                "\tsu.phone \n" +
                "FROM\n" +
                "\ttransfer tr,\n" +
                "\tsys_user su \n" +
                "WHERE\n" +
                "\ttr.del_flag = '0' \n" +
                "\tAND tr.work_order_id = #{id} \n" +
                "\tAND tr.oprate_user_id = su.id order by tr.update_date desc";
        return sql;
    }
}
