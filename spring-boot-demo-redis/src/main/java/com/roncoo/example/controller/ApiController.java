package com.roncoo.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public HashMap<String, Object> get(@RequestParam String name) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", "hello world");
		map.put("name", name);
		return map;
	}
	
	/**
	 * 添加键值对
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping(value="redis/add/{key}/{value}")
	public Map<String,Object> addWithRedis(@PathVariable String key,@PathVariable String value){
		ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
		ops.set(key,value);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "ok");
		return map;
	}
	
	/**
	 * 根据key，查找value
	 * @param key
	 * @return
	 */
	@RequestMapping(value="redis/find/{key}")
	public Map<String,Object> findWithRedis(@PathVariable String key){
		ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
		String value = ops.get(key);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", value);
		return map;
	}
	
	/**
	 * 删除key
	 * @param key
	 * @return
	 */
	@RequestMapping(value="redis/del/{key}")
	public Map<String,Object> delWithRedis(@PathVariable String key){
		this.stringRedisTemplate.delete(key);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "ok");
		return map;
	}
	
	/**
	 * 根据key，查出value后，根据参数截取value
	 * @param key
	 * @return
	 */
	@RequestMapping(value="redis/find/{key}/{range}")
	public Map<String,Object> getRangeWithRedis(@PathVariable String key,@PathVariable String range){
		String[] startAndEnd = range.split(",");
		//对应redis的getRange方法
		String value = stringRedisTemplate.opsForValue().get(key, Long.valueOf(startAndEnd[0]), Long.valueOf(startAndEnd[1]));
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", value);
		return map;
	}
	
	/**
	 * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
	 * 对应redis的GETSET方法
	 * @param key
	 * @return
	 */
	@RequestMapping(value="redis/getset/{key}/{value}")
	public Map<String,Object> getAndSetWithRedis(@PathVariable String key,@PathVariable String value){
		//对应redis的getRange方法
		String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("newValue", value);
		map.put("oldValue", oldValue);
		return map;
	}
	
}
