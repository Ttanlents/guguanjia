package com.yjf.provider;

/**
 * @author 余俊锋
 * @date 2020/10/28 17:32
 * @Description
 */
public class DetailProvider {
    public String selectDetail(){
        String sql="SELECT\n" +
                "\tde.*,\n" +
                "\twt.`name` waste_type_name,\n" +
                "\twa.`name` waster_name,\n" +
                "\twt.`code` waste_type_code,\n" +
                "\twa.`code` waste_code\n" +
                "FROM\n" +
                "\tdetail de,\n" +
                "\twaste_type wt,\n" +
                "\twaste wa \n" +
                "WHERE\n" +
                "\tde.del_flag = '0' and \n" +
                "\tde.work_order_id=#{id}\n" +
                "\tAND de.waste_type_id = wt.id \n" +
                "\tAND de.waste_id = wa.id";
        return sql;
    }
}
