package com.gyyx.mapper;

import java.util.List;

import com.gyyx.model.ServerInfo;

public interface ServerInfoMapper {

	public List<ServerInfo> queryListByGameId(String gameId);
}
