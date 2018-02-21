package go.openus.rapidframework.junit;

import org.junit.Test;

/**
 * Created by Jason on 2017-03-20.
 */
public class NormalTest {

    @Test
    public void testUpdateSqlString(){
        //String updateColumnSql = "a,b , c ,d=d+1,   e='hello'";
        String updateColumnSql = "a=?,b=? , c=? ,d=d+1,   e='hello'";

        if(updateColumnSql.contains("?")==false){
            String[] columns = updateColumnSql.split(",");
            for (int index=0; index<columns.length; index++) {
                if(columns[index].contains("=")==false){
                    columns[index] = columns[index].trim() + "=?";
                }
            }
            updateColumnSql = org.apache.commons.lang3.StringUtils.join(columns, ",");
        }

        System.out.println(updateColumnSql);
    }
}
