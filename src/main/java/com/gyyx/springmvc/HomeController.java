package com.gyyx.springmvc;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.gyyx.mapper.GameInfoMapper;
import com.gyyx.mapper.ServerInfoMapper;
import com.gyyx.model.GameInfo;
import com.gyyx.model.ServerInfo;
import com.gyyx.utils.MyBatisUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	SqlSessionFactory sqlSessionFactory = null;
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		{
			sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		}
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			GameInfoMapper gameMapper = sqlSession
					.getMapper(GameInfoMapper.class);
			List<GameInfo> games = gameMapper.getGameInfo();
			
			System.out.println(games.get(0).getName());
			model.addAttribute("games", games);
		} finally {
			sqlSession.close();
		}
		
		return "home";
	}

	@RequestMapping(value = "/getServerList", method = RequestMethod.GET,
			produces = "application/json; charset=utf-8")
	public @ResponseBody String getServerList(Locale locale, Model model,
			@RequestParam("gameId") String gameId) {
		{
			sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		}
		System.out.println(gameId);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ServerInfoMapper serverInfoMapper = sqlSession
				.getMapper(ServerInfoMapper.class);
		List<ServerInfo> servers = serverInfoMapper.queryListByGameId(gameId);
		Gson gson = new Gson();
		String s = gson.toJson(servers);
		System.out.println(s);
		return s;
	}

}
