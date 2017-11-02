package com.ef.models.data;

import com.ef.models.data.dtos.BlockedIpDto;
import com.google.inject.Inject;

public class BlockedIpRepositoryImpl implements BlockedIpRepository {

    private Database database;

    @Inject
    public BlockedIpRepositoryImpl(Database database){
        this.database = database;
    }

    public void save(BlockedIpDto blockedIpDto){

        String insertStatement = String.format("INSERT INTO %s.%s (%s, %s) VALUES (?, ?);",
                Database.NAME,
                Database.Tables.BlockedIps.TABLE_NAME,
                Database.Tables.BlockedIps.IP,
                Database.Tables.BlockedIps.MESSAGE);

        database.executeUpdate(insertStatement, blockedIpDto.getIp(), blockedIpDto.getMessage());
    }
}