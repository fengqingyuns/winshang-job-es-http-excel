package com.example.demo.modules.zgdream;

import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.modules.elsticearch.dao.ZhouGongDreamRepository;
import com.example.demo.modules.util.AJAXResult;
import com.example.demo.modules.xiaohua.entity.Dream;
import com.example.demo.modules.xiaohua.service.ZhouGongSleepDreamService;

@Controller
public class ZgDreamController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZgDreamController.class);
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	ZhouGongSleepDreamService zhouGongSleepDreamService;
	@Autowired
	ZhouGongDreamRepository zhouGongDreamRepository;

	@RequestMapping("/toDream")
	public String toDream() {
		return "zgdream/dream";
	}
	
	
	@RequestMapping("/getDream")
	@ResponseBody
	public AJAXResult getDream(String dreamInfo,@PageableDefault(size = 20) Pageable pageable) {
		
		AJAXResult result = new AJAXResult();AJAXResult reresult = new AJAXResult();
		List<Dream> list = new ArrayList<Dream>();
		List<Dream> dreamForList = null;
		SearchQuery searchQuery = null;
		String json = null;
		//elasticsearchTemplate.deleteIndex("");
		searchQuery = new NativeSearchQueryBuilder().withQuery(new MatchQueryBuilder("title", dreamInfo))
				.withPageable(pageable).build();
		dreamForList = elasticsearchTemplate.queryForList(searchQuery, Dream.class);
		if(null == dreamForList ||dreamForList.size() == 0) {
			result = zhouGongSleepDreamService.getSleepDream(dreamInfo);
		
		if (null != result) {
			try {

				list = (ArrayList<Dream>) result.getData();
				list.forEach(dream -> {
					zhouGongDreamRepository.save(dream);
				});

				LOGGER.info("获取dream数据:{}", list.toArray().toString());
			} catch (Exception e) {
				LOGGER.error("获取dream数据失败:{}", e);
			}finally {
				searchQuery = new NativeSearchQueryBuilder().withQuery(new MatchQueryBuilder("title", dreamInfo))
						.withPageable(pageable).build();
				dreamForList = elasticsearchTemplate.queryForList(searchQuery, Dream.class);
				if(null != dreamForList) {
					reresult.setData(dreamForList);
					reresult.setSuccess(true);
				}
			}
		}else {
			reresult.setMsg("不存在");
			reresult.setSuccess(false);
		}
		}else {
			reresult.setData(dreamForList);
			reresult.setSuccess(true);
		}
		
		return reresult;
	}
}
