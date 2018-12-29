package com.example.demo.modules.job.handler;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.TermQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;
import com.example.demo.modules.elsticearch.dao.ZhouGongDreamRepository;
import com.example.demo.modules.util.AJAXResult;
import com.example.demo.modules.xiaohua.entity.Dream;
import com.example.demo.modules.xiaohua.service.ZhouGongSleepDreamService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
@JobHander(value="dreamHandler")
@Component
public class ZhouGongDreamHandler extends IJobHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(ZhouGongDreamHandler.class);
	
	@Autowired
	ZhouGongSleepDreamService zhouGongSleepDreamService;
	 @Autowired
	 ZhouGongDreamRepository zhouGongDreamRepository;
	
	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		
		String content = "";
		List<Dream> list = new ArrayList<Dream>();
		if (params != null) {
			if (params.length >= 1) {
				
				content = params[0];// new Date()为获取当前系统时间，也可使用当前时间戳
				XxlJobLogger.log("content: {0}, releasenum: {1}", content);
			}
		}
		AJAXResult result = zhouGongSleepDreamService.getSleepDream(content);
		if (null != result) {
			try {
				
				list = (ArrayList<Dream>) result.getData();
				list.forEach(dream ->{
					zhouGongDreamRepository.save(dream);
				});
				
				LOGGER.info("获取dream数据", list.toArray().toString());
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("获取dream数据失败",e);
			}
			
		}
	//	elasticsearchTemplate.deleteIndex("zhougongdream");
		return ReturnT.SUCCESS;
	}
	
}
