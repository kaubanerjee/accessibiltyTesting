/**
 * Copyright (C) 2015 Deque Systems Inc.,
 *
 * Your use of this Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This entire copyright notice must appear in every copy of this file you
 * distribute or in any file that contains substantial portions of this source
 * code.
 */

package com.deque.axe;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URL;

public class AccessibilityTest {
	@Rule
	public TestName testName = new TestName();

	private WebDriver driver;

	private static final URL scriptUrl = AccessibilityTest.class.getResource("/axe.min.js");

	/**
	 * Instantiate the WebDriver and navigate to the test site
	 */
	@Before
	public void setUp() {
		// ChromeDriver needed to test for Shadow DOM testing support
                System.out.println("Directory "+System.getProperty("user.dir"));
                System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
	}

	/**
	 * Ensure we close the WebDriver after finishing
	 */
	@After
	public void tearDown() {
		driver.quit();
	}

	/**
	 * Basic test
	 */
	@Test
	public void testAccessibility() {
		driver.get("https://mindtap-staging.cengage.com/nb/service/launch?token=97E356B2C9D15A15F4549615A1FB87B48593728FF4EA9516E59AD278842324448C46FC6C30016D5CBB057AE87B05725467D641D3911E320ABD03753368D180237DA4B567E0AD241F&courseKey=MTPN07WZ0M3J&eISBN=9781305657571");
		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();

		JSONArray violations = responseJSON.getJSONArray("violations");
		
		System.out.println("Violation from the "+violations);

		if (violations.length() == 0) {
			assertTrue("No violations found", true);
		} else {
			
			
                   for(int i=0;i<violations.length();i++){
                	   
                	   System.out.println("******************************Violation "+(i+1)+"*******************************");
                       JSONObject jsonobject = violations.getJSONObject(i);    
                       String impact = jsonobject.getString("impact");    
                       String description = jsonobject.getString("description");
                       System.out.println("Impact is "+impact);
                        System.out.println("Description is "+description);
                   }
                   AXE.writeResults(testName.getMethodName(), responseJSON);
                   assertTrue(AXE.report(violations), true);
		}
	}

	/**
	 * Test with skip frames
	 */
//	@Test
//	public void testAccessibilityWithSkipFrames() {
//		driver.get("http://localhost:5005");
//		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
//				.skipFrames()
//				.analyze();
//
//		JSONArray violations = responseJSON.getJSONArray("violations");
//
//		if (violations.length() == 0) {
//			assertTrue("No violations found", true);
//		} else {
//			AXE.writeResults(testName.getMethodName(), responseJSON);
//			assertTrue(AXE.report(violations), false);
//		}
//	}
//
//	/**
//	 * Test with options
//	 */
//	@Test
//	public void testAccessibilityWithOptions() {
//		driver.get("http://localhost:5005");
//		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
//				.options("{ rules: { 'accesskeys': { enabled: false } } }")
//				.analyze();
//
//		JSONArray violations = responseJSON.getJSONArray("violations");
//
//		if (violations.length() == 0) {
//			assertTrue("No violations found", true);
//		} else {
//			AXE.writeResults(testName.getMethodName(), responseJSON);
//
//			assertTrue(AXE.report(violations), false);
//		}
//	}
//
//	/**
//	 * Test a specific selector or selectors
//	 */
//	@Test
//	public void testAccessibilityWithSelector() {
//		driver.get("http://localhost:5005");
//		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
//				.include("title")
//				.include("p")
//				.analyze();
//
//		JSONArray violations = responseJSON.getJSONArray("violations");
//
//		if (violations.length() == 0) {
//			assertTrue("No violations found", true);
//		} else {
//			AXE.writeResults(testName.getMethodName(), responseJSON);
//
//			assertTrue(AXE.report(violations), false);
//		}
//	}
//
//	/**
//	 * Test includes and excludes
//	 */
//	@Test
//	public void testAccessibilityWithIncludesAndExcludes() {
//		driver.get("http://localhost:5005");
//		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
//				.include("div")
//				.exclude("h1")
//				.analyze();
//
//		JSONArray violations = responseJSON.getJSONArray("violations");
//
//		if (violations.length() == 0) {
//			assertTrue("No violations found", true);
//		} else {
//			AXE.writeResults(testName.getMethodName(), responseJSON);
//			assertTrue(AXE.report(violations), false);
//		}
//	}
//
//	/**
//	 * Test a WebElement
//	 */
//	@Test
//	public void testAccessibilityWithWebElement() {
//		driver.get("http://localhost:5005");
//
//		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
//				.analyze(driver.findElement(By.tagName("p")));
//
//		JSONArray violations = responseJSON.getJSONArray("violations");
//
//		if (violations.length() == 0) {
//			assertTrue("No violations found", true);
//		} else {
//			AXE.writeResults(testName.getMethodName(), responseJSON);
//			assertTrue(AXE.report(violations), false);
//		}
//	}
//
//	/**
//	 * Test a page with Shadow DOM violations
//	 */
//	@Test
//	public void testAccessibilityWithShadowElement() {
//		driver.get("http://localhost:5005/shadow-error.html");
//
//		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();
//
//		JSONArray violations = responseJSON.getJSONArray("violations");
//
//		JSONArray nodes = ((JSONObject)violations.get(0)).getJSONArray("nodes");
//		JSONArray target = ((JSONObject)nodes.get(0)).getJSONArray("target");
//
//		if (violations.length() == 1) {
////			assertTrue(AXE.report(violations), true);
//			assertEquals(String.valueOf(target), "[[\"#upside-down\",\"ul\"]]");
//		} else {
//			AXE.writeResults(testName.getMethodName(), responseJSON);
//			assertTrue("No violations found", false);
//
//		}
//	}
}