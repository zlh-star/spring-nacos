package com.example.provider2.service;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.FilterList;

import java.io.IOException;
import java.util.List;

public interface HBaseService {

    /**
     * 创建新表，组簇
     * @param tableName
     * @param cfs
     * @return
     */
    Object createTable(String tableName,String[] cfs);

    /**
     * 存入消息（单体）
     * @param tableName
     * @param rowKey
     * @param cfName
     * @param qualifier
     * @param data
     * @return
     */
    Boolean putRow(String tableName, String rowKey, String cfName,String qualifier,String data);

    /**
     * 存入消息，list
     * @param tableName
     * @param puts
     * @return
     */
    Boolean putRows(String tableName, List<Put> puts);

    /**
     * 获取单体消息
     * @param tableName
     * @param rowKey
     * @return
     */
    Result getRow(String tableName, String rowKey);

    Result getRow(String tableName, String rowKey, FilterList filterList);

    ResultScanner getScanner(String tableName);

    ResultScanner getScanner(String tableName, String startRowKey, String endRowKey);

    ResultScanner getScanner(String tableName, String startRowKey, String endRowKey, FilterList filterList);

    /**
     * 删除一行
     * @param tableName
     * @param rowKey
     * @return
     */
    Boolean deleteRow(String tableName,String rowKey);

    /**
     * 删除列Family
     * @param tableName
     * @param columnFamily
     * @return
     */
    Boolean deleteColumnFamily(TableName tableName, byte[] columnFamily);

    Boolean deleteQualifier(String tableName, String rowKey, String cfName,String qualifier);

    Boolean delTableAsync(List<TableName> tableNames)throws IOException;

    Boolean changeStatus(TableName tableName) throws IOException;


}
