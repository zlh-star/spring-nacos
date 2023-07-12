package com.example.hbasetest.config;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class HBaseServiceImpl implements HBaseService {

    @Autowired
    @Qualifier("connection")
    private Connection connection;

    @Override
    public Object createTable(String tableName,String[] cfs){
        try(HBaseAdmin admin = (HBaseAdmin) connection.getAdmin()){
            if(admin.tableExists(TableName.valueOf(tableName))) {
                return "the table is existing";
            }
            // 不存在 则创建
            TableDescriptorBuilder descriptorBuilder=TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName));
//            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            List<ColumnFamilyDescriptor> families = new ArrayList<>();
            Arrays.stream(cfs).forEach(cf->{
                families.add(ColumnFamilyDescriptorBuilder.newBuilder(cf.getBytes()).build());
//                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
//                hColumnDescriptor.setMaxVersions(1);
//                hTableDescriptor.addFamily(hColumnDescriptor);
            });
            TableDescriptor tableDescriptor=descriptorBuilder
                    .setColumnFamilies(families)
                    .build();
            admin.createTable(tableDescriptor);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean putRow(String tableName, String rowKey, String cfName,String qualifier,String data){
        try(Table table = connection.getTable(TableName.valueOf(tableName))){
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(cfName), Bytes.toBytes(qualifier), Bytes.toBytes(data));
            table.put(put);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return true;
    }

    @Override
    public Boolean putRows(String tableName, List<Put> puts){
        try(Table table = connection.getTable(TableName.valueOf(tableName))){
            table.put(puts);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return true;
    }

    @Override
    public Result getRow(String tableName, String rowKey){
        try(Table table = connection.getTable(TableName.valueOf(tableName))){
            Get get = new Get(Bytes.toBytes(rowKey));
            return table.get(get);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    public Result getRow(String tableName, String rowKey, FilterList filterList){
        try(Table table = connection.getTable(TableName.valueOf(tableName))){
            Get get = new Get(Bytes.toBytes(rowKey));
            get.setFilter(filterList);
            return table.get(get);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultScanner getScanner(String tableName){
        try(Table table = connection.getTable(TableName.valueOf(tableName))){
            Scan scan = new Scan();
            scan.setCaching(1000);
            return table.getScanner(scan);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultScanner getScanner(String tableName, String startRowKey, String endRowKey){
        try(Table table = connection.getTable(TableName.valueOf(tableName))){
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRowKey));
            scan.setStopRow(Bytes.toBytes(endRowKey));
            scan.setCaching(1000);
            return table.getScanner(scan);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

    // 使用过滤器
    @Override
    public ResultScanner getScanner(String tableName, String startRowKey, String endRowKey, FilterList filterList){
        try(Table table = connection.getTable(TableName.valueOf(tableName))){
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRowKey));
            scan.setStopRow(Bytes.toBytes(endRowKey));
            scan.setCaching(1000);
            scan.setFilter(filterList);
            return table.getScanner(scan);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

    // 删除
    @Override
    public Boolean deleteRow(String tableName,String rowKey){
        try(Table table = connection.getTable(TableName.valueOf(tableName))){
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
            return true;
        }catch(IOException ioe){ioe.printStackTrace();}
        return null;
    }

    // 删除 列簇 用 admin
    @Override
    public Boolean deleteColumnFamily(TableName tableName, byte[] columnFamily){
        try(HBaseAdmin admin = (HBaseAdmin)connection.getAdmin()){
            admin.deleteColumnFamilyAsync(tableName,columnFamily);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return true;
    }
    //删除 某列 用 table
    @Override
    public Boolean deleteQualifier(String tableName, String rowKey, String cfName,String qualifier){
        try(Table table = connection.getTable(TableName.valueOf(tableName))){
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addColumn(Bytes.toBytes(cfName),Bytes.toBytes(qualifier));
            table.delete(delete);
            return true;
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }
}
