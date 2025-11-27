package com.excel.configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.excel.utility.MedicoConstants;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;

@Configuration
@EnableCaching
public class CachingConfig implements MedicoConstants {
	
	private final int ten_minutes = 10;
	
	private final int half_hour = 30;
	
	private final int one_hour = 1 * 60;
	
	private final int ten_hours = 10 * 60;

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager manager = new SimpleCacheManager();
		
	    CaffeineCache divIdByName = buildCache("DivIdByName",ten_hours);
	    CaffeineCache locationCache = buildCache("LocationCache",ten_hours);
	    CaffeineCache allSubCompaniesCache = buildCache("AllSubCompanies",ten_hours);
	    CaffeineCache allLocCache = buildCache("AllLocCache",ten_hours);
	    
	    
	    CaffeineCache PiDboard = buildCache("PiDboard", ten_minutes);
	    CaffeineCache SampleDboard = buildCache("SampleDboard", ten_minutes);
	    CaffeineCache DboardPiGrn = buildCache("DboardPiGrn", ten_minutes);
	    CaffeineCache DboardSampleGrn = buildCache("DboardSampleGrn", ten_minutes);
	    CaffeineCache DboardPlan = buildCache("DboardPlan", ten_minutes);
	    
	    CaffeineCache ApprTrackerPeriods = buildCache("apprTrackerPeriods", one_hour);
	    CaffeineCache ArtSchmSummHomepage = buildCache("ArtSchmSummHomepage", one_hour);
	    CaffeineCache EmpIdByUnameCache = buildCache("EmpIdByUnameCache", ten_hours);
	    CaffeineCache AllPeriodsByFinYear = buildCache("AllPeriodsByFinYear", ten_hours);
	    // added by abhishek
	    CaffeineCache productType = buildCache("ALLproductType", ten_minutes);
	    
	 
	    manager.setCaches(Arrays.asList(
	    		locationCache,
	    		allSubCompaniesCache,
	    		allLocCache,
	    		PiDboard,
	    		SampleDboard,
	    		DboardPiGrn,
	    		DboardSampleGrn,
	    		DboardPlan,
	    		ApprTrackerPeriods,
	    		ArtSchmSummHomepage,
	    		EmpIdByUnameCache,
	    		AllPeriodsByFinYear,
	    		divIdByName,
	    		productType
	    		));
	    
	    return manager;
	}
	 
	private CaffeineCache buildCache(String name, int minutesToExpire) {
	    return new CaffeineCache(name, Caffeine.newBuilder()
	                .expireAfterWrite(minutesToExpire, TimeUnit.MINUTES)
	                .build());
	}
}
